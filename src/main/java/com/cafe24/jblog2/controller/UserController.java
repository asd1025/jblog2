package com.cafe24.jblog2.controller;

import javax.servlet.http.HttpSession;

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
	
	// interceptor 처리!!
	/*
	 * @RequestMapping(value="/login",method = RequestMethod.POST) public String
	 * loginForm(@ModelAttribute UsersVo vo,Model model,HttpSession session) {
	 * UsersVo authUser=usersService.getUser(vo); if(authUser==null) {
	 * model.addAttribute("result","fail"); return "user/login"; }
	 * session.setAttribute("authUser", authUser); return "redirect:/"; }
	 */
	
	
	@RequestMapping(value = {"/login"})
	public String login() { 
		return "user/login";
	}
	
	// interceptor 처리!!
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";
	}
}
