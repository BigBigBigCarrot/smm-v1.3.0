package com.david.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception err)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		System.out.println("========登录后权限拦截器========");
		// 获取session值
		if (request.getSession().getAttribute("user")!= null) {
			return true;
		} else {
			request.getRequestDispatcher("/LoginController/loginPage").forward(request, response);    
			//response.sendRedirect(projectName + "/view/login/tologin");// 跳转到登录页面
			return false;
		}

	}
}
