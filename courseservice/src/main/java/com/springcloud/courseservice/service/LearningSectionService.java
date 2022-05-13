package com.springcloud.courseservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springcloud.courseservice.entity.Lectures;
import com.springcloud.courseservice.entity.Section;
import com.springcloud.courseservice.exception.NoSectionException;
import com.springcloud.courseservice.repository.CommonSectionRepository;

@Service
public class LearningSectionService {
	
	@Autowired
	private CommonSectionRepository sectionRepo;
	
	

	
	public boolean createSection(Section section) {
		int sectionNum=section.getSectionNumber();
		int courseID=section.getCourseBasic().getCourseID();
		if(!sectionIsDuplicated(courseID, sectionNum)) {
			sectionRepo.save(section);
			return true;
		}
		return false;
	}


	private boolean sectionIsDuplicated(int courseID ,int sectionNum) {
		if(sectionRepo.findBycourseIDAndSectionNum(courseID, sectionNum)!=null) {
			return true;
		}
		return false;
	}


	public Iterable<Section> findAllByCourseId(int courseID) {
			   Sort sort=Sort.by("sectionNumber").ascending();
		return 		sectionRepo.findAllByCourseID(courseID,sort);

	}
	
	//findSectionByCourseId - by weijie
	public List<Section> findAllSectionsByCourseId(Integer id) {
		List<Section> op1 = sectionRepo.findAllSectionsByCourseId(id);
		System.out.println();
		if (op1.isEmpty()) {
			return null;
		}
		return op1;
	}
	
	
	public Section findSectionByID(int id) throws NoSectionException {
		try {
			sectionRepo.findById(id);
			return sectionRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new NoSectionException("no section found");
		}

	}



	public boolean editLecturesListofSection(Section section) {
		
		int courseID=section.getCourseBasic().getCourseID();
		int sectionNum=section.getSectionNumber();
		Section existedSection=sectionRepo.findBycourseIDAndSectionNum(courseID,sectionNum);
		if (existedSection!=null) {
			existedSection.setLecturesList(section.getLecturesList());
			sectionRepo.save(existedSection);
			return true;
		}else {
			return false;
		}
		
	}
	
	
	public void saveSection(Section section) {
		sectionRepo.save(section);

	}


	public List<Section> findAllPreviewableSectionByCourseID(int courseID) {
		List<Section> list=new ArrayList<>();
		Sort sort=Sort.by("sectionNumber").ascending();
		Iterable<Section>  setIter=sectionRepo.findAllPreviewableSectionByCourseID(courseID,sort);
		for (Section sec : setIter   ) {
			if (list.contains(sec)) {
				continue;
			}
			List<Lectures> lectureslist=new ArrayList<>();
			for(Lectures lec : sec.getLecturesList()) {
				
				if(lec.isAvailableToPreview()) {
					lectureslist.add(lec);
				}
			}
			sec.setLecturesList(lectureslist);
			list.add(sec);
		}
		return list;
		
		
	}


	public void deleteSectionBySectionID(int sectionID) {
				sectionRepo.deleteById(sectionID);
	}
	
}
