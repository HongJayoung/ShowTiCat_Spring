package com.ShowTiCat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ShowTiCat.repository.PlaceRepository;
import com.ShowTiCat.repository.ScheduleRepository;
import com.ShowTiCat.repository.ShowRepository;
import com.ShowTiCat.repository.TheaterRepository;
import com.ShowTiCat.vo.TheaterVO;

@RestController
public class AdminRestController {

	@Autowired
	ShowRepository sRepo;
	
	@Autowired
	PlaceRepository pRepo;
	
	@Autowired
	ScheduleRepository scRepo;
	
	@Autowired
	TheaterRepository tRepo;
	
	@PostMapping("/admin/addTheater/{placeId}")
	public List<TheaterVO> addTheater(@RequestBody TheaterVO theater, @PathVariable Long placeId) {
		theater.setPlace(pRepo.findById(placeId).get());
		tRepo.save(theater);
		return tRepo.findByPlaceId(placeId);
	}

	@PostMapping("/admin/updateTheater")
	public List<TheaterVO> updateTheater(@RequestBody TheaterVO theater) {
		TheaterVO t = tRepo.findById(theater.getTheaterId()).get();
		
		t.setLastSeat(theater.getLastSeat());
		t.setTheaterType(theater.getTheaterType());
		
		tRepo.save(t);
		return tRepo.findByPlaceId(t.getPlace().getPlaceId());
	}

}
