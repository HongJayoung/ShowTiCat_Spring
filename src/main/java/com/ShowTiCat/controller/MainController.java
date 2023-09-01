package com.ShowTiCat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.vo.MemberVO;

@Controller
public class MainController {

	@Autowired
	MemberRepository mRepo;
	
	@GetMapping("/ShowTiCat")
	public String showTiCat(HttpSession session) {
		MemberVO m = mRepo.findById("test").get();
		session.setAttribute("member", m);
		return "/main/showTiCat";
	}
}
