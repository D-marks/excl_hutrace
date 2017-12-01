package com.hutrace.api.note;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hutrace.j2ee.excl.e.ParameterList;

/**
 * 操作注解
 * @author HuTrace
 * @since JDK1.7
 * @version 1.0
 * <br>创建时间: 2016-8-30
 * <br>文件名: HTFiledInfo.java
 * <br>包路径: com.hutrace.api.note
 * <br>项目名: excl_hutrace
 */
public class HTFiledInfo {
	
	/**
	 * @author HuTrace
	 * @方法说明 获取Bean对象的需要操作字段
	 * @param clazz
	 * @return Map
	 */
	public static Map<String, Object> getFieldInfo(Class<?> clazz) {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = clazz.getDeclaredFields();
		List<String> valueList = new ArrayList<String>();
		List<String> typeList = new ArrayList<String>();
		int length = 0;
		for (Field field : fields) {
			if (field.getAnnotation(HTField.class) != null) {
				length ++;
				valueList.add(field.getName());
				String type = field.getType().getName();
				typeList.add(type.substring(type.lastIndexOf('.') + 1, type.length()));
			}
		}
		map.put(ParameterList.beanLength, length);
		map.put(ParameterList.beanValueList, valueList);
		map.put(ParameterList.beanTypeList, typeList);
		return map;
	}
}
