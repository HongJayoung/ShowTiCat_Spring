package com.ShowTiCat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.repository.PointRepository;
import com.ShowTiCat.repository.ReservationRepository;
import com.ShowTiCat.repository.ReviewRepository;
import com.ShowTiCat.security.MemberServiceImpl;
import com.ShowTiCat.vo.MemberVO;

@Controller
public class MemberController {


	@Autowired
	MemberServiceImpl mservice;
	
	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ReservationRepository reRepo;
	
	@Autowired
	ReviewRepository rRepo;
	
	@Autowired
	PointRepository pRepo;
	
	@GetMapping("/myPage")
	public String myPage() {
		return "/myPage/myPage";
	}
	
	@GetMapping("/myPage/myReservation")
	public void myReservation(HttpSession session, Model model) {
		MemberVO m = (MemberVO) session.getAttribute("member");
		model.addAttribute("reservList", reRepo.findByMemberId(m.getMemberId()));
	}
	
	@GetMapping("/myPage/myPoint")
	public void myPoint(HttpSession session, Model model) {
		MemberVO m = (MemberVO) session.getAttribute("member");
		model.addAttribute("pointDetail", pRepo.findByMemberId(m.getMemberId()));
	}
	
	@GetMapping("/myPage/myReview")
	public void myReview(HttpSession session, Model model) {
		MemberVO m = (MemberVO) session.getAttribute("member");
		model.addAttribute("reviewList", rRepo.findByMemberId(m.getMemberId()));
	}
	
	@ResponseBody
	@PostMapping("/myPage/checkPw")
	public Boolean checkPw(@RequestParam String pw, HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute("member");
		return mservice.checkPw(member, pw);
	}
	
	@GetMapping("/myInfo")
	public String checkPwBeforeMyInfo(Model model) {
		model.addAttribute("from", "myInfo");
		return "/myPage/checkPwPage";
	}
	
	@GetMapping("/myPage/myInfo")
	public void myInfo(Model model) {
		model.addAttribute("from", "myInfo");
	}
	
	@PostMapping("/myPage/updateMyInfo")
	public String updateMyInfo(@ModelAttribute MemberVO member, HttpSession session) {
		MemberVO m = mservice.insertMember(member);
		session.setAttribute("member", m);
		return "redirect:/myPage";
	}
	
	@GetMapping("/deleteAccount")
	public String checkPwBeforeDeleteAccount(Model model) {
		model.addAttribute("from", "deleteAccount");
		return "/myPage/checkPwPage";
	}
	
	@GetMapping("/myPage/deleteAccount")
	public void deleteAccount(HttpSession session) {
		//예매내역이 있으면 안되게해야되나
		
		//
		
	}
}
