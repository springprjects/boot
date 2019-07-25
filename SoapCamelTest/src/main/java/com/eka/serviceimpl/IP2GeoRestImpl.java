package com.eka.serviceimpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.cdyne.ws.IPInformation;

@Component
public class IP2GeoRestImpl   {

	@GET
	@Path("/resolve/{ipAddress}/{licenseKey}")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public IPInformation resolveIP(@PathParam("ipAddress")String ipAddress,@PathParam("licenseKey")String licenseKey) {
		IPInformation iPInformation=new IPInformation();
		iPInformation.setOrganization("eka");
		return iPInformation;
	}

}
