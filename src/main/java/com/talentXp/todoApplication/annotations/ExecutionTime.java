package com.talentXp.todoApplication.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @Target(ElementType.METHOD): Indicates that this annotation can be applied to methods only.
 * @Retention(RetentionPolicy.RUNTIME): Ensures that the annotation is retained at runtime, 
 * allowing it to be accessed via reflection during execution.
 * @Documented: Indicates that this annotation will be included in the Javadoc documentation for 
 * the annotated method.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExecutionTime {
	/*
	 * This annotation is used to measure the execution time of an endpoint.
	 * It calculates the time taken for the endpoint to process the request and complete its execution.
	 */
}
