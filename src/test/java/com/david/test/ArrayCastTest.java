package com.david.test;

public class ArrayCastTest {

	public static void main(String[] args) {
		Object[] os=new Object[2];
//		Object[] os=new String[2];
		os[0]="0";
		os[1]="1";
		
		String[] Strs=(String[])os;

	}

}
