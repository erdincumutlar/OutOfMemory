package com.patientkeeper.reflections;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

public class Reflection {
    
	private static final Logger log = Logger.getLogger(Reflection.class);
    
	public static <S> Iterable<S> findSubTypesOf(String pkg, Class<S> clazz) {
		Collection<S> collection = new ArrayList<S>();
		Reflections reflections = new Reflections(pkg);		
		for(Class<? extends S> type : reflections.getSubTypesOf(clazz)) {			
				try {
					Constructor<? extends S> ctor = type.getConstructor();
					S each = ctor.newInstance();
					collection.add(each);
				}
				catch (Throwable t) {
					log.error("error with calling constructor", t);
				}
		}
		return collection;
	}
}