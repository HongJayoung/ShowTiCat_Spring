package com.ShowTiCat.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ShowTiCat.repository.CastDetailRepository;
import com.ShowTiCat.repository.CastRepository;
import com.ShowTiCat.repository.PlaceRepository;
import com.ShowTiCat.repository.ReservationRepository;
import com.ShowTiCat.repository.ScheduleRepository;
import com.ShowTiCat.repository.ShowRepository;
import com.ShowTiCat.repository.TheaterRepository;
import com.ShowTiCat.vo.CastDetailVO;
import com.ShowTiCat.vo.PlaceVO;
import com.ShowTiCat.vo.ScheduleVO;
import com.ShowTiCat.vo.ShowVO;
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
	
	@Autowired
	CastRepository cRepo;
	
	@Autowired
	CastDetailRepository cdRepo;
	
	@Autowired
	ReservationRepository rRepo;
	
	@PostMapping("/admin/findShow/{showCode}")
	public ShowVO findShow(@PathVariable Long showCode) {
		return sRepo.findById(showCode).get();
	}
	
	@PostMapping("/admin/getCastList")
	public List<CastDetailVO> getCastList() {
		return (List<CastDetailVO>) cdRepo.findAll();
	}

	@Transactional
	@DeleteMapping("/admin/deleteShow/{showCode}")
	public String deleteShow(@PathVariable Long showCode) {
		String msg ="상영스케줄이 있어 삭제가 불가합니다.";
		//스케줄있는지 확인
		if(scRepo.findByShowCode(showCode).isEmpty()) {
			//cast 삭제
			cRepo.deleteByShowCode(showCode);
			//show 삭ㅈㅔ
			sRepo.deleteById(showCode);
			msg = "";
		}
			
		return msg;
	}
	
	@PostMapping("/admin/findPlace/{placeId}")
	public PlaceVO findPlace(@PathVariable Long placeId) {
		return pRepo.findById(placeId).get();
	}
	
	@Transactional
	@DeleteMapping("/admin/deletePlace/{placeId}")
	public String deletePlace(@PathVariable Long placeId) {
		String msg ="상영스케줄이 있어 삭제가 불가합니다.";
		//스케줄있는지 확인
		if(scRepo.findByPlaceId(placeId).isEmpty()) {
			//theater 삭제
			tRepo.deleteByPlaceId(placeId);
			//place 삭ㅈㅔ
			pRepo.deleteById(placeId);
			msg = "";
		}
		
		return msg;
	}
	
	@PostMapping("/admin/getTheaterList/{placeId}")
	public List<TheaterVO> getTheaterList(@PathVariable Long placeId) {
		return tRepo.findByPlaceId(placeId);
	}

	@PostMapping("/admin/findTheater/{theaterId}")
	public TheaterVO findTheater(@PathVariable Long theaterId) {
		return tRepo.findById(theaterId).get();
	}
	
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
	
	@PostMapping("/admin/addCast")
	public List<CastDetailVO> addCast(@RequestBody CastDetailVO castDetail) {
		cdRepo.save(castDetail);
		return (List<CastDetailVO>) cdRepo.findAll();
	}

	@DeleteMapping("/admin/deleteTheater/{theaterId}")
	public List<TheaterVO> deleteTheater(@PathVariable Long theaterId) {
		Long placeId = tRepo.findById(theaterId).get().getPlace().getPlaceId();
		
		//스케줄있는지 확인
		if(scRepo.findByTheaterId(theaterId).isEmpty()) {
			//theater 삭제
			tRepo.deleteById(theaterId);
		}
		
		return tRepo.findByPlaceId(placeId);
	}
	
	@PostMapping("/admin/findSchedule/{scheduleId}")
	public ScheduleVO findSchedule(@PathVariable Long scheduleId) {
		return scRepo.findById(scheduleId).get();
	}
	
	@DeleteMapping("/admin/deleteSchedule/{scheduleId}")
	public String deleteSchedule(@PathVariable Long scheduleId) {
		String msg ="예약내역이 있어 삭제가 불가합니다.";
		//예약내역있는지 확인
		if(rRepo.findByScheduleId(scheduleId).isEmpty()) {
			//스케줄 삭제
			scRepo.deleteById(scheduleId);
			msg = "";
		}
		
		return msg;
	}
}
