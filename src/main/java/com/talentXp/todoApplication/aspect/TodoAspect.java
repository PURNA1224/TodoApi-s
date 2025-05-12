package com.talentXp.todoApplication.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.talentXp.todoApplication.annotations.ExecutionTime;

@Aspect
@Component
public class TodoAspect {
	
	@Around("@annotation(executionTime)")
	public Object calculateExecutionTime(ProceedingJoinPoint point, ExecutionTime executionTime) throws Throwable {
		long start = System.currentTimeMillis();
		
		Object response = point.proceed();
		
		long duration = System.currentTimeMillis() - start;
		System.out.println(duration + "ms");
		return response;
	}
	
//	@After("execution(* com.talentXp.todoApplication.controller..*(..))")
//	public void log(JoinPoint point) {
//		System.out.println(point.getSignature().getName() + " method called");
//	}
	
	@Around("@annotation(com.talentXp.todoApplication.annotations.Crypto)")
	public Object checkEncryption(ProceedingJoinPoint point) throws Throwable {
		

		
		for(Object arg: point.getArgs()) {
			if(arg == null) continue;
			
			String str = arg.toString();
			
			if(str.startsWith("$2a$") || str.startsWith("<")) 
				return ResponseEntity.status(400).body("Something wrong with input");
				
		}
		
		return point.proceed();
		
	}
}
