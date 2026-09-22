package fixture.links;

final class AccountDeepLinkRouter {
    interface Accounts {
        void open(String accountId);
    }

    private final boolean loggedIn;
    private final Accounts accounts;

    AccountDeepLinkRouter(boolean loggedIn, Accounts accounts) {
        this.loggedIn = loggedIn;
        this.accounts = accounts;
    }

    boolean openColdStart(String accountId) {
        if (!loggedIn) return false;
        accounts.open(accountId);
        return true;
    }

    boolean onNewIntent(String accountId) {
        accounts.open(accountId);
        return true;
    }
}
