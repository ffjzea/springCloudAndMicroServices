package com.springcloud.courseservice.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springcloud.courseservice.entity.Section;

public interface CommonSectionRepository extends PagingAndSortingRepository<Section, Integer> {
		
	@Query("SELECT s FROM Section s WHERE s.courseBasic.courseID = ?1 AND s.sectionNumber=?2")
	public Section findBycourseIDAndSectionNum(int courseID,int sectionNumber);
	
	@Query("SELECT s FROM Section s WHERE s.courseBasic.courseID = ?1")
	public Iterable<Section>  findAllByCourseID(int courseID, Sort sort);
	
	@Query("SELECT s FROM Section s join s.lecturesList l WHERE s.courseBasic.courseID = ?1 AND l.isAvailableToPreview = 1 ")
	public Iterable<Section> findAllPreviewableSectionByCourseID(int courseID, Sort sort);
	
	@Query(nativeQuery = true,
	           value = "SELECT * FROM section \n"
	           		+ "WHERE course_id = ?1\n"
	           		+ "ORDER BY section_id;")
	public List<Section> findAllSectionsByCourseId(Integer id);
}
