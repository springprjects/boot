package com.ekaplus.io;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class CacheableService {

	@CachePut(cacheNames="simple",key="name")
	public String getGreet(String name) {
		System.out.println("CacheableService Called");
		return "Hello " + name;
	}
	
	@CacheEvict(cacheNames="simple",allEntries=true)
	public void evictGreet() {
		
	}
	
}
