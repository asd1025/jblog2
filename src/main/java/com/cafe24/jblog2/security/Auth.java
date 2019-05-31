package com.cafe24.jblog2.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 이 어노테이션을 어디다 붙여서 쓰겠냐
//  Method 만약 Controller 에 붙이면 안붙음..!!
@Target({ElementType.TYPE, ElementType.METHOD}) 
//RetentionPolicy 얘가 언제 유용하냐
// runtime 시 Auth 어노테이션은 컴파일 무시하고 메서드 붙여놓고 
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	
	// 정해진 값들에 대해서는 String은 좋지 않음
	// 사람마다 다른말 쓰니까 Enum이 좋슈
	//String role() default "USER";
	
	public enum Role {USER, ADMIN}
	public Role role() default Role.USER;
	
//	String value() default "USER";
//	int test() default 1;
	
	

}
