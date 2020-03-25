package org.siouan.frontendgradleplugin.infrastructure;

import javax.annotation.Nonnull;

public class BeanInstanciationException extends Exception {

    public BeanInstanciationException(@Nonnull final Throwable cause) {
        super(cause);
    }
}
