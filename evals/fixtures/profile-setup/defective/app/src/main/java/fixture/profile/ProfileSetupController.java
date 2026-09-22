package fixture.profile;

final class ProfileSetupController {
    interface Repository {
        void save(String city) throws Exception;
    }

    private final Repository repository;
    boolean complete;
    boolean navigated;
    boolean locating = true;

    ProfileSetupController(Repository repository) {
        this.repository = repository;
    }

    void submit(String city) throws Exception {
        complete = true;
        navigated = true;
        repository.save(city);
    }

    void onLocationDenied() {
        // Manual entry remains disabled while locating is true.
    }
}
