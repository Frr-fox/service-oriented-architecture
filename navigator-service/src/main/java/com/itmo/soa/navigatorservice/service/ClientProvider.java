package com.itmo.soa.navigatorservice.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.ext.Provider;

@Provider
public class ClientProvider {
    Client client = null;
//    private Client client = ClientBuilder.newClient();
    public void init() {
        ClientBuilder builder = ClientBuilder.newBuilder();
        builder.sslContext(ConnectionFactory.getSslContext());
        builder.hostnameVerifier(ConnectionFactory.getHostnameVerifier());
        client = builder.build();
    }

    public Client getClient() {
        if (client == null) {
            init();
        }
        return client;
    }


}
