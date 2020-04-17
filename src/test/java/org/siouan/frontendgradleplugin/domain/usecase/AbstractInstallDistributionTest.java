package org.siouan.frontendgradleplugin.domain.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.file.Path;
import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.exception.ArchiverException;
import org.siouan.frontendgradleplugin.domain.exception.DistributionValidatorException;
import org.siouan.frontendgradleplugin.domain.exception.InvalidDistributionUrlException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionArchiveException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedDistributionIdException;
import org.siouan.frontendgradleplugin.domain.exception.UnsupportedPlatformException;
import org.siouan.frontendgradleplugin.domain.model.DeploymentSettings;
import org.siouan.frontendgradleplugin.domain.model.DistributionId;
import org.siouan.frontendgradleplugin.domain.model.GetDistributionSettings;
import org.siouan.frontendgradleplugin.domain.model.InstallSettings;
import org.siouan.frontendgradleplugin.domain.model.Logger;
import org.siouan.frontendgradleplugin.domain.provider.FileManager;
import org.siouan.frontendgradleplugin.test.fixture.PlatformFixture;
import org.siouan.frontendgradleplugin.test.util.DeploymentSettingsMatcher;
import org.siouan.frontendgradleplugin.test.util.GetDistributionSettingsMatcher;

@ExtendWith(MockitoExtension.class)
class AbstractInstallDistributionTest {

    private static final Proxy PROXY = Proxy.NO_PROXY;

    private static final String VERSION = "7.34.1";

    private static final String DISTRIBUTION_URL = "https://domain.com/distro.tar.gz";

    @TempDir
    Path temporaryDirectoryPath;

    private Path extractDirectoryPath;

    @Mock
    private FileManager fileManager;

    @Mock
    private GetDistribution getDistribution;

    @Mock
    private DeployDistribution deployDistribution;

    private AbstractInstallDistribution usecase;

    private URL distributionUrl;

    private Path installDirectoryPath;

    @BeforeEach
    void setUp() throws MalformedURLException {
        usecase = new InstallDistributionImpl(fileManager, getDistribution, deployDistribution, mock(Logger.class));
        installDirectoryPath = temporaryDirectoryPath.resolve("install");
        extractDirectoryPath = temporaryDirectoryPath.resolve(AbstractInstallDistribution.EXTRACT_DIRECTORY_NAME);
        distributionUrl = new URL(DISTRIBUTION_URL);
    }

    @Test
    void shouldFailWhenInstallDirectoryCannotBeDeleted() throws IOException {
        final Exception expectedException = new IOException();
        doThrow(expectedException).when(fileManager).deleteFileTree(installDirectoryPath, true);
        final InstallSettings installSettings = new InstallSettings(PlatformFixture.LOCAL_PLATFORM, VERSION,
            distributionUrl, PROXY, temporaryDirectoryPath, installDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(installSettings)).isEqualTo(expectedException);

        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }

    @Test
    void shouldFailWhenDistributionCannotBeRetrieved()
        throws IOException, UnsupportedDistributionIdException, UnsupportedPlatformException,
        DistributionValidatorException, InvalidDistributionUrlException {
        final Exception expectedException = new UnsupportedPlatformException(PlatformFixture.LOCAL_PLATFORM);
        when(getDistribution.execute(argThat(new GetDistributionSettingsMatcher(
            new GetDistributionSettings(usecase.getDistributionId(), PlatformFixture.LOCAL_PLATFORM, VERSION,
                distributionUrl, temporaryDirectoryPath, PROXY))))).thenThrow(expectedException);
        final InstallSettings installSettings = new InstallSettings(PlatformFixture.LOCAL_PLATFORM, VERSION,
            distributionUrl, PROXY, temporaryDirectoryPath, installDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(installSettings)).isEqualTo(expectedException);

        verify(fileManager).deleteFileTree(installDirectoryPath, true);
        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }

