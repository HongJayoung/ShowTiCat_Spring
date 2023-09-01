package com.ShowTiCat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.repository.ReservationRepository;
import com.ShowTiCat.repository.ReviewRepository;
import com.ShowTiCat.vo.MemberVO;

@Controller
public class MemberController {

	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ReservationRepository reRepo;
	
	@Autowired
	ReviewRepository rRepo;
	
	@GetMapping("/myPage")
	public String myPage() {
		return "/myPage/myPage";
	}
	
	@GetMapping("/myPage/myReservation")
	public void myReservation(HttpSession session, Model model) {
		MemberVO m = (MemberVO) session.getAttribute("member");
		model.addAttribute("reservList", reRepo.findByMemberId(m.getMemberId()));
	}
	
	@GetMapping("/myPage/myReview")
	public void myReview(HttpSession session, Model model) {
		MemberVO m = (MemberVO) session.getAttribute("member");
		model.addAttribute("reviewList", rRepo.findByMemberId(m.getMemberId()));
	}
	
	@GetMapping("/myPage/updateInfo")
	public void updateInfo() {
	}
	
	@GetMapping("/myPage/deleteAccount")
	public void deleteAccount() {
	}
}
