package org.siouan.frontendgradleplugin.domain.model;

import javax.annotation.Nonnull;

/**
 * This class credentials based on a username/password tuple.
 *
 * @since 3.0.0
 */
public class Credentials {

    private final String username;

    private final String password;

    /**
     * Builds credentials.
     *
     * @param username Username.
     * @param password Password.
     */
    public Credentials(@Nonnull final String username, @Nonnull final String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username.
     *
     * @return Username.
     */
    @Nonnull
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password.
     *
     * @return Password.
     */
    @Nonnull
    public String getPassword() {
        return password;
    }
}
