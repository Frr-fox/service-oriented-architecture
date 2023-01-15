package com.itmo.soa.navigatorservice.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.ext.Provider;

@Provider
public class ClientProvider {
    private Client client = ClientBuilder.newClient();

    public Client getClient() {
        return client;
    }
}
