package com.ShowTiCat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.repository.ScheduleRepository;
import com.ShowTiCat.repository.ShowRepository;

@Controller
public class ReservationController {
	
	@Autowired
	ScheduleRepository scRepo;
	
	@GetMapping("/reservation")
	public String reservationList(Model model) {
		model.addAttribute("scheduleList", scRepo.findAll());
		return "/reservation/scheduleList";
	}
}
