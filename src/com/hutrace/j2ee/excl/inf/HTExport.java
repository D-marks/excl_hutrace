package com.hutrace.j2ee.excl.inf;

import java.util.List;

/**
 * 数据导出excl文件
 * @author HuTrace
 * @version 1.0
 * <br>jdk_version 1.7
 * <br>创建时间: 2016年8月30日
 */
public interface HTExport {
	
	/**
	 * 数据导出excl文件,可开启实时添加数据监控,arg布尔值为true时开启,不开启则可以不传值
	 * @author HuTrace
	 * @param sheetName 页名,如果不设置则不传值
	 * @param title 字符串类型的集合,代表excl表头的字段,如果不设置则不传值 <br>
	 * <b>注意: 集合顺序得与数据顺序对应</b>
	 * @param data 数据集,JavaBean集合
	 * @param clazz JavaBean.class
	 * @param arg true开启步骤监控
	 * @param arg1 导出文档路劲
	 */
	public <T> void exportExcl(String sheetName, List<String> title, List<T> data, Class<?> clazz, boolean arg, String arg1);
	
}
