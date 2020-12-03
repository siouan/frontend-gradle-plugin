package org.siouan.frontendgradleplugin.test.fixture;

import org.siouan.frontendgradleplugin.domain.model.Credentials;

public final class CredentialsFixture {

    private CredentialsFixture() {
    }

    public static Credentials someCredentials() {
        return someCredentials("goezkgir1fei3");
    }

    public static Credentials someCredentials(final String username) {
        return new Credentials(username, "ger#~76'ger");
    }
}
