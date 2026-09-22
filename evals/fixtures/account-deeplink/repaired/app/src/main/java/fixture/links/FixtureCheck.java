package fixture.links;

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
    }

    private static void excellence() {
        AtomicInteger opens = new AtomicInteger();
        AccountDeepLinkRouter allowed = new AccountDeepLinkRouter(true, account -> true, account -> opens.incrementAndGet());
        require(!allowed.openColdStart("../acct-7"), "malformed identifier must be rejected");
        require(allowed.onNewIntent("acct-7"), "authorized account must open");
        require(opens.get() == 1, "only the authorized route may open");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
