package com.itmo.soa.navigatorservice.service;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ConnectionFactory {

    Proxy proxy;

    String proxyHost;

    Integer proxyPort;

    public boolean canConnect = true;

    public ConnectionFactory() {
    }

    /**
     * @return
     */
    public static SSLContext getSslContext() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new SecureTrustManager()}, new SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException ex) {

        }
        return sslContext;
    }

    /**
     * @return
     */
    public static HostnameVerifier getHostnameVerifier() {
        return (String hostname, javax.net.ssl.SSLSession sslSession) -> true;
    }

    public Boolean isHttps(String url) {

        if (url.startsWith("https://")) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
