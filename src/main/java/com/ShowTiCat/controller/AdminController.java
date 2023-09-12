package com.ShowTiCat.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ShowTiCat.repository.CastDetailRepository;
import com.ShowTiCat.repository.CastRepository;
import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.repository.PlaceRepository;
import com.ShowTiCat.repository.ReservationRepository;
import com.ShowTiCat.repository.ScheduleRepository;
import com.ShowTiCat.repository.ShowRepository;
import com.ShowTiCat.repository.TheaterRepository;
import com.ShowTiCat.util.AwsS3;
import com.ShowTiCat.util.DateUtil;
import com.ShowTiCat.vo.CastDetailVO;
import com.ShowTiCat.vo.CastMultikey;
import com.ShowTiCat.vo.CastVO;
import com.ShowTiCat.vo.MemberVO;
import com.ShowTiCat.vo.PlaceVO;
import com.ShowTiCat.vo.ScheduleVO;
import com.ShowTiCat.vo.ShowVO;
import com.ShowTiCat.vo.TheaterVO;

@Controller
public class AdminController {

	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ShowRepository sRepo;
	
	@Autowired
	PlaceRepository pRepo;
	
	@Autowired
	ScheduleRepository scRepo;
	
	@Autowired
	TheaterRepository tRepo;
	
	@Autowired
	CastDetailRepository cdRepo;
	
	@Autowired
	CastRepository cRepo;
	
	@Autowired
	ReservationRepository rRepo;
	
	@Autowired
	AwsS3 s3;
	
	@GetMapping("/ShowTiCat/admin")
	public String admin(Principal principal, HttpSession session) {
		MemberVO m = mRepo.findById(principal.getName()).orElse(null);
		session.setAttribute("member", m);
		return "/admin/admin";
	}
	
	@GetMapping("/ShowTiCat/admin/show")
	public String adminShow(Model model) {
		model.addAttribute("showList", sRepo.findAll());
		return "/admin/show";
	}
	
	@GetMapping("/admin/addShow")
	public void showAddPage() {
	}
	
	@PostMapping("/admin/addShow")
	public String addShow(@ModelAttribute ShowVO show, MultipartFile file) throws IOException {
		if(!file.isEmpty()) {
			String img = s3.upload(file, "uploads/showImg/");
			show.setPoster(img);
		}
		
		ShowVO s = sRepo.save(show);
		
		for(Long castId:show.getCastId()) {
			CastDetailVO cast = cdRepo.findById(castId).get();
			CastMultikey multiKey = CastMultikey.builder().show(s).castDetail(cast).build();
			CastVO c = new CastVO(multiKey);
			cRepo.save(c);
		}
		
		return "redirect:/ShowTiCat/admin/show";
	}
	
	@GetMapping("/admin/show/{showCode}")
	public String showUpdatePage(@PathVariable Long showCode, Model model) {
		ShowVO show = sRepo.findById(showCode).get();
		List<CastVO> castList = cRepo.findByshowCode(showCode);
		
		String cList = "";
		for(int i=0;i<castList.size();i++) {
			cList += castList.get(i).getCast().getCastDetail().getCastName();
			if(i < castList.size() -1) {
				cList += ", ";
			}
		}
		
		model.addAttribute("show", show);
		model.addAttribute("cList", cList);
		model.addAttribute("cast", cRepo.findAll());
		return "/admin/updateShow";
	}

	@PostMapping("/admin/updateShow")
	public String updateShow(@ModelAttribute ShowVO show, MultipartFile file) throws IOException {
		ShowVO s = sRepo.findById(show.getShowCode()).get();
		
		if(!file.isEmpty()) {
			s3.delete(s.getPoster()); //s3에서 이미지 삭제
			
			String img = s3.upload(file, "uploads/showImg/");
			s.setPoster(img);
		 }
		
		for(Long castId:show.getCastId()) {
			CastDetailVO cast = cdRepo.findById(castId).get();
			CastMultikey multiKey = CastMultikey.builder().show(s).castDetail(cast).build();
			CastVO c = new CastVO(multiKey);
			cRepo.save(c);
		}
		
		s.setCategory(show.getCategory());
		s.setDirector(show.getDirector());
		s.setOpeningDate(show.getOpeningDate());
		s.setPrice(show.getPrice());
		s.setRunningTime(show.getRunningTime());
		s.setShowName(show.getShowName());
		s.setSummary(show.getSummary());
		s.setTrailer(show.getTrailer());
		
		sRepo.save(s);
		return "redirect:/ShowTiCat/admin/show";
	}
	
	@GetMapping("/ShowTiCat/admin/place")
	public String adminPlace(Model model) {
		model.addAttribute("placeList", pRepo.findAll());
		return "/admin/place";
	}

	
	@PostMapping("/admin/addPlace")
	public String addPlace(@ModelAttribute PlaceVO place) {
		pRepo.save(place);
		return "redirect:/ShowTiCat/admin/place";
	}
	
	@PostMapping("/admin/updatePlace")
	public String updatePlace(@ModelAttribute PlaceVO place) {
		PlaceVO p = pRepo.findById(place.getPlaceId()).get();
		
		p.setPlaceLoc(place.getPlaceLoc());
		p.setPlaceName(place.getPlaceName());
		p.setPlacePhone(place.getPlacePhone());
		
		pRepo.save(p);
		return "redirect:/ShowTiCat/admin/place";
	}
	
	@GetMapping("/ShowTiCat/admin/theater")
	public String adminTheater(Model model) {
		model.addAttribute("placeList", pRepo.findAll());
		return "/admin/theater";
	}

	@GetMapping("/ShowTiCat/admin/schedule")
	public String adminSchedule(Model model) {
		model.addAttribute("scheduleList", scRepo.findAllByOrderByShowStart());
		model.addAttribute("showList", sRepo.findAll());
		model.addAttribute("placeList", pRepo.findAll());
		return "/admin/schedule";
	}
	
	@PostMapping("/admin/addSchedule")
	public String addSchedule(String startTime, Long showCode, Long placeId, Long theaterId) {
		PlaceVO p = pRepo.findById(placeId).get();
		ShowVO s = sRepo.findById(showCode).get();
		TheaterVO t = tRepo.findById(theaterId).get();
		Date d = DateUtil.convertToDateTime(startTime);
		
		ScheduleVO schedule = ScheduleVO.builder().place(p).show(s).theater(t).showStart(d).build();
		scRepo.save(schedule);
		
		return "redirect:/ShowTiCat/admin/schedule";
	}

	@PostMapping("/admin/updateSchedule")
	public String updateSchedule(Long scheduleId, Long showCode, Long theaterId, String startTime) {
		ScheduleVO schedule = scRepo.findById(scheduleId).get();
		System.out.println("startTime => "+startTime);
		
		ShowVO s = sRepo.findById(showCode).get();
		TheaterVO t = tRepo.findById(theaterId).get();
		
		schedule.setShow(s);
		schedule.setTheater(t);
		
		scRepo.save(schedule);
		
		return "redirect:/ShowTiCat/admin/schedule";
	}
}
