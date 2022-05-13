package com.springcloud.courseservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springcloud.courseservice.entity.CourseStatusType;
import com.springcloud.courseservice.repository.CourseStatuseTypeRepository;

@Service
public class CourseStatusTypeService {
	@Autowired
	private CourseStatuseTypeRepository cstr;

	public CourseStatusType findById(int id) {
		if (null != cstr.findById(id)) {
			return cstr.findById(id).get();
		}
		return null;
	}
}
