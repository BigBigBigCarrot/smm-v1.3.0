package com.david.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.david.mybatis.dao.CustomerDao;
import com.david.mybatis.entity.Customer;
import com.david.util.JedisUtil;

@Controller
@RequestMapping("/TestController")
public class TestController
{
	private static String TEXT_PAGE="test_page";
	private static String AJAX_CLOSURE_TEST_PAGE="ajaxClosureTest";
	@Autowired
	CustomerDao customerDao;
	
	public TestController()
	{
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping("/test_page")
	private String Index()
	{
//		Customer c=customerDao.get(new Integer(1));
//		int countCustomer=customerDao.countCustomer();
//		jedisTest();
		return TEXT_PAGE;
	}

	private void jedisTest(){
		JedisUtil.set("myKey", "myValue", 10000);
		String value=JedisUtil.get("myKey");
		System.out.println(value);
	}
	
	@RequestMapping("/ajaxClosureTestPage")
	private String ajaxClosurePage()
	{
		return AJAX_CLOSURE_TEST_PAGE;
	}
	
	@RequestMapping("/ajaxClosureTestHandler")
	@ResponseBody
	private String ajaxClosureTestHandler()
	{
		return "ajaxClosureTestHandler";
	}
	
	
}
