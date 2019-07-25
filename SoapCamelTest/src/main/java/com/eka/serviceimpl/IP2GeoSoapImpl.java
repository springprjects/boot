package com.eka.serviceimpl;

import com.cdyne.ws.IP2GeoSoap;
import com.cdyne.ws.IPInformation;

public class IP2GeoSoapImpl implements IP2GeoSoap {

	@Override
	public IPInformation resolveIP(String ipAddress, String licenseKey) {
		IPInformation iPInformation=new IPInformation();
		iPInformation.setOrganization("eka");
		return iPInformation;
	}

}
