package fixture.orders;

import java.util.UUID;

final class OrderUploadWorker {
    interface Gateway {
        void upload(String orderId, String idempotencyKey) throws Exception;
    }

    enum Result { SUCCESS, RETRY, CANCELLED }

    private final Gateway gateway;

    OrderUploadWorker(Gateway gateway) {
        this.gateway = gateway;
    }

    Result run(String orderId) {
        try {
            gateway.upload(orderId, UUID.randomUUID().toString());
            return Result.SUCCESS;
        } catch (Exception failure) {
            return Result.RETRY;
        }
    }
}
