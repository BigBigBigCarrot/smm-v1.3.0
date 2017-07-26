package com.david.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.david.mybatis.dao.CustomerDao;
import com.david.mybatis.entity.Customer;
import com.david.util.JedisUtil;

@Controller
@RequestMapping("/Test2Controller")
public class Test2Controller
{
	
	@RequestMapping("/ajaxClosureTestHandler")
	@ResponseBody
	public String ajaxClosureTestHandler(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "ajaxParameter", required = false) String ajaxParameter)
	{
		return ajaxParameter;
	}
	
}
