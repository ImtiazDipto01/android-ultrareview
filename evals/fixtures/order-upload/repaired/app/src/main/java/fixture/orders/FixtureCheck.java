package fixture.orders;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicInteger;

public final class FixtureCheck {
    public static void main(String[] args) {
        if (args.length == 0 || "baseline".equals(args[0])) baseline();
        if (args.length > 0 && "excellence".equals(args[0])) excellence();
    }

    private static void baseline() {
        AtomicInteger attempts = new AtomicInteger();
        List<String> keys = new ArrayList<>();
        MemoryLedger ledger = new MemoryLedger();
        OrderUploadWorker firstAttempt = new OrderUploadWorker((order, key) -> {
            keys.add(key);
            if (attempts.incrementAndGet() == 1) throw new Exception("transient");
        }, ledger);
        require(firstAttempt.run("order-7") == OrderUploadWorker.Result.RETRY,
                "transport failure must retry");

        OrderUploadWorker recreatedAttempt = new OrderUploadWorker((order, key) -> {
            keys.add(key);
            attempts.incrementAndGet();
        }, ledger);
        require(recreatedAttempt.run("order-7") == OrderUploadWorker.Result.SUCCESS,
                "retry after worker recreation must succeed");
        require(keys.size() == 2, "retry must reach the gateway twice");
        require("order-7".equals(keys.get(0)) && keys.get(0).equals(keys.get(1)),
                "retry after worker recreation must reuse one stable idempotency key");

        OrderUploadWorker cancelled = new OrderUploadWorker((order, value) -> { throw new InterruptedException(); }, new MemoryLedger());
        require(cancelled.run("order-8") == OrderUploadWorker.Result.CANCELLED,
                "interruption must remain cancellation");
        Thread.interrupted();

        OrderUploadWorker explicitlyCancelled = new OrderUploadWorker(
                (order, value) -> { throw new CancellationException("cancelled"); },
                new MemoryLedger());
        require(explicitlyCancelled.run("order-8") == OrderUploadWorker.Result.CANCELLED,
                "cancellation exception must remain cancellation");
    }

    private static void excellence() {
        AtomicInteger uploads = new AtomicInteger();
        MemoryLedger ledger = new MemoryLedger();
        OrderUploadWorker firstDelivery = new OrderUploadWorker(
                (order, key) -> uploads.incrementAndGet(), ledger);
        require(firstDelivery.run("order-9") == OrderUploadWorker.Result.SUCCESS,
                "first delivery must succeed");

        OrderUploadWorker recreatedDelivery = new OrderUploadWorker(
                (order, key) -> uploads.incrementAndGet(), ledger);
        require(recreatedDelivery.run("order-9") == OrderUploadWorker.Result.SUCCESS,
                "acknowledged redelivery after worker recreation must succeed");
        require(uploads.get() == 1,
                "acknowledged redelivery after worker recreation must not upload twice");
    }

    private static final class MemoryLedger implements OrderUploadWorker.Ledger {
        private final Set<String> acknowledged = new HashSet<>();
        public boolean isAcknowledged(String orderId) { return acknowledged.contains(orderId); }
        public void acknowledge(String orderId) { acknowledged.add(orderId); }
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
