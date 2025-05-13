package com.talentXp.todoApplication.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.talentXp.todoApplication.annotations.ExecutionTime;


/*
 * The @Aspect annotation defines a class that handles cross-cutting concerns (e.g., logging, security).
 * It works with different types of advice:
 * - @Before: Executes code before the annotated method runs.
 * - @After: Executes code after the annotated method runs, regardless of the method’s outcome.
 * - @Around: Wraps the annotated method, allowing you to run code before and after the method, 
 *   and even control whether the method is executed or not.
 */
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
