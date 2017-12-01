package com.hutrace.api.note;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * 在Bean实体类里面需要获取的字段进行注解
 * @author HuTrace
 * @since JDK1.7
 * @version 1.0
 * <br>创建时间: 2016年8月31日
 * <br>文件名: HTField.java
 * <br>包路径: com.hutrace.api.note
 * <br>项目名: excl_hutrace
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HTField {
	
}
