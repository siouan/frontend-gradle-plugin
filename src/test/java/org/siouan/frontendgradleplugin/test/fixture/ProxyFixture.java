package org.siouan.frontendgradleplugin.test.fixture;

public final class ProxyFixture {


    private ProxyFixture() {
    }

    /**
     * Disables bypassing some proxy hosts such as 'localhost'...
     */
    public static void disableNonProxyHosts() {
        System.out.println("http.nonProxyHosts=" + System.getProperty("http.nonProxyHosts"));
        System.setProperty("http.nonProxyHosts", "");
    }
}
