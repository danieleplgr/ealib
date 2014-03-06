package com.ealib.json.mapper;

import java.lang.reflect.Method;

public class JsonReflectionValueMapperException extends RuntimeException {

	private Class<? extends Object> clazz;
	private Method method;
	private Exception wrappedException;

	public JsonReflectionValueMapperException(Class<? extends Object> clazz,
			Method m, Exception e) {
		this.clazz = clazz;
		this.method = m;
		this.wrappedException = e;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3393191211721867122L;

	@Override
	public String toString() {
		return super.toString();
	}
	
	@Override
	public String getMessage() {
		return "An error occurred during the json mapping process for class: "+clazz.getCanonicalName()+" in the invocation of method: "+method.getName()+" caused by: "+this.wrappedException.toString();
	}
	
}
