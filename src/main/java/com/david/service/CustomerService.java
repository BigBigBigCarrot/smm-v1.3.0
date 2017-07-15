package com.david.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.david.mybatis.entity.Customer;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface CustomerService {
	public Customer getCustomerById(Integer id);
	public List<Map<String,Object>> findByCondition(Map<String,Object> map);
	public List<Map<String,Object>> findByCondition(Map<String,Object> map,PageBounds pageBounds);
	public void exportExcel(ServletOutputStream outputStream);
	public void exportExcelCellCombined(ServletOutputStream outputStream);
	public void exportQuoteDetails(ServletOutputStream outputStream);
}
