package com.ShowTiCat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ShowTiCat.repository.ReservDetailRepository;
import com.ShowTiCat.repository.ScheduleRepository;

@Controller
public class ReservationController {
	
	@Autowired
	ScheduleRepository scRepo;
	
	@Autowired
	ReservDetailRepository rdRepo;
	
	@GetMapping("/reservation")
	public String reservationList(Model model) {
		model.addAttribute("scheduleList", scRepo.findAll());
		return "/reservation/scheduleList";
	}
	
	@GetMapping("/reservation/{scheduleId}")
	public String checkSeat(@PathVariable Long scheduleId, Model model) {
		model.addAttribute("schedule", scRepo.findById(scheduleId).get());
		model.addAttribute("reservedSeat", rdRepo.findByScheduleId(scheduleId));
		return "/reservation/checkSeat";
	}
	
	@GetMapping("/reservation/{scheduleId}/{checkedSeat}")
	public String goPayment(@PathVariable Long scheduleId, @PathVariable List<String> checkedSeat, Model model) {
		System.out.println(checkedSeat);
		
		return "/reservation/pay";
	}
}
