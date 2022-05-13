package com.springcloud.courseservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.springcloud.courseservice.entity.Category;

public interface CommonCategoryRepository extends PagingAndSortingRepository<Category, Integer>{

}
