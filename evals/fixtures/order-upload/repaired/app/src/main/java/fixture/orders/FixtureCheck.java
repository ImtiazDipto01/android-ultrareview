package fixture.orders;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class FixtureCheck {
    public static void main(String[] args) {
        if (args.length == 0 || "baseline".equals(args[0])) baseline();
        if (args.length > 0 && "excellence".equals(args[0])) excellence();
    }

    private static void baseline() {
        AtomicReference<String> key = new AtomicReference<>();
        OrderUploadWorker worker = new OrderUploadWorker((order, value) -> key.set(value), new MemoryLedger());
        require(worker.run("order-7") == OrderUploadWorker.Result.SUCCESS, "upload must succeed");
        require("order-7".equals(key.get()), "idempotency key must be stable");
        OrderUploadWorker cancelled = new OrderUploadWorker((order, value) -> { throw new InterruptedException(); }, new MemoryLedger());
        require(cancelled.run("order-8") == OrderUploadWorker.Result.CANCELLED, "cancellation must propagate");
        Thread.interrupted();
    }

    private static void excellence() {
        AtomicInteger uploads = new AtomicInteger();
        MemoryLedger ledger = new MemoryLedger();
        OrderUploadWorker worker = new OrderUploadWorker((order, key) -> uploads.incrementAndGet(), ledger);
        worker.run("order-9");
        worker.run("order-9");
        require(uploads.get() == 1, "acknowledged redelivery must not upload twice");
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
