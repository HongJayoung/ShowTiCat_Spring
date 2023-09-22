package com.ShowTiCat.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ShowTiCat.repository.ReservDetailRepository;
import com.ShowTiCat.repository.ScheduleRepository;
import com.ShowTiCat.util.DateUtil;
import com.ShowTiCat.vo.ScheduleVO;

@Controller
public class ScheduleController {

	@Autowired
	ScheduleRepository scRepo;
	
	@Autowired
	ReservDetailRepository rdRepo;
	
	@ResponseBody
	@PostMapping("/schedule/findSchedule/{showCode}/{placeId}/{date}")
	public List<ScheduleVO> findSchedule(@PathVariable Long showCode, @PathVariable Long placeId, @PathVariable String date) {
		Date d = DateUtil.convertToDate(date);
		List<ScheduleVO> scheduleList = scRepo.findSchedule(showCode, placeId, d, DateUtil.dayAfter(d));
		for(ScheduleVO s:scheduleList) {
			String[] reservedSeat = rdRepo.findByScheduleId(s.getScheduleId());
			s.setCountSeat(s.getTheater().getLastSeat() - reservedSeat.length);
		}
		return scheduleList;
	}
}
