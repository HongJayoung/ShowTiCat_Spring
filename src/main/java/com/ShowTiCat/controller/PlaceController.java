package com.ShowTiCat.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ShowTiCat.repository.PlaceRepository;
import com.ShowTiCat.repository.ReservDetailRepository;
import com.ShowTiCat.repository.ScheduleRepository;
import com.ShowTiCat.util.DateUtil;
import com.ShowTiCat.vo.PlaceVO;
import com.ShowTiCat.vo.ScheduleVO;

@Controller
public class PlaceController {

	@Autowired
	PlaceRepository pRepo;
	
	@Autowired
	ScheduleRepository scRepo;
	
	@Autowired
	ReservDetailRepository rdRepo;
	
	@GetMapping("/place/{placeId}/{date}")
	public String placeList(@PathVariable Long placeId, @PathVariable String date, Model model) {
		Date day = DateUtil.convertToDate(date);
		Date dayAfter = DateUtil.dayAfter(day);
		PlaceVO place = pRepo.findById(placeId).get();
		
		List<ScheduleVO> scheduleList = scRepo.findByPlaceAndShowStartBetween(place, day, dayAfter);
		if(day.equals(DateUtil.getSysdate())) scheduleList = scRepo.findByPlaceIfToday(placeId, dayAfter);
		
		for(ScheduleVO s:scheduleList) {
			String[] reservedSeat = rdRepo.findByScheduleId(s.getScheduleId());
			s.setSeat(reservedSeat);
			s.setCountSeat(s.getTheater().getLastSeat() - reservedSeat.length);
		}
		
		model.addAttribute("place", place);
		model.addAttribute("placeList", pRepo.findAllByOrderByPlaceName());
		model.addAttribute("weekDate", DateUtil.getWeekDate());
		model.addAttribute("scheduleList", scheduleList);
		
		return "/place/placeList";
	}
}