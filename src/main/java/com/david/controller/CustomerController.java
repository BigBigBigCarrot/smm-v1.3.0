package com.david.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONArray;
import com.david.mybatis.entity.Customer;
import com.david.service.CustomerService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
@RequestMapping("/CustomerController")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/customerList")
	//@ResponseBody
	public String CsutomerList( HttpServletRequest request, HttpServletResponse response) {
		return "customerList";		 	
	}
	
	@RequestMapping(value = "/receiveCustomer",method = RequestMethod.POST)
	@ResponseBody
	public Customer receiveCustomer(
			HttpServletRequest request,
			HttpServletResponse response,
			Customer customer
			) {
	return customer;		 	
	}
	
	/*
	@RequestMapping(value = "/receiveCustomer",method = RequestMethod.POST)
	@ResponseBody
	public Customer receiveCustomer(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="id",required=false) Integer id,
			@RequestParam(value="firstName",required=false) String firstName,
			@RequestParam(value="lastName",required=false) String lastName,
			@RequestParam(value="phone",required=false) String phone
			) {
	
	//Map<String, Object> map  = new HashMap<String, Object>();
		
		Customer customer=new Customer();
		if(id!=null){
			customer.setId(id);
		}
		if(firstName!=null){
			customer.setFirstName(firstName);
		}
		if(lastName!=null){
			customer.setLastName(lastName);
		}
		if(phone!=null){
			customer.setPhone(phone);
		}
	return customer;		 	
	}
	*/
	
	/*
	 *返回链表
	@RequestMapping(value = "/data",method = RequestMethod.GET)
	@ResponseBody
	public List<Customer> data( HttpServletRequest request, HttpServletResponse response) {
		List<Customer> list=new ArrayList();
		
		 Customer customer1=new Customer();
		 Customer customer2=new Customer();
		 
		 customer1.setId(new Integer("1"));
		 customer1.setFirstName("Brian");
		 customer1.setLastName("Cox");
		 
		 customer2.setId(new Integer("2"));
		 customer2.setFirstName("Martin");
		 customer2.setLastName("Jacques");
		 
		 list.add(customer1);
		 list.add(customer2);
		 
		return list;		 	
	}
	*/
	
	@RequestMapping(value = "/receiveCustomerListStr",method = RequestMethod.POST)
	@ResponseBody
	public List<Customer> receiveCustomerListStr(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="customerListStr",required=false) String customerListStr
			) {
		
		List<Customer> customerList = JSONArray.parseArray(HtmlUtils.htmlUnescape(customerListStr), Customer.class);
		return customerList;		 	
	}
	
	@RequestMapping(value = "/data",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Customer> data( HttpServletRequest request, HttpServletResponse response) {
		Map<String,Customer> map=new HashMap();
		
		 Customer customer1=new Customer();
		 Customer customer2=new Customer();
		 
		 customer1.setId(new Integer("1"));
		 customer1.setFirstName("Brian");
		 customer1.setLastName("Cox");
		 
		 customer2.setId(new Integer("2"));
		 customer2.setFirstName("Martin");
		 customer2.setLastName("Jacques");
		 
		 map.put("customer1",customer1);
		 map.put("customer2",customer2);
		 
		return map;		 	
	}
	
	@RequestMapping(value = "/getCustomerEntity",method = RequestMethod.GET)
	@ResponseBody
	public Customer getCustomerEntity( HttpServletRequest request, HttpServletResponse response) {
		
		/*
		 Customer customer=new Customer();
		 
		 SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession();
		 customer = (Customer) session.selectOne(
					"com.david.mybatis.entity.CustomerMapper.GetCustomerById", 1);
		 */
		return customerService.getCustomerById(1);		 	
	}
	
	@RequestMapping(value = "/getCustomerList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> getCustomerList(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "firstName", required = false) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName,
			@RequestParam(value = "dob", required = false) String dob,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "currPage", defaultValue = "1", required = false) Integer currPage
			) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		PageBounds pageBounds = new PageBounds(currPage, pageSize);
		
		return customerService.findByCondition(map,pageBounds);
	}
	
}
