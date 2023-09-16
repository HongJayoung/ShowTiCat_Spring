package com.ShowTiCat.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ShowTiCat.repository.MemberRepository;
import com.ShowTiCat.repository.ShowRepository;
import com.ShowTiCat.util.DateUtil;
import com.ShowTiCat.vo.MemberVO;
import com.ShowTiCat.vo.ShowVO;

@Controller
public class MainController {

	@Autowired
	MemberRepository mRepo;
	
	@Autowired
	ShowRepository sRepo;
	
	@GetMapping("/ShowTiCat")
	public String showTiCat(Principal principal, HttpSession session) {
		if(principal != null) {
			MemberVO m = mRepo.findById(principal.getName()).get();
			session.setAttribute("member", m);
		}
		session.setAttribute("date", DateUtil.getSysdate());
		return "/main/showTiCat";
	}
	
	@GetMapping("/ShowTiCat/search")
	public String search(String word, Model model) {
		List<ShowVO> showList = sRepo.findByShowNameContaining(word);
		model.addAttribute("word", word);
		model.addAttribute("showList", showList);
		return "/main/searchResult";
	}
}
