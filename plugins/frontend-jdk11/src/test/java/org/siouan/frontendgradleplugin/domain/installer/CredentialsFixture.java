package org.siouan.frontendgradleplugin.domain.installer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CredentialsFixture {

    public static Credentials someCredentials() {
        return someCredentials("goezkgir1fei3");
    }

    public static Credentials someCredentials(final String username) {
        return Credentials.builder().username(username).password("ger#~76'ger").build();
    }
}
