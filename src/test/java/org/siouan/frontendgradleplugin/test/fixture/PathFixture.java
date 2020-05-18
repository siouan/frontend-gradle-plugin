package org.siouan.frontendgradleplugin.test.fixture;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathFixture {

    public static final Path ANY_PATH = Paths.get("/frontend-gradle-plugin/any-path");

    public static final Path TMP_PATH = ANY_PATH.resolve("tmp");

    private PathFixture() {
    }
}
