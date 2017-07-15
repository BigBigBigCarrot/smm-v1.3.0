package com.david.mybatis.dao;

import java.util.List;
import java.util.Map;

import com.david.mybatis.entity.Customer;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;


public interface CustomerDao extends BaseDao<Customer,Integer> {
	public int countCustomer();
	public List<Map<String,Object>> findByCondition(Map<String,Object> map);
	public List<Map<String,Object>> findByCondition(Map<String,Object> map,PageBounds pageBounds);
}
