package com.ktds.interceptors;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class IpInterceptor extends HandlerInterceptorAdapter{
	private List<String> blackList;
	private String ipNum = "192.168.201";
	
	public IpInterceptor() {
		blackList = new ArrayList<String>();
		blackList.add(ipNum);
	}
	//
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler)	throws Exception {
		String requestorIp = request.getRemoteAddr();
		
			if ( requestorIp.startsWith(ipNum)) {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/error/500.jsp");
				return false;
			}
			return true;
//		
//		if ( blackList.contains(requestorIp) ) {
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/error/500.jsp");
//			rd.forward(request, response);
//			return false;
//		}
//		return true;
		
		
//		if ( request.getRemoteAddr().equals("192.168.201.18")) {
//			response.sendRedirect("http://www.naver.com");
//			return false;	
//		}
//		return true;
	}
	
}
