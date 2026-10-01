package fixture.links;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class FixtureCheck {
    public static void main(String[] args) {
        if (args.length == 0 || "baseline".equals(args[0])) baseline();
        if (args.length > 0 && "excellence".equals(args[0])) excellence();
    }

    private static void baseline() {
        AtomicInteger opens = new AtomicInteger();
        AccountDeepLinkRouter denied = new AccountDeepLinkRouter(true, account -> false, account -> opens.incrementAndGet());
        require(!denied.openColdStart("acct-7"), "cold start must enforce authorization");
        require(!denied.onNewIntent("acct-7"), "warm start must enforce authorization");
        require(opens.get() == 0, "denied account must not open");

        AtomicInteger loggedOutAuthorizations = new AtomicInteger();
        AccountDeepLinkRouter loggedOut = new AccountDeepLinkRouter(false, account -> {
            loggedOutAuthorizations.incrementAndGet();
            return true;
        }, account -> opens.incrementAndGet());
        require(!loggedOut.openColdStart("acct-7"), "logged-out cold start must be rejected");
        require(!loggedOut.onNewIntent("acct-7"), "logged-out warm start must be rejected");
        require(loggedOutAuthorizations.get() == 0, "logged-out routes must fail before authorization");
        require(opens.get() == 0, "logged-out routes must not open account data");
    }

    private static void excellence() {
        List<String> openedAccounts = new ArrayList<>();
        List<String> authorizedAccounts = new ArrayList<>();
        AccountDeepLinkRouter allowed = new AccountDeepLinkRouter(true, account -> {
            authorizedAccounts.add(account);
            return true;
        }, openedAccounts::add);
        require(!allowed.openColdStart("../acct-7"), "cold start must reject malformed identifiers");
        require(!allowed.onNewIntent(null), "warm start must reject absent identifiers");
        require(authorizedAccounts.isEmpty(), "invalid identifiers must fail before authorization");
        require(openedAccounts.isEmpty(), "invalid identifiers must not open account data");
        require(allowed.openColdStart("acct-7"), "authorized cold start must open");
        require(allowed.onNewIntent("acct-8"), "authorized warm start must open");
        require(authorizedAccounts.equals(List.of("acct-7", "acct-8")),
                "authorization must receive each exact canonical account identifier once");
        require(openedAccounts.equals(List.of("acct-7", "acct-8")),
                "account opening must receive each exact authorized identifier once");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
