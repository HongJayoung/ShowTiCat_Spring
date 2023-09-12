package com.ShowTiCat.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.util.DateUtil;
import com.ShowTiCat.vo.MemberVO;

@Controller
public class SecurityController {

	@Autowired
	MemberServiceImpl mservice;
	
	@Autowired
	MemberRepository mRepo;

	//필수 
	@GetMapping("/ShowTiCat/login")
	public void login() {
	}

	@GetMapping("/logout")
	public void logout() {		
	}
	
	@RequestMapping("/ShowTiCat/accessDenied")
	public void accessDenied() {
	}
	
	@GetMapping("/ShowTiCat/join")
	public String join() {
	  return "/ShowTiCat/joinForm";	
	}
	
	@PostMapping("/ShowTiCat/join")
	public String register(@ModelAttribute MemberVO member, String email1, String email2, String year, String month, String day) {
		String email = email1 + "@" + email2;
		String birth = year+"-"+month+"-"+day;
		
		member.setEmail(email);
		member.setBirth(DateUtil.convertToDate(birth));
		
		mservice.insertMember(member);
	  return "redirect:/ShowTiCat/login";	
	}
	
	@ResponseBody
	@PostMapping("/join/checkId")
	public String checkId(@RequestParam String memberId) {
		MemberVO member = mservice.checkId(memberId);
		return member != null?"":"Y";
	}
	
	@GetMapping("/ShowTiCat/findId")
	public void findIdForm() {
	}
	
	@PostMapping("/ShowTiCat/findId")
	public String findId(String name, String email1, String email2, Model model) {
		String email = email1 + "@" + email2;
		MemberVO member = mRepo.findbyNameAndEmail(name, email);
		
		if(member == null) return "/ShowTiCat/findId";
		
		model.addAttribute("memberId", member.getMemberId());
		return "/ShowTiCat/findIdResult";
	}
	
	@GetMapping("/ShowTiCat/findPw")
	public void findPwForm() {
	}
	
	@PostMapping("/ShowTiCat/findPw")
	public String findPw(String memberId, String name, String email1, String email2, Model model) {
		String email = email1 + "@" + email2;
		MemberVO member = mRepo.findbyIdAndNameAndEmail(memberId, name, email);
		
		if(member == null) return "/ShowTiCat/findPw";
		
		model.addAttribute("memberId", member.getMemberId());
		return "/ShowTiCat/resetPw";
	}
	
	@GetMapping("/ShowTiCat/resetPw")
	public void resetPwForm() {
	}
	
	@PostMapping("/ShowTiCat/resetPw")
	public String resetPw(String memberId, String pw) {
		MemberVO member = mRepo.findById(memberId).get();
		member.setPw(pw);
		
		mservice.insertMember(member);
		return "redirect:/ShowTiCat/login";	
	}

}
