package fixture.links;

final class AccountDeepLinkRouter {
    interface Authorization {
        boolean canOpen(String accountId);
    }

    interface Accounts {
        void open(String accountId);
    }

    private final boolean loggedIn;
    private final Authorization authorization;
    private final Accounts accounts;

    AccountDeepLinkRouter(boolean loggedIn, Authorization authorization, Accounts accounts) {
        this.loggedIn = loggedIn;
        this.authorization = authorization;
        this.accounts = accounts;
    }

    boolean openColdStart(String accountId) {
        return openAuthorized(accountId);
    }

    boolean onNewIntent(String accountId) {
        return openAuthorized(accountId);
    }

    private boolean openAuthorized(String accountId) {
        if (!loggedIn || accountId == null || !accountId.matches("acct-[0-9]+")) return false;
        if (!authorization.canOpen(accountId)) return false;
        accounts.open(accountId);
        return true;
    }
}
