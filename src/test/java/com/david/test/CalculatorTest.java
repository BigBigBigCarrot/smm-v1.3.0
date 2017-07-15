package com.david.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Junit简单测试类
 * @author david
 *
 */
public class CalculatorTest
{
	public int evaluate(String expression)
	{
		int sum = 0;
		for (String summand : expression.split("\\+"))
			sum += Integer.valueOf(summand);
		return sum;
	}
	
	@Test
	public void evaluatesExpression()
	{
		CalculatorTest calculator = new CalculatorTest();
		int sum = calculator.evaluate("1+2+3");
		assertEquals(6, sum);
		//run|debug as junitTest
//		System.out.println(sum);
	}
}
