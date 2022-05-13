package com.springcloud.courseservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springcloud.courseservice.entity.UserAccountDt;
import com.springcloud.courseservice.entity.UserAccountMt;
import com.springcloud.courseservice.repository.CommonAccountDetailRepository;
import com.springcloud.courseservice.repository.CommonAccountRepository;



@Service
public class LearningAccountService {

	@Autowired
	private CommonAccountRepository accountRepo;
	
	private CommonAccountDetailRepository detailRepo;
	public Optional<UserAccountMt> findById(int uid) {
		
		return accountRepo.findById(uid);
	}
	
	public UserAccountDt findByEmail(String email) {
		return detailRepo.findByEmail(email);
		
	}
	
}
