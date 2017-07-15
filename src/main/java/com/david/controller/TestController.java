package com.david.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.david.mybatis.dao.CustomerDao;
import com.david.mybatis.entity.Customer;
import com.david.util.JedisUtil;

@Controller
@RequestMapping("/TestController")
public class TestController
{
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
		return "test_page";
	}
	
	private void jedisTest(){
		JedisUtil.set("myKey", "myValue", 10000);
		String value=JedisUtil.get("myKey");
		System.out.println(value);
	}
	
}
