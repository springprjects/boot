package com.eka.service.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eka.serviceimpl.IP2GeoRestImpl;

@Component
public class RestRouter extends RouteBuilder {

	@Autowired
	private IP2GeoRestImpl iP2GeoRestImpl;
	
	
	@Override
	public void configure() throws Exception {
		
		final CamelContext camelContext = getContext();
        final org.apache.camel.impl.SimpleRegistry registry = new org.apache.camel.impl.SimpleRegistry();
        final org.apache.camel.impl.CompositeRegistry compositeRegistry = new org.apache.camel.impl.CompositeRegistry();
		        compositeRegistry.addRegistry(camelContext.getRegistry());
		        compositeRegistry.addRegistry(registry);
		        ((org.apache.camel.impl.DefaultCamelContext) camelContext).setRegistry(compositeRegistry);
		    registry.put("iP2GeoRestImpl", iP2GeoRestImpl);
		    
		rest("/resolve")
		  .get("{ipAddress}/{licenseKey}")
		  		.to("bean:iP2GeoRestImpl?method=resolveIP(${header.ipAddress},${header.licenseKey})");
	}

}
