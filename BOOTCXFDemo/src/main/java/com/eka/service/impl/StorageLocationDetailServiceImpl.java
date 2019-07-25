package com.eka.service.impl;

import org.springframework.stereotype.Service;

import com.eka.service.StorageLocationDetailService;
import com.ekaplus.entity.EkaResponse;
import com.ekaplus.entity.ResponseData;
import com.ekaplus.entity.ResponseType;
import com.ekaplus.entity.StorageLocationDetail;

@Service
public class StorageLocationDetailServiceImpl implements StorageLocationDetailService {

	@Override
	public EkaResponse processStorageLocationDetail(StorageLocationDetail storageLocationDetail) {
		System.out.println("**** Reached ****");
		return getDefaultSuccess();
	}

	private EkaResponse getDefaultSuccess() {
		EkaResponse ekaResponse=new EkaResponse();
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseData responseData=new ResponseData();
		ResponseType responseType=new ResponseType();
		responseType.setCode("SUCCESS");
		responseType.setEnvironmentType("TEST");
		responseType.setType("TEST");
		responseType.setEkaRefNo("123");
		responseType.setExternalRefNo("123");
		responseData.getResponse().add(responseType);
		ekaResponse.setResponses(responseData);
		return ekaResponse;
	}

}
