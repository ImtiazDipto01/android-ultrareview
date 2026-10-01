package fixture.orders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class OrderGatewayBoundaryCheck {
    public static void main(String[] args) {
        AtomicInteger attempts = new AtomicInteger();
        List<String> receivedOrderIds = new ArrayList<>();
        MemoryLedger ledger = new MemoryLedger();

        OrderUploadWorker first = new OrderUploadWorker((orderId, key) -> {
            receivedOrderIds.add(orderId);
            if (attempts.incrementAndGet() == 1) throw new Exception("transient");
        }, ledger);
        require(first.run("order-42") == OrderUploadWorker.Result.RETRY,
                "first transient failure must retry");

        OrderUploadWorker recreated = new OrderUploadWorker((orderId, key) -> {
            receivedOrderIds.add(orderId);
            attempts.incrementAndGet();
        }, ledger);
        require(recreated.run("order-42") == OrderUploadWorker.Result.SUCCESS,
                "recreated retry must succeed");
        require(receivedOrderIds.equals(List.of("order-42", "order-42")),
                "both gateway attempts must receive the requested order identifier unchanged");
        System.out.println("PASS: initial and recreated retry received the exact requested order identifier");
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
