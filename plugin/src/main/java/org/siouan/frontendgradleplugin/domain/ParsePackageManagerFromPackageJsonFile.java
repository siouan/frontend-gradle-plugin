package org.siouan.frontendgradleplugin.domain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Parses the {@code packageManager} property in a metadata file {@code package.json}.
 *
 * @since 7.0.0
 */
@RequiredArgsConstructor
public class ParsePackageManagerFromPackageJsonFile {

    private static final String PACKAGE_MANAGER_KEY = "packageManager";

    private final FileManager fileManager;

    private final ParsePackageManagerSpecification parsePackageManagerSpecification;

    private final Logger logger;

    /**
     * Resolves the name and version of the package manager chosen in a {@code package.json} file.
     *
     * @param packageJsonFilePath Path to the file.
     * @return Specification of the package manager.
     * @throws IOException If an I/O error occurs.
     * @throws InvalidJsonFileException If the {@code package.json} file does not contain valid JSON.
     * @throws MalformedPackageManagerSpecification If value of the package manager key cannot be parsed.
     * @throws UnsupportedPackageManagerException If the package manager is not supported.
     */
    public PackageManager execute(final Path packageJsonFilePath)
        throws InvalidJsonFileException, UnsupportedPackageManagerException, IOException,
        MalformedPackageManagerSpecification {
        final String packageManagerSpecification;
        try {
            final JSONObject packageJsonFileObject = new JSONObject(
                fileManager.readString(packageJsonFilePath, StandardCharsets.UTF_8));
            packageManagerSpecification = packageJsonFileObject.getString(PACKAGE_MANAGER_KEY);
        } catch (final JSONException e) {
            throw new InvalidJsonFileException(packageJsonFilePath);
        }
        final PackageManager packageManager = parsePackageManagerSpecification.execute(packageManagerSpecification);
        logger.info("Package manager: {} v{}", packageManager.type().getPackageManagerName(), packageManager.version());
        return packageManager;
    }
}
