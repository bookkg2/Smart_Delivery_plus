package com.util;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class LogAdvisor {
	private Logger work_log = 
			Logger.getLogger("work"); 
	private Logger user_log = 
			Logger.getLogger("user"); 
	private Logger data_log = 
			Logger.getLogger("data"); 
	
	
//	
//	// after return
//	@AfterReturning(pointcut="execution(* com.controller.SessionController.*(..))",
//			returning="obj")
//	public void sessionLogging(JoinPoint jp, Object obj) {
//		// User 정상 로그인 후 session에서 loginuser attribute를 받아와 로그인 한 유저의 닉네임 로깅
//		HttpSession session = null;
//		
//		for(Object object : jp.getArgs()) {
//			if(object instanceof HttpSession) {
//				session = (HttpSession) object;
//			}
//		}
//		
//		User loginuser = (User)session.getAttribute("loginuser");
//		
//		user_log.debug("user : " + loginuser.getU_name() +" logged in.");
//	}
//	
//	@Before("execution(* com.controller.BoardController.bboardlist(..)) || execution(* com.controller.RestaurantController.rtmap(..))" )
//	public void boardLogging(JoinPoint jp) {
//		// session에서 loginuser attribute를 받아와 로그인 한 유저의 행동(bboardlist와 rtmap만..) 추적
//		HttpSession session = (HttpSession) jp.getArgs()[0];
//		
//		User loginuser = (User)session.getAttribute("loginuser");
//		
//		work_log.debug("user : " + loginuser.getU_name() +
//				" at board " + jp.getSignature().getName());	
//	}
//	
////	@Before()
////	public void maplogging(JoinPoint jp) {
////		
////	}
////	
//	@Around("execution(* com.sds.component..*Service.*(..))")
//	public Object aroundLogger(ProceedingJoinPoint pjp) throws Throwable {
//		System.out.println("�Լ� ���� ��");
//		System.out.println(pjp.getSignature().getName());
////		System.out.println(pjp.getArgs()[0].toString());
//		Object returnObj = pjp.proceed();
//		System.out.println(returnObj.toString());
//		System.out.println("�Լ� ���� ��");
//		return returnObj;
//	}
}