package org.siouan.frontendgradleplugin.domain.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public class ExecSpecification {

    private final String executable;

    private final List<String> arguments;

    private final List<String> additionalExecutionPaths;

    public ExecSpecification(@Nonnull final String executable) {
        this.executable = executable;
        this.arguments = new ArrayList<>();
        this.additionalExecutionPaths = new ArrayList<>();
    }

    @Nonnull
    public String getExecutable() {
        return executable;
    }

    public void addArgument(@Nonnull final String argument) {
        this.arguments.add(argument);
    }

    @Nonnull
    public List<String> getArguments() {
        return new ArrayList<>(arguments);
    }

    public void addAdditionalExecutionPath(@Nonnull final String additionalExecutionPath) {
        this.additionalExecutionPaths.add(additionalExecutionPath);
    }

    @Nonnull
    public List<String> getAdditionalExecutionPaths() {
        return new ArrayList<>(additionalExecutionPaths);
    }
}
