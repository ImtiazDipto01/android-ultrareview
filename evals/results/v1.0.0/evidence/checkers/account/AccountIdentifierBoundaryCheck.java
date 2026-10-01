package fixture.links;

import java.util.ArrayList;
import java.util.List;

public final class AccountIdentifierBoundaryCheck {
    public static void main(String[] args) {
        List<String> authorized = new ArrayList<>();
        List<String> opened = new ArrayList<>();
        AccountDeepLinkRouter router = new AccountDeepLinkRouter(
                true,
                accountId -> {
                    authorized.add(accountId);
                    return true;
                },
                opened::add);

        require(!router.openColdStart(""), "cold-start empty identifier must be rejected");
        require(!router.onNewIntent(""), "warm-start empty identifier must be rejected");
        require(!router.openColdStart("   "), "cold-start blank identifier must be rejected");
        require(!router.onNewIntent("   "), "warm-start blank identifier must be rejected");
        require(authorized.isEmpty(), "empty and blank identifiers must fail before authorization");
        require(opened.isEmpty(), "empty and blank identifiers must not open account data");

        System.out.println("PASS: empty/blank cold and warm identifiers were rejected before authorization/opening");
    }

    private static void require(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
