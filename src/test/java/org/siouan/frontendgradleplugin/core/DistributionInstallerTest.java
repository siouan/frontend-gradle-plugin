package org.siouan.frontendgradleplugin.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.WorkResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for the {@link DistributionInstaller} class.
 */
public class DistributionInstallerTest {

    @TempDir
    protected File temporaryDirectory;

    @Mock
    private Project project;

    @Mock
    private Logger logger;

    @Mock
    private Task task;

    @Mock
    private DistributionUrlResolver urlResolver;

    @Mock
    private DistributionValidator validator;

    @Mock
    private DistributionPostInstallAction listener;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        when(task.getProject()).thenReturn(project);
        when(task.getLogger()).thenReturn(logger);
        final File taskTemporaryDirectory = new File(temporaryDirectory, "task");
        Files.createDirectory(taskTemporaryDirectory.toPath());
        when(task.getTemporaryDir()).thenReturn(taskTemporaryDirectory);
    }

    @Test
    void shouldFailWhenDistributionNotFound() throws DistributionUrlResolverException, MalformedURLException {
        when(urlResolver.resolve()).thenReturn(URI.create("https://pg.htrhjyt.gvdfciz/htrsdf").toURL());
        final DistributionInstaller job = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, null,
                new File(temporaryDirectory, "install"), listener));

        assertThatThrownBy(job::install).isInstanceOf(DownloadException.class);

        verify(urlResolver).resolve();
        verifyNoMoreInteractions(urlResolver);
        verifyZeroInteractions(validator, listener);
    }

    @Test
    void shouldFailWhenDistributionArchiveIsCorrupted()
        throws DistributionUrlResolverException, InvalidDistributionException {
        final URL distributionUrl = getClass().getClassLoader().getResource("distribution.tar.gz");
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        final Exception expectedException = new InvalidDistributionException("");
        doThrow(expectedException).when(validator).validate(eq(distributionUrl), any(File.class));
        final DistributionInstaller job = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, validator,
                new File(temporaryDirectory, "install"), listener));

        assertThatThrownBy(job::install).isEqualTo(expectedException);

        verify(urlResolver).resolve();
        verify(validator).validate(eq(distributionUrl), any(File.class));
        verifyNoMoreInteractions(urlResolver, validator);
        verifyZeroInteractions(listener);
    }

    @Test
    void shouldFailWhenDistributionArchiveIsNotSupported() throws DistributionUrlResolverException {
        final URL distributionUrl = getClass().getClassLoader().getResource("node-v10.15.1.txt");
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        final DistributionInstaller job = new DistributionInstaller(
            new DistributionInstallerSettings(task, Utils.getSystemOsName(), urlResolver, null,
                new File(temporaryDirectory, "install"), listener));

        assertThatThrownBy(job::install).isInstanceOf(UnsupportedDistributionArchiveException.class);

        verify(urlResolver).resolve();
        verifyNoMoreInteractions(urlResolver);
        verifyZeroInteractions(validator, listener);
    }

    @Test
    void shouldDownloadDistribution()
        throws IOException, DistributionUrlResolverException, InvalidDistributionException,
        UnsupportedDistributionArchiveException, DownloadException, DistributionPostInstallException {
        final URL distributionUrl = getClass().getClassLoader().getResource("node-v10.15.3.zip");
        final String distributionUrlAsString = distributionUrl.toString();
        final File installDirectory = new File(temporaryDirectory, "install");
        when(urlResolver.resolve()).thenReturn(distributionUrl);
        final DistributionInstallerSettings settings = new DistributionInstallerSettings(task, Utils.getSystemOsName(),
            urlResolver, null, new File(temporaryDirectory, "install"), listener);
        final DistributionInstaller job = new DistributionInstaller(settings);
        // Emulate exploding distribution
        when(project.copy(ArgumentMatchers.<Action<CopySpec>>any()))
            .then(invocation -> explodeArchive(distributionUrlAsString, installDirectory));

        job.install();

        verify(urlResolver).resolve();
        verify(project).copy(ArgumentMatchers.<Action<CopySpec>>any());
        assertThat(installDirectory.list()).isNotEmpty();
        verify(listener).onDistributionInstalled(settings);
        verifyNoMoreInteractions(urlResolver, listener);
        verifyZeroInteractions(validator);
    }

    private WorkResult explodeArchive(final String distributionUrl, final File installDirectory) throws IOException {
        final String distributionFilename = distributionUrl.substring(distributionUrl.lastIndexOf('/') + 1);
        final File copyDestDirectory = new File(installDirectory, Utils.removeExtension(distributionFilename));
        Files.createDirectory(copyDestDirectory.toPath());
        Files.copy(new File(installDirectory, distributionFilename).toPath(),
            new File(copyDestDirectory, "node").toPath());
        return null;
    }
}
