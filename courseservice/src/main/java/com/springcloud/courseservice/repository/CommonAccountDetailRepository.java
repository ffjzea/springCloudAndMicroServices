package com.springcloud.courseservice.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.springcloud.courseservice.entity.UserAccountDt;

public interface CommonAccountDetailRepository extends PagingAndSortingRepository<UserAccountDt, Integer> {

	public UserAccountDt findByEmail(String useremail);
}
