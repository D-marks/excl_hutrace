package com.hutrace.api;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hutrace.j2ee.excl.e.ExclMsg;
import com.hutrace.j2ee.excl.e.ParameterList;
import com.hutrace.j2ee.excl.e.PrintMsg;

/**
 * 封装较为常用的基本API
 * @author HuTrace
 * @since JDK1.7
 * @version 1.0
 * <br>创建时间: 2016年8月31日
 * <br>文件名: Baisc.java
 * <br>包路径: com.hutrace.api
 * <br>项目名: excl_hutrace
 */
public class Baisc {
	
	private static String beanVariableType;
	private static StackTraceElement[] temp;

	/**
	 * Map转JavaBean
	 * 
	 * @author HuTrace
	 * @param type
	 *            JaveBean.class
	 * @param map
	 *            map
	 * @return JavaBean
	 * @throws IntrospectionException
	 *             分析类属性失败
	 * @throws InstantiationException
	 *             实例化 JavaBean 失败
	 * @throws IllegalAccessException
	 *             实例化 JavaBean 失败
	 */
	public static <T> Object mapToBean(Class<?> type, Map<String, Object> map)
			throws IntrospectionException, InstantiationException,
			IllegalAccessException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象
		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				try {
					beanVariableType = descriptor.getReadMethod().getReturnType().toString();
					if(beanVariableType.indexOf(ParameterList.space) > 0)
						beanVariableType = beanVariableType.substring(beanVariableType.lastIndexOf(ParameterList.space)).trim();
					Object value = map.get(propertyName);
					if(ParameterList.integer.equals(beanVariableType) || ParameterList.number.equals(beanVariableType)) {
						Double dou = Double.parseDouble(value.toString());
						descriptor.getWriteMethod().invoke(obj, dou.intValue());
					}else if(ParameterList.date.equals(beanVariableType)) {
						descriptor.getWriteMethod().invoke(obj, strToDate(value.toString()));
					}else {
						Object[] args = new Object[1];
						args[0] = value;
						descriptor.getWriteMethod().invoke(obj, args);
					}
				} catch (IllegalArgumentException e) {
					throw new IntrospectionException(ExclMsg.TransformationError.error() + e.getMessage());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	/**
	 * JavaBean转Map
	 * @param data JavaBean对象
	 * @return Map对象
	 */
	public static <T> Map<String, Object> BeanToMap(T data) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(data.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					//得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(data);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			println("JavaBean To Map Error ... ");
		}
		return map;
	}

	/**
	 * 字符串转Date格式
	 * 
	 * @author HuTrace
	 * @param str
	 *            时间格式字符串
	 * @return 时间
	 */
	public static Date strToDate(String str) {
		str = str.trim();
		int a = 0;
		a = str.indexOf('/');
		if (a != -1) {
			str = str.replace('/', '-');
		}
		a = str.length();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
		switch (a) {
		case 8:
		case 9:
		case 10:
			str += " 00:00:00";
			break;
		default:
			break;
		}
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * 控制台打印信息
	 * 
	 * @author HuTrace
	 * @param o
	 *            Object
	 */
	public static void println(Object o) {
		temp = Thread.currentThread().getStackTrace();
		System.err.print(new SimpleDateFormat(PrintMsg.PrintSimpleDateFormat.print()).format(new Date()) + 
				temp[2].getClassName() + PrintMsg.Print.print() + o + "\n");
	}

}
