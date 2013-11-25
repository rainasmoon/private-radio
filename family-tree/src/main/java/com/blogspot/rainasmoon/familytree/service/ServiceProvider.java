package com.blogspot.rainasmoon.familytree.service;

import java.util.HashMap;
import java.util.Map;

import org.mortbay.log.Log;

/**
 * <p>
 * Utility Class that provides a lookup facility for registered bean services.
 * </p>
 *
 * @author ITGYO
 *
 **/
public class ServiceProvider
{
	private static HashMap<Object, Object> services;

	/**
	 * 
	 * <p>
	 * Retrieves the implementation of a Service based on the Service's interface.
	 * </p>
	 *
	 * @param <T> T
	 * @param clazz Class
	 * @return T
	 *
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(final Class<T> clazz)
	{
		if (services == null)
			return null;

		return (T) services.get(clazz.getName());

	}

	/**
	 * 
	 * <p>
	 * Used as the populator of available services.
	 * </p>
	 *
	 * @param inSservices Map<Object, Object>
	 *
	 */
	@SuppressWarnings("unchecked")
	public void setServices(final Map<Object, Object> inSservices)
	{
		ServiceProvider.services = (HashMap) inSservices;
	}

	
	public static HashMap testHashMap() {
		
		Log.info("services.is:" + services);
		
		return services;
	}
}
