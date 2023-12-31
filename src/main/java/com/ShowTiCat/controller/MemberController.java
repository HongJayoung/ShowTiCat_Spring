package com.ShowTiCat.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.repository.PointRepository;
import com.ShowTiCat.repository.ReservDetailRepository;
import com.ShowTiCat.repository.ReservationRepository;
import com.ShowTiCat.repository.ReviewRepository;
import com.ShowTiCat.security.MemberServiceImpl;
import com.ShowTiCat.vo.MemberVO;
import com.ShowTiCat.vo.PointVO;
import com.ShowTiCat.vo.ReservDetailVO;
import com.ShowTiCat.vo.ReservationVO;

@Controller
public class MemberController {


	@Autowired
	MemberServiceImpl mservice;
	
	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ReservationRepository reRepo;
	
	@Autowired
	ReservDetailRepository rdRepo;
	
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
	
	@GetMapping("/myPage/myReservation/{reservationId}")
	public String reservationDetail(@PathVariable Long reservationId, Model model) {
		ReservationVO reservation = reRepo.findById(reservationId).get();
		
		reservation.setSeat(rdRepo.findByReservationId(reservationId));
		
		model.addAttribute("reservation", reservation);
		return "/myPage/reservDetail";
	}
	
	@Transactional
	@GetMapping("/myPage/cancelReserv/{reservationId}")
	public String cancelReservation(@PathVariable Long reservationId, HttpSession session) {
		ReservationVO reservation = reRepo.findById(reservationId).get();
		reservation.setPayYn("N");
		reRepo.save(reservation);

		rdRepo.deleteByReservationId(reservationId);
		
		MemberVO m = (MemberVO) session.getAttribute("member");
		Long point = reservation.getPoint().getPoint();
//		PointVO p = PointVO.builder().member(m)
//				.point((-1) * point).pointDetail("예매 취소로 인한 사용 취소").build();
//		pRepo.save(p);
		m.setMPoint(m.getMPoint() - point);

		Long plusPoint = (long) (reservation.getTotalPrice() * 0.05);
		if(plusPoint != point) {
			PointVO p = PointVO.builder().member(m)
					.point((plusPoint - point) * 20).pointDetail("예매 취소로 인한 사용 취소").build();
			pRepo.save(p);

			m.setMPoint(m.getMPoint()+p.getPoint());
		}
		
		MemberVO updateM = mRepo.save(m);
		session.setAttribute("member", updateM);

		return "redirect:/myPage/myReservation/"+reservationId;
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
		MemberVO m = (MemberVO) session.getAttribute("member");
		member.setMPoint(m.getMPoint());
		member.setPlace(m.getPlace());
		member.setRegDate(m.getRegDate());
		
		MemberVO updateM = mservice.insertMember(member);
		session.setAttribute("member", updateM);
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
