package fixture.profile;

final class ProfileSetupController {
    interface Repository {
        void save(String city) throws Exception;
    }

    private final Repository repository;
    boolean complete;
    boolean navigated;
    boolean locating = true;
    private boolean saving;

    ProfileSetupController(Repository repository) {
        this.repository = repository;
    }

    void submit(String city) throws Exception {
        if (saving) return;
        saving = true;
        try {
            repository.save(city);
            complete = true;
            navigated = true;
        } finally {
            saving = false;
        }
    }

    void onLocationDenied() {
        locating = false;
    }
}
