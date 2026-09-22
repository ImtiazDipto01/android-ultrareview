package fixture.profile;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class FixtureCheck {
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || "baseline".equals(args[0])) baseline();
        if (args.length > 0 && "excellence".equals(args[0])) excellence();
    }

    private static void baseline() {
        ProfileSetupController failed = new ProfileSetupController(city -> { throw new Exception("offline"); });
        try { failed.submit("Dhaka"); } catch (Exception expected) { }
        require(!failed.complete && !failed.navigated, "failed save must not complete");
        failed.onLocationDenied();
        require(!failed.locating, "denial must restore manual entry");
    }

    private static void excellence() throws Exception {
        AtomicInteger saves = new AtomicInteger();
        AtomicReference<ProfileSetupController> ref = new AtomicReference<>();
        ProfileSetupController controller = new ProfileSetupController(city -> {
            saves.incrementAndGet();
            ref.get().submit(city);
        });
        ref.set(controller);
        controller.submit("Dhaka");
        require(saves.get() == 1 && controller.complete, "repeated submit must commit once");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
