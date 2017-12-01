package com.hutrace.j2ee.excl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.hutrace.j2ee.excl.e.ParameterList;
import com.hutrace.j2ee.excl.imp.ExclImport;
import com.hutrace.j2ee.excl.imp.HTExportImp;
import com.hutrace.j2ee.excl.imp.MonitorExclImport;
import com.hutrace.j2ee.excl.inf.HTExcl;
import com.hutrace.j2ee.excl.inf.HTExport;

/**
 * 
 * 读取excl数据主类
 * <br>导出数据至excl
 * <br>创建此对象即可调用方法进行读取数据
 * @author HuTrace
 * @since JDK1.7
 * @version 1.0
 * <br>创建时间: 2016-8-30
 * <br>文件名: ExclInfo.java
 * <br>包路径: com.hutrace.j2ee.excl
 * <br>项目名: excl_hutrace
 * <br><b>调用必要包</b>: 
 * &emsp;&emsp;dom4j-1.6.1.jar,
 * &emsp;&emsp;poi-3.8-20120326.jar,
 * &emsp;&emsp;poi-ooxml-3.8-20120326.jar,
 * &emsp;&emsp;poi-ooxml-schemas-3.8-20120326.jar,
 * &emsp;&emsp;xmlbeans-2.3.0.jar
 */
public class ExclInfo {
	
	private HTExcl excl;
	private HTExport export;
	
	/**
	 * 获取excl数据，需要传入实体类
	 * @author HuTrace
	 * @param zclass 需要传入实体类
	 * @param exclPath 文件路劲
	 * @return List<Map> 返回JavaBean集合
	 */
	public <T> List<T> exclBean(Class<?> zclass, String exclPath) {
		return exclBean(zclass, exclPath, false);
	}
	
