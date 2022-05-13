package com.springcloud.courseservice.entrypoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springcloud.courseservice.entity.Category;
import com.springcloud.courseservice.entity.CourseBasic;
import com.springcloud.courseservice.entity.Section;
import com.springcloud.courseservice.entity.UserAccountDt;
import com.springcloud.courseservice.entity.UserAccountMt;
import com.springcloud.courseservice.exception.CourseDuplicatedException;
import com.springcloud.courseservice.exception.NoCourseException;
import com.springcloud.courseservice.repository.CommonCategoryRepository;
import com.springcloud.courseservice.service.LearningAccountService;
import com.springcloud.courseservice.service.LearningCourseService;



@Controller
@RequestMapping("/teacherPage")
public class IndexPageController {


	@Autowired
	private LearningCourseService courseService;

	@Autowired
	private LearningAccountService accountService;

	@Autowired
	private CommonCategoryRepository caregoryRepo;
	
	

	@GetMapping("/page")
	public String homepage( Model model) {
		  UserAccountDt udt = accountService.findByEmail(UtilityTool.getTokenEmail());
		int accountID = udt.getUserId();
		model.addAttribute("creator", udt);
		model.addAttribute("accountID", accountID);
		return "course/teacherPage/page.html";
	}

	@GetMapping("/play/{courseID}")
	public String playpage(@PathVariable(name = "courseID") String courseID, Model model) throws IOException {
		int courseIntID=Integer.parseInt(courseID);
		  UserAccountDt acd = accountService.findByEmail(UtilityTool.getTokenEmail());
		CourseBasic theCourse=courseService.findCourseByCourseId(courseIntID);
		
		UserAccountDt creator=theCourse.getCreator();
		int num=0;
		for (Section sec :theCourse.getSectionList()) {
			num+=sec.getLecturesList().size();
		}
		
		
		model.addAttribute("theCourse",theCourse);
		model.addAttribute("courseID", courseIntID);
		model.addAttribute("accountDt", acd);
		model.addAttribute("creator", creator);
		model.addAttribute("leactureTotalNum", num);
		return "course/teacherPage/courseToPlay";
	}

	@PostMapping("/edit")
	public String editpage(@RequestParam(name = "courseID") Integer courseID, Model model) {
		  UserAccountDt creator = accountService.findByEmail(UtilityTool.getTokenEmail());
		List<Category> cateList = new ArrayList<Category>();
		Iterable<Category> cate = caregoryRepo.findAll();
		cate.forEach(cateList::add);
		model.addAttribute("creator", creator);
		model.addAttribute("courseID", courseID);
		model.addAttribute("cateList", cateList);
		return "course/teacherPage/editcoursepage.html";
	}

	@GetMapping("/new")
	public String newpage(Model model) {
		 UserAccountDt creator = accountService.findByEmail(UtilityTool.getTokenEmail());
		CourseBasic courseBasic = new CourseBasic();
		List<Category> cateList = new ArrayList<Category>();
		Iterable<Category> cate = caregoryRepo.findAll();
		cate.forEach(cateList::add);
		model.addAttribute("courseBasic", courseBasic);
		model.addAttribute("cateList", cateList);
		model.addAttribute("creator", creator);
		return "course/teacherPage/newcoursepage.html";
	}

	@PostMapping("/processedCreateCourse")
	public String processedCreateCourse(CourseBasic courseBasic, @RequestParam("poster") MultipartFile multipartfile,
			Model model) {
		 UserAccountDt creator = accountService.findByEmail(UtilityTool.getTokenEmail());
		int accountID = creator.getUserId();
		UserAccountMt userAccount = accountService.findById(accountID).get();
		courseBasic.setCreator(userAccount.getUserAccountDt());
		
		try {
				courseService.createCourseSucessed(courseBasic);
				CourseBasic existedCourse = courseService.findCourseByUIDAndName(accountID, courseBasic.getCourseName());
				Integer courseID= existedCourse.getCourseID();
				if(multipartfile !=null && !multipartfile.isEmpty()) {
				courseService.updateCourseAbstractCover(multipartfile,courseID);
				}
				List<Category> cateList = new ArrayList<Category>();
				Iterable<Category> cate = caregoryRepo.findAll();
				cate.forEach(cateList::add);
				
				model.addAttribute("creator", creator);
				model.addAttribute("courseID", courseID);
				model.addAttribute("cateList", cateList);
				return "course/teacherPage/editcoursepage.html";
			
		} catch (CourseDuplicatedException | NoCourseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/teacherPage/new";
		}

		
	}

}
