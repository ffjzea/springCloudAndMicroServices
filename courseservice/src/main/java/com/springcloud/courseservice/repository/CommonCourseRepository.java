package com.springcloud.courseservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springcloud.courseservice.entity.CourseBasic;


public interface CommonCourseRepository extends PagingAndSortingRepository<CourseBasic, Integer> {
				
//	@Query("SELECT l FROM Lectures l WHERE l.lecturesName LIKE %?1%"
//			+ "OR l.section.course.description LIKE %?1%"
//			+"OR l.section.sectionName LIKE %?1%"
//			+"OR l.section.course.courseName LIKE %?1%"
//			)
//	public Page<Lectures> findAllByKeyWord(String keyword,Pageable pageable);
	
	@Query("SELECT c FROM CourseBasic c WHERE c.creator.userId = ?1 AND c.courseName=?2")
	public Optional<CourseBasic>   findCourseByUIDAndCourseName(Integer UID,String CourseName);
	
	
	@Query("SELECT c FROM CourseBasic c WHERE c.creator.userId = ?1 ")
	public  Iterable<CourseBasic>   findAllCourseByUID(Integer UID, Sort sort);
	
	@Query("SELECT c FROM CourseBasic c WHERE c.creator.userId = ?1 ")
	public  Page<CourseBasic>   findAllCourseByUID(Integer UID, Pageable pageable);
}
