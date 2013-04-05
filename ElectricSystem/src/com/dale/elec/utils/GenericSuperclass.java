package com.dale.elec.utils;
import java.lang.reflect.ParameterizedType;

public class GenericSuperclass {

	//类型转换
	@SuppressWarnings("rawtypes")
	public static Class ConverType(Class entity){
		ParameterizedType type = (ParameterizedType) entity.getGenericSuperclass();
		Class entityClass = (Class) type.getActualTypeArguments()[0];
		return entityClass;
	}
}
