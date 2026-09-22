package fixture.orders;

public final class FixtureCheck {
    public static void main(String[] args) {
        OrderUploadWorker worker = new OrderUploadWorker((order, key) -> { });
        require(worker.run("order-7") == OrderUploadWorker.Result.SUCCESS, "happy path");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
