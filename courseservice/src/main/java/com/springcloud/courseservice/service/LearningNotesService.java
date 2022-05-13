package com.springcloud.courseservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springcloud.courseservice.entity.Lectures;
import com.springcloud.courseservice.entity.Notes;
import com.springcloud.courseservice.entity.UserAccountDt;
import com.springcloud.courseservice.entity.UserAccountMt;
import com.springcloud.courseservice.repository.CommonAccountRepository;
import com.springcloud.courseservice.repository.CommonLectureRepository;
import com.springcloud.courseservice.repository.CommonNotesRepo;

@Service
public class LearningNotesService {

	@Autowired

	private CommonNotesRepo notesRepo;

	@Autowired

	private CommonAccountRepository accountRepo;

	@Autowired

	private CommonLectureRepository lectureRepo;
	
	

	public boolean createNote(JsonNoteRecevier reciver) {

		try {
			
			if(!isDuplication(reciver)) {
				buildNewNotesAndSave(reciver);
			}	
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void buildNewNotesAndSave(JsonNoteRecevier reciver) {
		UserAccountMt theuser = accountRepo.findById(reciver.getUserID()).get();
		UserAccountDt theUDT = theuser.getUserAccountDt();
		Long duration = reciver.getDuration();
		Lectures thelecture = lectureRepo.findById(reciver.getLectureID()).get();

		Notes notes = new Notes();
		notes.setAuthor(theUDT);
		notes.setDuration(duration);
		notes.setNotedlecture(thelecture);
		notes.setContext(reciver.getNotescontext());

		notesRepo.save(notes);
	}

	private boolean isDuplication(JsonNoteRecevier reciver)  {
		UserAccountMt theuser = accountRepo.findById(reciver.getUserID()).get();
		UserAccountDt theUDT = theuser.getUserAccountDt();
		Long duration = reciver.getDuration();
		Lectures thelecture = lectureRepo.findById(reciver.getLectureID()).get();
		if (notesRepo.checkDuplication(theUDT,duration,thelecture) !=null) {
			return true;
		}else {
			return false;
		}
		
	}

	public Iterable<Notes> findAllNotesListByUIDAndLectureID(int UID, int lecturesID) {
		   Sort sort=Sort.by("duration").ascending();
		return notesRepo.findAllByUIDAndLectureID(UID,lecturesID,sort);
		
	}

	public Iterable<Notes> findAllNotesListByUIDAndLectureID(JsonNoteRecevier reciver) {
		   Sort sort=Sort.by("duration").ascending();
		return notesRepo.findAllByUIDAndLectureID(reciver.getUserID(),reciver.getLectureID(),sort);
	}

	public Iterable<Notes> findAllNotesList() {
		Pageable pageable= PageRequest.of(0, 3);
		
		return notesRepo.findAll(pageable);
	}
}
