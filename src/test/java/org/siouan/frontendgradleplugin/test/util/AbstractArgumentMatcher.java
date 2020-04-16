package org.siouan.frontendgradleplugin.test.util;

import javax.annotation.Nonnull;

import org.mockito.ArgumentMatcher;

public abstract class AbstractArgumentMatcher<T> implements ArgumentMatcher<T> {

    final T expectedValue;

    AbstractArgumentMatcher(@Nonnull final T expectedValue) {
        this.expectedValue = expectedValue;
    }
}