    @Test
    void shouldFailWhenDistributionCannotBeDeployed()
        throws IOException, UnsupportedDistributionIdException, UnsupportedPlatformException,
        DistributionValidatorException, UnsupportedDistributionArchiveException, ArchiverException,
        InvalidDistributionUrlException {
        final Path distributionFilePath = temporaryDirectoryPath.resolve("dist.zip");
        when(getDistribution.execute(argThat(new GetDistributionSettingsMatcher(
            new GetDistributionSettings(usecase.getDistributionId(), PlatformFixture.LOCAL_PLATFORM, VERSION,
                distributionUrl, temporaryDirectoryPath, PROXY))))).thenReturn(distributionFilePath);
        final Exception expectedException = mock(UnsupportedDistributionArchiveException.class);
        doThrow(expectedException)
            .when(deployDistribution)
            .execute(argThat(new DeploymentSettingsMatcher(
                new DeploymentSettings(PlatformFixture.LOCAL_PLATFORM, extractDirectoryPath, installDirectoryPath,
                    distributionFilePath))));
        final InstallSettings installSettings = new InstallSettings(PlatformFixture.LOCAL_PLATFORM, VERSION,
            distributionUrl, PROXY, temporaryDirectoryPath, installDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(installSettings)).isEqualTo(expectedException);

        verify(fileManager).deleteFileTree(installDirectoryPath, true);
        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }

    @Test
    void shouldFailWhenDownloadedDistributionFileCannotBeDeleted()
        throws IOException, UnsupportedDistributionIdException, UnsupportedPlatformException,
        DistributionValidatorException, UnsupportedDistributionArchiveException, ArchiverException,
        InvalidDistributionUrlException {
        final Path distributionFilePath = temporaryDirectoryPath.resolve("dist.zip");
        when(getDistribution.execute(argThat(new GetDistributionSettingsMatcher(
            new GetDistributionSettings(usecase.getDistributionId(), PlatformFixture.LOCAL_PLATFORM, VERSION,
                distributionUrl, temporaryDirectoryPath, PROXY))))).thenReturn(distributionFilePath);
        final Exception expectedException = new IOException();
        doThrow(expectedException).when(fileManager).delete(distributionFilePath);
        final InstallSettings installSettings = new InstallSettings(PlatformFixture.LOCAL_PLATFORM, VERSION,
            distributionUrl, PROXY, temporaryDirectoryPath, installDirectoryPath);

        assertThatThrownBy(() -> usecase.execute(installSettings)).isEqualTo(expectedException);

        verify(fileManager).deleteFileTree(installDirectoryPath, true);
        verify(deployDistribution).execute(argThat(new DeploymentSettingsMatcher(
            new DeploymentSettings(PlatformFixture.LOCAL_PLATFORM, extractDirectoryPath, installDirectoryPath,
                distributionFilePath))));
        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }

    @Test
    void shouldInstallDistribution()
        throws IOException, UnsupportedDistributionIdException, UnsupportedPlatformException,
        DistributionValidatorException, UnsupportedDistributionArchiveException, ArchiverException,
        InvalidDistributionUrlException {
        final Path distributionFilePath = temporaryDirectoryPath.resolve("dist.zip");
        when(getDistribution.execute(argThat(new GetDistributionSettingsMatcher(
            new GetDistributionSettings(usecase.getDistributionId(), PlatformFixture.LOCAL_PLATFORM, VERSION,
                distributionUrl, temporaryDirectoryPath, PROXY))))).thenReturn(distributionFilePath);
        final InstallSettings installSettings = new InstallSettings(PlatformFixture.LOCAL_PLATFORM, VERSION,
            distributionUrl, PROXY, temporaryDirectoryPath, installDirectoryPath);

        usecase.execute(installSettings);

        verify(fileManager).deleteFileTree(installDirectoryPath, true);
        verify(deployDistribution).execute(argThat(new DeploymentSettingsMatcher(
            new DeploymentSettings(PlatformFixture.LOCAL_PLATFORM, extractDirectoryPath, installDirectoryPath,
                distributionFilePath))));
        verify(fileManager).delete(distributionFilePath);
        verifyNoMoreInteractions(fileManager, getDistribution, deployDistribution);
    }

    private static class InstallDistributionImpl extends AbstractInstallDistribution {

        InstallDistributionImpl(final FileManager fileManager, final GetDistribution getDistribution,
            final DeployDistribution deployDistribution, final Logger logger) {
            super(fileManager, getDistribution, deployDistribution, logger);
        }

        @Nonnull
        @Override
        protected DistributionId getDistributionId() {
            return DistributionId.NODE;
        }
    }
}
