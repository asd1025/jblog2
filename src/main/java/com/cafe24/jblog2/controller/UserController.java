package com.cafe24.jblog2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.jblog2.service.UsersService;
import com.cafe24.jblog2.vo.UsersVo;


@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	private UsersService usersService;
	
	@RequestMapping("/join")
	public String joinForm() {
		return "user/join";
	}
	@RequestMapping("/joinSuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	@RequestMapping(value="/join",method = RequestMethod.POST)
	public String join(@ModelAttribute UsersVo vo,Model model) {
		usersService.join(vo);
		return "redirect:/user/joinSuccess";
	}
	
	@RequestMapping(value = {"/login"})
	public String login() { 
		return "user/login";
	}
}
