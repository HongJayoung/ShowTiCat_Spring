package com.ShowTiCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ShowTiCat.repository.CastRepository;
import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.repository.ReviewRepository;
import com.ShowTiCat.repository.ShowRepository;

@Controller
public class ShowController {

	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ShowRepository sRepo;
	
	@Autowired
	CastRepository cRepo;
	
	@Autowired
	ReviewRepository rRepo;
	
	@GetMapping("/show/{category}")
	public String showList(@PathVariable String category, Model model) {
		model.addAttribute("category", category);
		model.addAttribute("showList", sRepo.findByCategory(category));
		return "/show/showList";
	}
	
	@GetMapping("/show/detail/{showCode}")
	public String showDetail(@PathVariable Long showCode, Model model) {
		model.addAttribute("show", sRepo.findById(showCode).get());
		model.addAttribute("castList", cRepo.findByshowCode(showCode));
		model.addAttribute("reviewList", rRepo.findByshowCode(showCode));
		return "/show/showDetail";
	}
}
