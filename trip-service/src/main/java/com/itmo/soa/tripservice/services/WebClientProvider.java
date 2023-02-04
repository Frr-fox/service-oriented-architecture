package com.itmo.soa.tripservice.services;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Component
public class WebClientProvider {
    private final String baseURL = "https://localhost:31471/api/v1/routes";

    SslContext sslContext = SslContextBuilder
            .forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE)
            .build();

    HttpClient httpClient = HttpClient.create().secure(t ->
            t.sslContext(sslContext) );
    private WebClient webClient = WebClient.builder()
            .baseUrl(baseURL)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

    public WebClientProvider() throws SSLException {
    }

    public WebClient getWebClient() {
        return webClient;
    }
}
