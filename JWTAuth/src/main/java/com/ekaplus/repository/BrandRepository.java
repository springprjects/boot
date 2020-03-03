package com.ekaplus.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ekaplus.entity.Brand;

@RepositoryRestResource(path = "brand")
public interface BrandRepository extends PagingAndSortingRepository<Brand, Long>{

}
