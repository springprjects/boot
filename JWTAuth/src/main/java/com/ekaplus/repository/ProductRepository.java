package com.ekaplus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ekaplus.entity.Product;

@RepositoryRestResource(path = "product")
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

	@Query(nativeQuery = true,value = "select p.* from product p order by p.color ")
	public List<Product> findAllProductGroupByColor();
}
