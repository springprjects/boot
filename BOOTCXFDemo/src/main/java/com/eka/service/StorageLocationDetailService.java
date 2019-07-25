package com.eka.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.ekaplus.entity.EkaResponse;
import com.ekaplus.entity.StorageLocationDetail;

@WebService(serviceName="processStorageLocationDetail",targetNamespace="http://www.ekaplus.com/integration/webservice/storagelocationdetail/")
public interface StorageLocationDetailService {

	@WebMethod(action="http://service/eif/ekaplus/com/EIFMessage/processRequest")
	@WebResult(name="Eka.Response",targetNamespace="uri:com.ekaplus.entity.Response")
	public EkaResponse processStorageLocationDetail(@WebParam(name="StorageLocationDetail",targetNamespace="uri:com.ekaplus.entity.StorageLocationDetail")StorageLocationDetail storageLocationDetail);
}
