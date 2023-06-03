package org.siouan.frontendgradleplugin.domain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Parses the {@code packageManager} property in a metadata file {@code package.json}.
 *
 * @since 7.0.0
 */
@RequiredArgsConstructor
public class ParsePackageManagerType {

    private static final String PACKAGE_MANAGER_KEY = "packageManager";

    private static final String PACKAGE_MANAGER_NAME_GROUP = "packageManagerName";

    private static final Pattern PACKAGE_MANAGER_PATTERN = Pattern.compile(
        "^(?<" + PACKAGE_MANAGER_NAME_GROUP + ">[\\w\\-.]++)@[\\w\\-.]++$");

    private final FileManager fileManager;

    private final Logger logger;

    /**
     * Resolves the type of package manager chosen in a metadata file (i.e. {@code package.json} file).
     *
     * @param metadataFilePath Path to the metadata file.
     * @return Type of package manager.
     * @throws UnsupportedPackageManagerException If the package manager is not supported.
     * @throws IOException If an I/O error occurs.
     */
    public PackageManagerType execute(final Path metadataFilePath)
        throws UnsupportedPackageManagerException, IOException {
        final String packageManagerValue;
        try {
            final JSONObject packageJsonFileObject = new JSONObject(
                fileManager.readString(metadataFilePath, StandardCharsets.UTF_8));
            packageManagerValue = packageJsonFileObject.getString(PACKAGE_MANAGER_KEY);
        } catch (final JSONException e) {
            throw new UnsupportedPackageManagerException(metadataFilePath, null);
        }
        final Matcher matcher = PACKAGE_MANAGER_PATTERN.matcher(packageManagerValue);
        if (!matcher.matches()) {
            throw new UnsupportedPackageManagerException(metadataFilePath, null);
        }
        final String packageManagerName = matcher.group(PACKAGE_MANAGER_NAME_GROUP);
        logger.info("Package manager: {}", packageManagerName);
        return PackageManagerType
            .fromPackageName(packageManagerName)
            .orElseThrow(() -> new UnsupportedPackageManagerException(metadataFilePath, packageManagerName));
    }
}
