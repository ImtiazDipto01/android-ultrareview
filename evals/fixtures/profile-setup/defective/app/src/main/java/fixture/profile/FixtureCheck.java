package fixture.profile;

public final class FixtureCheck {
    public static void main(String[] args) throws Exception {
        ProfileSetupController controller = new ProfileSetupController(city -> { });
        controller.submit("Dhaka");
        require(controller.complete && controller.navigated, "happy path must complete");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
