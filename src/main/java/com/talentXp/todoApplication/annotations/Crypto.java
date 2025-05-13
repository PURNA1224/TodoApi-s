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
 * @Documented: Indicates that this annotation will be included in the Javadoc documentation for the 
 * annotated method.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Crypto {
	/*
	 * This annotation is used to validate the input received by an endpoint.
	 * It checks for suspicious inputs such as HTML tags or potentially malicious encrypted data.
	 * If any such suspicious input is detected, it will throw a Bad Request (400) HTTP status code.
	 */
}