	/**
	 * 获取excl数据，需要传入实体类
	 * @author HuTrace
	 * @param zclass 需要传入实体类
	 * @param exclPath 文件路劲
	 * @return List<Map> 返回JavaBean集合
	 */
	public <T> List<T> exclBean(Class<?> zclass, String exclPath, boolean arg0) {
		if(ParameterList.nullStr.equals(exclPath) || exclPath == null) return null;
		if(zclass == null) return null;
		int exclType = exclPath.substring(exclPath.lastIndexOf('.') + 1, exclPath.length()).length();
		excl = arg0 == true ? new MonitorExclImport() : new ExclImport();
		try {
			if(exclType == 3) return excl.readXlsForBean(zclass, exclPath);
			else if(exclType == 4) return excl.readXlsxForBean(zclass, exclPath);
			else return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取excl数据，需要传入实体类，返回数据按照实体类的字段进行取值
	 * @author HuTrace
	 * @param zclass 需要传入实体类
	 * @param exclPath 文件路劲
	 * @return List<Map> 返回Map集合
	 */
	public List<Map<String, Object>> exclMap(Class<?> zclass, String exclPath) {
		return exclMap(zclass, exclPath, false);
	}
	
	/**
	 * 获取excl数据，需要传入实体类，返回数据按照实体类的字段进行取值
	 * @author HuTrace
	 * @param zclass 需要传入实体类
	 * @param exclPath 文件路劲
	 * @return List<Map> 返回Map集合
	 */
	public List<Map<String, Object>> exclMap(Class<?> zclass, String exclPath, boolean arg0) {
		if(ParameterList.nullStr.equals(exclPath) || exclPath == null) return null;
		if(zclass == null) return exclMap(exclPath);
		int exclType = exclPath.substring(exclPath.lastIndexOf('.') + 1, exclPath.length()).length();
		excl = arg0 == true ? new MonitorExclImport() : new ExclImport();
		try {
			if(exclType == 3) return excl.readXlsForMap(zclass, exclPath);
			else if(exclType == 4) return excl.readXlsxForMap(zclass, exclPath);
			else return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取excl数据，不需要传入实体类，返回数据按data1,data2...至数据条数进行取值
	 * @author HuTrace
	 * @param exclPath 文件路劲
	 * @return List<Map> 返回Map集合
	 */
	public List<Map<String, Object>> exclMap(String exclPath) {
		return exclMap(exclPath, false);
	}
	
	/**
	 * 获取excl数据，不需要传入实体类，返回数据按data1,data2...至数据条数进行取值
	 * @author HuTrace
	 * @param exclPath 文件路劲
	 * @return List<Map> 返回Map集合
	 */
	public List<Map<String, Object>> exclMap(String exclPath, boolean arg0) {
		if(ParameterList.nullStr.equals(exclPath) || exclPath == null) return null;
		int exclType = exclPath.substring(exclPath.lastIndexOf('.') + 1, exclPath.length()).length();
		excl = arg0 == true ? new MonitorExclImport() : new ExclImport();
		try {
			if(exclType == 3) return excl.readXlsForMap(exclPath);
			else if(exclType == 4) return excl.readXlsxForMap(exclPath);
			else return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 数据导出excl文件,可开启实时添加数据监控,arg布尔值为true时开启,不开启则可以不传值
	 * @author HuTrace
	 * @param sheetName 页名,如果不设置则不传值
	 * @param title 字符串类型的集合,代表excl表头的字段,如果不设置则不传值 <br>
	 * <b>注意: 集合顺序得与数据顺序对应</b>
	 * @param data 数据集,JavaBean集合
	 * @param clazz JavaBean.class
	 * @param arg 布尔值为true时开启,不开启则可以不传值,也可以传入false
	 * @param arg1 导出文档路劲
	 */
	public <T> void exportExcl(String sheetName, List<String> title, 
			List<T> data, Class<?> clazz, boolean arg, String arg1) {
		export = new HTExportImp();
		export.exportExcl(sheetName, title, data, clazz, arg, arg1);
	}
	
	/**
	 * 数据导出excl文件,可开启实时添加数据监控,arg布尔值为true时开启,不开启则可以不传值
	 * @author HuTrace
	 * @param title 字符串类型的集合,代表excl表头的字段,如果不设置则不传值 <br>
	 * <b>注意: 集合顺序得与数据顺序对应</b>
	 * @param data 数据集,JavaBean集合
	 * @param clazz JavaBean.class
	 * @param arg 布尔值为true时开启,不开启则可以不传值,也可以传入false
	 * @param arg1 导出文档路劲
	 */
	public <T> void exportExcl(List<String> title, List<T> data,
			Class<?> clazz, boolean arg, String arg1) {
		export = new HTExportImp();
		export.exportExcl(null, title, data, clazz, arg, arg1);
	}

	/**
	 * 数据导出excl文件,可开启实时添加数据监控,arg布尔值为true时开启,不开启则可以不传值
	 * @author HuTrace
	 * @param sheetName 页名,如果不设置则不传值
	 * @param data 数据集,JavaBean集合
	 * @param clazz JavaBean.class
	 * @param arg 布尔值为true时开启,不开启则可以不传值,也可以传入false
	 * @param arg1 导出文档路劲
	 */
	public <T> void exportExcl(String sheetName, List<T> data, Class<?> clazz,
			boolean arg, String arg1) {
		export = new HTExportImp();
		export.exportExcl(sheetName, null, data, clazz, arg, arg1);
		
	}

	/**
	 * 数据导出excl文件,可开启实时添加数据监控,arg布尔值为true时开启,不开启则可以不传值
	 * @author HuTrace
	 * @param data 数据集,JavaBean集合
	 * @param clazz JavaBean.class
	 * @param arg 布尔值为true时开启,不开启则可以不传值,也可以传入false
	 * @param arg1 导出文档路劲
	 */
	public <T> void exportExcl(List<T> data, Class<?> clazz, boolean arg, String arg1) {
		export = new HTExportImp();
		export.exportExcl(null, null, data, clazz, arg, arg1);
	}

	/**
	 * 数据导出excl文件,可开启实时添加数据监控,arg布尔值为true时开启,不开启则可以不传值
	 * @author HuTrace
	 * @param sheetName 页名,如果不设置则不传值
	 * @param title 字符串类型的集合,代表excl表头的字段,如果不设置则不传值 <br>
	 * <b>注意: 集合顺序得与数据顺序对应</b>
	 * @param data 数据集,JavaBean集合
	 * @param clazz JavaBean.class
	 * @param arg1 导出文档路劲
	 */
	public <T> void exportExcl(String sheetName, List<String> title,
			List<T> data, Class<?> clazz, String arg1) {
		export = new HTExportImp();
		export.exportExcl(sheetName, title, data, clazz, false, arg1);
	}

	/**
	 * 数据导出excl文件,可开启实时添加数据监控,arg布尔值为true时开启,不开启则可以不传值
	 * @author HuTrace
	 * @param title 字符串类型的集合,代表excl表头的字段,如果不设置则不传值 <br>
	 * <b>注意: 集合顺序得与数据顺序对应</b>
	 * @param data 数据集,JavaBean集合
	 * @param clazz JavaBean.class
	 * @param arg1 导出文档路劲
	 */
	public <T> void exportExcl(List<String> title, List<T> data, Class<?> clazz, String arg1) {
		export = new HTExportImp();
		export.exportExcl(null, title, data, clazz, false, arg1);
	}

	/**
	 * 数据导出excl文件,可开启实时添加数据监控,arg布尔值为true时开启
	 * @author HuTrace
	 * @param sheetName 页名,如果不设置则不传值
	 * @param data 数据集,JavaBean集合
	 * @param clazz JavaBean.class
	 * @param arg1 导出文档路劲
	 */
	public <T> void exportExcl(String sheetName, List<T> data, Class<?> clazz, String arg1) {
		export = new HTExportImp();
		export.exportExcl(sheetName, null, data, clazz, false, arg1);
	}

	/**
	 * 数据导出excl文件,可开启实时添加数据监控,arg布尔值为true时开启
	 * @author HuTrace
	 * @param data 数据集,JavaBean集合
	 * @param clazz JavaBean.class
	 * @param arg1 导出文档路劲
	 */
	public <T> void exportExcl(List<T> data, Class<?> clazz, String arg1) {
		export = new HTExportImp();
		export.exportExcl(null, null, data, clazz, false, arg1);
	}
}
