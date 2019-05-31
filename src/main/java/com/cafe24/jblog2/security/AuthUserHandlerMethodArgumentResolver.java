package com.cafe24.jblog2.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.cafe24.jblog2.vo.UsersVo;


public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {


	@Override
	public Object resolveArgument(MethodParameter parameter, 
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
		
		
		// 내가 지원하는 애가 아니야 다른 애로 셋팅해줘
		if(supportsParameter(parameter)==false) {
			return WebArgumentResolver.UNRESOLVED; 
		}
		
		HttpServletRequest request= webRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session= request.getSession();
		if(session==null) {
			return null;
		}
		return session.getAttribute("authUser");
	}
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser= parameter.getParameterAnnotation(AuthUser.class);
		// @AuthUser가 안붙어 있음.
		if(authUser==null) {
			return false;
		}
		
		// 
		// class 객체를 비교하는 것. 같은 클래스면 하나만 있는 것
		//파라미터 타입이 UserVo
		if(parameter.getParameterType().equals(UsersVo.class)==false){
			return false;
		}
		
		return true;
	}

}
