package org.siouan.frontendgradleplugin.domain;

import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SystemProperties {

    public static final String JVM_ARCH_PROPERTY = "os.arch";

    public static final String HTTP_PROXY_HOST = "http.proxyHost";

    public static final String HTTP_PROXY_PORT = "http.proxyPort";

    public static final String HTTPS_PROXY_HOST = "https.proxyHost";

    public static final String HTTPS_PROXY_PORT = "https.proxyPort";

    public static final String NON_PROXY_HOSTS = "http.nonProxyHosts";

    public static final String NON_PROXY_HOSTS_SPLIT_PATTERN = Pattern.quote("|");

    public static final String OS_NAME_PROPERTY = "os.name";
}
