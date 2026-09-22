package fixture.orders;

final class OrderUploadWorker {
    interface Gateway {
        void upload(String orderId, String idempotencyKey) throws Exception;
    }

    interface Ledger {
        boolean isAcknowledged(String orderId);
        void acknowledge(String orderId);
    }

    enum Result { SUCCESS, RETRY, CANCELLED }

    private final Gateway gateway;
    private final Ledger ledger;

    OrderUploadWorker(Gateway gateway, Ledger ledger) {
        this.gateway = gateway;
        this.ledger = ledger;
    }

    Result run(String orderId) {
        if (ledger.isAcknowledged(orderId)) return Result.SUCCESS;
        try {
            gateway.upload(orderId, orderId);
            ledger.acknowledge(orderId);
            return Result.SUCCESS;
        } catch (InterruptedException cancelled) {
            Thread.currentThread().interrupt();
            return Result.CANCELLED;
        } catch (Exception failure) {
            return Result.RETRY;
        }
    }
}
