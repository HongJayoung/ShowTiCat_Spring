package com.ShowTiCat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.vo.MemberVO;

@Controller
@RequestMapping("/ShowTiCat")
public class MemberController {

	@Autowired
	MemberRepository mRepo;
	
	@GetMapping("/myPage")
	public String myPage(HttpSession session) {
		return "/myPage/myPage";
	}
}
