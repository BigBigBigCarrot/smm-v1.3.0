package com.david.controller;

import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

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
import com.david.util.DateUtil;
import com.david.util.JedisUtil;

@Controller
@RequestMapping("/TestController")
public class TestController {
	private static String TEXT_PAGE = "test_page";
	private static String AJAX_CLOSURE_TEST_PAGE = "ajaxClosureTest";
	@Autowired
	CustomerDao customerDao;

	public TestController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/test_page")
	private String Index() {
		// Customer c=customerDao.get(new Integer(1));
		// int countCustomer=customerDao.countCustomer();
		// jedisTest();
		return TEXT_PAGE;
	}

	private void jedisTest() {
		JedisUtil.set("myKey", "myValue", 10000);
		String value = JedisUtil.get("myKey");
		System.out.println(value);
	}

	@RequestMapping("/ajaxClosureTestPage")
	private String ajaxClosurePage() {
		return AJAX_CLOSURE_TEST_PAGE;
	}

	@RequestMapping("/ajaxClosureTestHandler")
	@ResponseBody
	public String ajaxClosureTestHandler(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "ajaxParameter", required = false) String ajaxParameter) {
//		System.out.println(DateUtil.getSystemDate()+"--进入controller方法");
//		Long delaySecond = 1000*30L;
//		Timer timer = new Timer();// 实例化Timer类
//		timer.schedule(new TimerTask() {
//			public void run() {
//				System.out.println(DateUtil.getSystemDate()+"--timer run");
//				this.cancel();
//			}
//		}, delaySecond);
		int a=0;
		for(long i=0;i<1000000000;i++){
//			System.out.println(DateUtil.getSystemDate()+"i="+i);
			a++;
		}

		return ajaxParameter;
	}

}
