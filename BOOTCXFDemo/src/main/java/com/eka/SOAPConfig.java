package com.eka;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eka.service.impl.StorageLocationDetailServiceImpl;

@Configuration
public class SOAPConfig {
	
	@SuppressWarnings("unchecked")
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new CXFServlet(), "/app/*");
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
		return new SpringBus();
	}
	
	@Bean
	public LoggingFeature loggingFeature() {
		 LoggingFeature loggingFeature=new LoggingFeature();
		 loggingFeature.setPrettyLogging(true);
		 return loggingFeature;
	}

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(springBus(), new StorageLocationDetailServiceImpl());
		endpoint.getFeatures().add(loggingFeature());
		endpoint.publish("/StorageLocationDetailWebservice");
		return endpoint;
	}
}
