package fixture.profile;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public final class FixtureCheck {
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || "baseline".equals(args[0])) baseline();
        if (args.length > 0 && "excellence".equals(args[0])) excellence();
    }

    private static void baseline() throws Exception {
        AtomicInteger attempts = new AtomicInteger();
        AtomicReference<ProfileSetupController> ref = new AtomicReference<>();
        ProfileSetupController controller = new ProfileSetupController(city -> {
            require(!ref.get().complete && !ref.get().navigated, "save must finish before completion");
            if (attempts.incrementAndGet() == 1) throw new Exception("offline");
        });
        ref.set(controller);

        try { controller.submit("Dhaka"); } catch (Exception expected) { }
        require(!controller.complete && !controller.navigated, "failed save must not complete");
        controller.onLocationDenied();
        require(!controller.locating, "denial must restore manual entry");

        controller.submit("Dhaka");
        require(attempts.get() == 2, "failed save must remain retryable");
        require(controller.complete && controller.navigated, "successful retry must complete and navigate");
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
        require(saves.get() == 1, "repeated submit must persist once");
        require(controller.complete && controller.navigated, "outer submit must complete and navigate once");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
