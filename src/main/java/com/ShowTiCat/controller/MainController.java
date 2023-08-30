package com.ShowTiCat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/ShowTiCat")
	public String showTiCat() {
		return "/main/showTiCat";
	}
}
