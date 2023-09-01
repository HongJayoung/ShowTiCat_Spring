package com.ShowTiCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ShowTiCat.repository.PlaceRepository;
import com.ShowTiCat.repository.ScheduleRepository;
import com.ShowTiCat.repository.ShowRepository;
import com.ShowTiCat.vo.PlaceVO;
import com.ShowTiCat.vo.ShowVO;

@Controller
public class AdminController {

	@Autowired
	ShowRepository sRepo;
	
	@Autowired
	PlaceRepository pRepo;
	
	@Autowired
	ScheduleRepository scRepo;
	
	@GetMapping("/ShowTiCat/admin")
	public String admin() {
		return "/admin/admin";
	}
	
	@GetMapping("/ShowTiCat/admin/show")
	public String adminShow(Model model) {
		model.addAttribute("showList", sRepo.findAll());
		return "/admin/show";
	}
	
	@ResponseBody
	@PostMapping("/admin/findShow/{showCode}")
	public ShowVO findShow(@PathVariable Long showCode) {
		return sRepo.findById(showCode).get();
	}
	
	@GetMapping("/admin/addShow")
	public void addShowPage() {
	}
	
	@PostMapping("/admin/addShow")
	public String addShow(@ModelAttribute ShowVO show, MultipartFile file) {
		//파일 처리
		sRepo.save(show);
		return "redirect:/ShowTiCat/admin/show";
	}
	
	@PostMapping("/admin/updateShow")
	public String updateShow(@ModelAttribute ShowVO show, MultipartFile file) {
		ShowVO s = sRepo.findById(show.getShowCode()).get();
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
	
	@ResponseBody
	@PostMapping("/admin/findPlace/{placeId}")
	public PlaceVO findPlace(@PathVariable Long placeId) {
		return pRepo.findById(placeId).get();
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
	
	@GetMapping("/ShowTiCat/admin/schedule")
	public String adminSchedule(Model model) {
		model.addAttribute("scheduleList", scRepo.findAll());
		return "/admin/schedule";
	}
}
