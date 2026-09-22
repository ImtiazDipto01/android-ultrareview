package fixture.links;

public final class FixtureCheck {
    public static void main(String[] args) {
        AccountDeepLinkRouter router = new AccountDeepLinkRouter(true, account -> { });
        require(router.openColdStart("acct-7"), "logged-in cold start");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
