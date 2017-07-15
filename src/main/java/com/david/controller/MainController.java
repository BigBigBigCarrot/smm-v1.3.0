package com.david.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MainController {
	@RequestMapping(value="")
	public String requestApi(HttpServletRequest request,	HttpServletResponse response){
		/*
		request.getSession().getServletContext().setAttribute("localPath", ApplicationConfig.getLocalhostPath());
		request.setAttribute("currentUser", UserUtils.getUser());
		Cookie cookie = new Cookie("userName", UserUtils.getUser().getUserName());
		response.addCookie(cookie);
		*/
		return "index";
	}
}
