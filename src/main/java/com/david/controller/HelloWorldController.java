package com.david.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/HelloWorldController")
public class HelloWorldController
{

	@RequestMapping("/hello_world")
	private String Index()
	{
//		int[] a={1,2};
//		a[5]++;
		return "hello_world";
	}
	
	@RequestMapping("/request_parameter_test")
	private String requestParameterTest()
	{
		
		return "request_parameter_test";
	}
	
}
