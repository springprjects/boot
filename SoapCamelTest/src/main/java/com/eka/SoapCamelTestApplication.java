package com.eka;


import javax.xml.ws.Endpoint;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.eka.serviceimpl.IP2GeoRestImpl;
import com.eka.serviceimpl.IP2GeoSoapImpl;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@SpringBootApplication
public class SoapCamelTestApplication {

	@Autowired
	private IP2GeoRestImpl iP2GeoRestImpl;
	


	public static void main(String[] args) {
		SpringApplication.run(SoapCamelTestApplication.class, args);
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		SpringBus springBus = new SpringBus();
		return springBus;
	}

	@Bean
	public LoggingFeature loggingFeature() {
		LoggingFeature loggingFeature = new LoggingFeature();
		loggingFeature.setPrettyLogging(false);
		return loggingFeature;
	}

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(springBus(), new IP2GeoSoapImpl());
		endpoint.getFeatures().add(loggingFeature());
		endpoint.publish("/IP2GeoService");
		return endpoint;
	}

	
	@Bean(destroyMethod = "destroy")
	public Server jaxRsServer() {
		final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
		factory.setServiceBean(iP2GeoRestImpl);
		factory.setProvider(new JacksonJsonProvider());
		factory.setBus(springBus());
		factory.setAddress("/IP2GeoServiceRest");
		factory.getFeatures().add(loggingFeature());
		return factory.create();
	}
	
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		final ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/camel/*");
		servlet.setName("CamelServlet");
		return servlet;
	}
	
}
