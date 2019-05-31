package com.cafe24.jblog2.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.jblog2.vo.UsersVo;

 
public class AuthInterceptor  extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// interceptor : 맵핑된 정보에 의해  함수로 가려하는데 그 전에 interceptor가 개입
		// return false를 하면 그 함수로 안감
		//1. Handler 종류 확인
		// 2가지 있음 HandlerMethod (class) , defaultServletHandler 이렇게 있음 
		// spring-servlet 에 있는 맵핑 정보들이 객체로 저장
		// 맵핑된 정보가 없을 때 defaultServletHandler가 처리함
		if(handler instanceof HandlerMethod == false) {
			// ex) /check.png 맵핑된게 없네 그럼 defaultServletHandler가 처리
			System.out.println(handler);
			// image/ css 파일 이런것들이 다 여기로 들어옴
			// 이런 것들은 처리 안할테니 그냥 넘어가라
			return true;
		}
		
		//2. casting 
		HandlerMethod handlerMethod=(HandlerMethod) handler;
		
		//3. method의 @ Auth  받아오기
		Auth auth= handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. Mehtod에 @Auth 없으면  ?????????????????????????????
		//  Class(Type)에 @Auth를 받아오기
		//		if(auth==null) {
		//			return true;
		//		}
		
		//5. @Auth가 안붙어 있는 경우 ..method에도 class에도 없는
		if(auth==null) {return true;}
		
		//6. @Auth가 class 또는 method에 붙어 있기 때문에 
		// 인증 여부 체크
		HttpSession session= request.getSession();
		if(session==null) {// 인증이 안되어 있네
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		UsersVo authUser= (UsersVo) session.getAttribute("authUser");
		if(authUser==null) { //인증이 안되어 있네
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		// 여기는 인증이 성공
		// 7. Role 가져오기
		Auth.Role role=auth.role();
		
		//8. role이 Auth.Role.USER 라면, 
		// 인증된 모든 사용자는 접근 다능
		//		if(role == Auth.Role.USER) {
		//			return true;
		//		}
		
		// 이것도 해보기..........................................
		//9. Admin role 권한 체크
		// authUser.getRoel().euquals("ADMIN")
		
		// handler에게 요청을 건내주는 것이 목적
		return true;
	}
	

}
