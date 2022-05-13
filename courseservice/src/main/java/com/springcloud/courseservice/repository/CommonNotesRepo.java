package com.springcloud.courseservice.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.springcloud.courseservice.entity.Lectures;
import com.springcloud.courseservice.entity.Notes;
import com.springcloud.courseservice.entity.UserAccountDt;

public interface CommonNotesRepo extends PagingAndSortingRepository<Notes, Integer>{
	
	@Query("SELECT n FROM Notes n WHERE n.author=?1 AND n.duration=?2 AND n.notedlecture=?3")
	Notes checkDuplication(UserAccountDt theUDT, Long duration, Lectures thelecture);

	@Query("SELECT n FROM Notes n WHERE n.author.userId=?1 AND n.notedlecture.lecturesID=?2")
	Iterable<Notes> findAllByUIDAndLectureID(int uid, int lectureID, Sort sort);



}
