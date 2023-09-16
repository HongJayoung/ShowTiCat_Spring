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

import com.ShowTiCat.repository.ScheduleRepository;
import com.ShowTiCat.util.DateUtil;
import com.ShowTiCat.vo.ScheduleVO;

@Controller
public class ScheduleController {

	@Autowired
	ScheduleRepository scRepo;
	
	@ResponseBody
	@PostMapping("/schedule/findSchedule/{showCode}/{placeId}/{date}")
	public List<ScheduleVO> findSchedule(@PathVariable Long showCode, @PathVariable Long placeId, @PathVariable String date) {
		Date d = DateUtil.convertToDate(date);
		return scRepo.findSchedule(showCode, placeId, d, DateUtil.dayAfter(d));
	}
}
