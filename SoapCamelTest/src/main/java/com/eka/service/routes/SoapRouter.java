/*package com.eka.service.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdyne.ws.IP2GeoSoap;
import com.eka.serviceimpl.IP2GeoSoapImpl;

@Component
public class SoapRouter extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {
		
		final CamelContext camelContext = getContext();
       
				
	        CxfEndpoint cxfEndpoint = new CxfEndpoint();
			cxfEndpoint.setAddress("http://localhost:8080/camel/IP2GeoSoapImpl");
			cxfEndpoint.setServiceClass(IP2GeoSoap.class);
	        cxfEndpoint.setCamelContext(camelContext);
	        cxfEndpoint.setDataFormat(DataFormat.PAYLOAD);
	        
	        camelContext.addEndpoint("cxfSoapEndpoint", cxfEndpoint);    
	        
		    
		from("cxf:cxfSoapEndpoint")
		  .log("request reached");
	}

}
*/