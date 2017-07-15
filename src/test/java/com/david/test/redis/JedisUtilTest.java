package com.david.test.redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.david.util.JedisUtil;

/**
 * 使用jedisUtil操作redis(需开启redis服务)
 * @author david
 *
 */
public class JedisUtilTest
{
	@Test
	public void setAndGetTest(){
		JedisUtil.set("myString", "123", 10000);
		String myString=JedisUtil.get("myString");
		System.out.println(myString);
	}
	
	@Test
	public void setAndGetListTest(){
		List<String> list=new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		
		JedisUtil.setList("myList", list, 10000);
	
		List<String> list2=JedisUtil.getList("myList");
		for (String string : list2)
		{
			System.out.println(string);
		}
	}
	
	public static void main(String[] args){
		
	}
}
