package com.david.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.david.mybatis.entity.Customer;

@Controller
@RequestMapping("/PostManController")
public class PostManController {
	@RequestMapping(value = "/sessionTest")
	public String sessionTest( HttpServletRequest request, HttpServletResponse response) {
		return "postMan/sessionTest";		 	
	}
	
	@RequestMapping(value = "/saveSession",method = RequestMethod.POST)
	@ResponseBody
	public String receiveCustomer(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="sessionKey",required=false) String sessionKey,
			@RequestParam(value="sessionValue",required=false) String sessionValue
			) {
		HttpSession session=request.getSession();
		session.setAttribute(sessionKey, sessionValue);
		return "success";		 	
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param sessionKey
	 * @return sessionValue
	 */
	@RequestMapping(value = "/getSession",method = RequestMethod.GET)
	@ResponseBody
	public String getSession(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="sessionKey",required=false) String sessionKey
			) {
		HttpSession session=request.getSession();
		String sessionValue=null;
		if(session.getAttribute(sessionKey)!=null){
			sessionValue=(String)session.getAttribute(sessionKey);
		}
		
		return sessionValue;		 	
	}
	
	/**
	 * 登陆验证
	 */
	@RequestMapping(value="/loginvalidate",method = RequestMethod.POST)
	@ResponseBody
	public String loginValidate(
			@RequestParam(value="account",required=true) String account, 
			@RequestParam(value="password",required=true) String password, 
			//@RequestParam(value="jCaptcha",required=false) String jCaptcha,
			HttpServletRequest request, HttpServletResponse response) {
		
		return "登录成功";
	}
	
}
