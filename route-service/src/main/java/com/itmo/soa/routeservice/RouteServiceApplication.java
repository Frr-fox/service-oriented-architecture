package com.itmo.soa.routeservice;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/api/v1")
public class RouteServiceApplication extends Application {

}