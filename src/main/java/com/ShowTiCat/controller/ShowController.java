package com.ShowTiCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.repository.ShowRepository;

@Controller
public class ShowController {

	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ShowRepository sRepo;
	
	@GetMapping("/show/{category}")
	public String showList(@PathVariable String category, Model model) {
		model.addAttribute("category", category);
		model.addAttribute("showList", sRepo.findByCategory(category));
		return "/show/showList";
	}
}
