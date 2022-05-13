package com.springcloud.courseservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.springcloud.courseservice.entity.UserAccountMt;


public interface CommonAccountRepository extends PagingAndSortingRepository<UserAccountMt, Integer> {

}
