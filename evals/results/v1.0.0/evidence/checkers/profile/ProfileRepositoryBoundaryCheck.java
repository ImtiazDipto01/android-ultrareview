package fixture.profile;

import java.util.ArrayList;
import java.util.List;

public final class ProfileRepositoryBoundaryCheck {
    public static void main(String[] args) throws Exception {
        List<String> savedCities = new ArrayList<>();
        ProfileSetupController controller = new ProfileSetupController(savedCities::add);

        controller.submit("Dhaka");

        require(savedCities.equals(List.of("Dhaka")),
                "Repository.save must receive the city supplied to submit, unchanged");
        require(controller.complete && controller.navigated,
                "successful persistence must complete the setup");
        System.out.println("PASS: Repository.save received the exact submitted city once");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
