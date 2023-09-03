package com.ShowTiCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ShowTiCat.repository.PlaceRepository;

@Controller
public class PlaceController {

	@Autowired
	PlaceRepository pRepo;
	
	@GetMapping("/place/{placeId}")
	public String placeList(@PathVariable Long placeId, Model model) {
		model.addAttribute("place", pRepo.findById(placeId).get());
		model.addAttribute("placeList", pRepo.findAll());
		return "/place/placeList";
	}
}
