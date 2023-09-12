package com.ShowTiCat.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.repository.PointRepository;
import com.ShowTiCat.repository.ReservDetailRepository;
import com.ShowTiCat.repository.ReservationRepository;
import com.ShowTiCat.repository.ScheduleRepository;
import com.ShowTiCat.vo.MemberVO;
import com.ShowTiCat.vo.PointVO;
import com.ShowTiCat.vo.ReservDetailMultikey;
import com.ShowTiCat.vo.ReservDetailVO;
import com.ShowTiCat.vo.ReservationVO;
import com.ShowTiCat.vo.ScheduleVO;

@Controller
public class ReservationController {
	
	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ScheduleRepository scRepo;
	
	@Autowired
	ReservationRepository rRepo;
	
	@Autowired
	ReservDetailRepository rdRepo;
	
	@Autowired
	PointRepository pRepo;
	
	@GetMapping("/reservation")
	public String reservationList(Model model) {
		model.addAttribute("scheduleList", scRepo.findAll());
		return "/reservation/scheduleList";
	}
	
	@GetMapping("/reservation/{scheduleId}")
	public String checkSeat(@PathVariable Long scheduleId, Model model) {
		ScheduleVO schedule = scRepo.findById(scheduleId).get();
		schedule.setSeat(rdRepo.findByScheduleId(scheduleId));
		model.addAttribute("schedule", schedule);
		return "/reservation/checkSeat";
	}
	
	@GetMapping("/reservation/{scheduleId}/{checkedSeat}")
	public String goPayment(@PathVariable Long scheduleId, @PathVariable List<String> checkedSeat, Model model) {
		model.addAttribute("schedule", scRepo.findById(scheduleId).get());
		model.addAttribute("checkedSeat", checkedSeat);
		return "/reservation/pay";
	}
	
	@PostMapping("/reservation/getPayment")
	public String getPayment(@ModelAttribute ReservationVO reservation, Long scheduleId, Long usedPoint, String seat,  Model model, HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute("member");
		
		ScheduleVO schedule = scRepo.findById(scheduleId).get();
		
		reservation.setMember(member);
		reservation.setSchedule(schedule);
		reservation.setPayYn("Y");
		
		Long totalPoint = reservation.getTotalPrice();
		if(usedPoint != null) {
			member.setMPoint(member.getMPoint()-usedPoint);
			totalPoint -= usedPoint;
			PointVO p = PointVO.builder().member(member).point(usedPoint*-1).pointDetail("영화/공연 예매").build();
			pRepo.save(p); 
		}
		
		PointVO plusPoint = PointVO.builder().member(member).point((long) (totalPoint * 0.05)).pointDetail("영화/공연 예매").build();
		PointVO point = pRepo.save(plusPoint);
		reservation.setPoint(point);
		
		member.setMPoint(member.getMPoint()+(long) (totalPoint * 0.05));
		mRepo.save(member);
		
		session.setAttribute("member", member);
		
		ReservationVO r = rRepo.save(reservation);
		
		String strSeat = seat.substring(1, seat.length()-1);
		String[] ArrayStr = strSeat.split(",");
		
		ReservDetailMultikey multikey = ReservDetailMultikey.builder().reservation(r).build();
		for(String s:ArrayStr) {
			multikey.setSeatNum(s.trim());
			ReservDetailVO rd = ReservDetailVO.builder().reservDetail(multikey).build();
			rdRepo.save(rd);
		}
		
		r.setSeat(rdRepo.findByReservationId(r.getReservationId()));
		model.addAttribute("reservation", r);
		
		return "/reservation/payResult";
	}
}
