package com.talentXp.todoApplication;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/*
 * This class implements ApplicationContextAware to gain access to the Spring ApplicationContext.
 * It allows us to interact with the Spring container and retrieve beans programmatically.
 */
public class SpringApplicationContext implements ApplicationContextAware{

	public static ApplicationContext context;
	
	/* The method `setApplicationContext()` is called by Spring after the bean is initialized, 
	 * making the ApplicationContext available for use within the class.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringApplicationContext.context = applicationContext;
	}
	
	/*
	 * Retrieves a bean from the Spring ApplicationContext by its name.
	 * The `bean` parameter is the name of the bean to be retrieved.
	 */
	public static Object getBean(String bean) {
		return context.getBean(bean);
	}

}
