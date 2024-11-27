package org.siouan.frontendgradleplugin.infrastructure.gradle;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.logging.LogLevel;

/**
 * Global settings fixed for the entire build.
 *
 * @since 5.2.0
 */
@Getter
@Builder
public class GradleSettings implements Serializable {

    private static final long serialVersionUID = 8563728350729514612L;

    private final LogLevel projectLogLevel;

    private final LogLevel commandLineLogLevel;

    public static GradleSettings ofProject(final Project project) {
        return new GradleSettings(project.getLogging().getLevel(),
            project.getGradle().getStartParameter().getLogLevel());
    }
}
