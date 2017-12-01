package com.hutrace.j2ee.excl.imp;

import java.beans.IntrospectionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hutrace.api.Baisc;
import com.hutrace.api.note.HTFiledInfo;
import com.hutrace.j2ee.excl.e.ExclMsg;
import com.hutrace.j2ee.excl.e.ParameterList;
import com.hutrace.j2ee.excl.inf.HTExcl;

public final class ExclImport implements HTExcl {

	/**
	 * 读取2007等以上新版本excl文件
	 * @author HuTrace
	 * @param clazz 实体类
	 * @param path 文件路径
	 * @return List<JavaBean>
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> readXlsxForBean(Class<?> clazz, String path)
			throws IOException {
		Baisc.println(ExclMsg.Begin.error());
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<T> list = new ArrayList<T>();
		Map<String, Object> map = HTFiledInfo.getFieldInfo(clazz);
		int length = (int) map.get(ParameterList.beanLength);
		List<String> valueList = (List<String>) map.get(ParameterList.beanValueList);
		List<String> typeList = (List<String>) map.get(ParameterList.beanTypeList);
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) continue;
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				map = new HashMap<String, Object>();
				if (xssfRow != null) {
					for (int i = 0; i < length; i++) {
						String type = typeList.get(i);
						XSSFCell field = xssfRow.getCell(i);
						if(field == null) {
							map.put(valueList.get(i), getNull(type));
							continue;
						}
						map.put(valueList.get(i), getXlsxValue(type, field));
					}
				}
				try {
					list.add((T) Baisc.mapToBean(clazz, map));
				} catch (InstantiationException e) {
					Baisc.println(ExclMsg.InstantiationError.error());
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					Baisc.println(ExclMsg.IllegalAccessError.error());
					e.printStackTrace();
				} catch (IntrospectionException e) {
					Baisc.println(ExclMsg.IntrospectionError.error());
					e.printStackTrace();
				}
			}
		}
		Baisc.println(ExclMsg.End.error());
		return list;
	}
	
	/**
	 * 读取97/2003等老版本excl文件
	 * @author HuTrace
	 * @param clazz 实体类
	 * @param path 文件路径
	 * @return List<JavaBean>
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> readXlsForBean(Class<?> clazz, String path) throws IOException {
		Baisc.println(ExclMsg.Begin.error());
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<T> list = new ArrayList<T>();
		Map<String, Object> map = HTFiledInfo.getFieldInfo(clazz);
		int length = (int) map.get(ParameterList.beanLength);
		List<String> valueList = (List<String>) map.get(ParameterList.beanValueList);
		List<String> typeList = (List<String>) map.get(ParameterList.beanTypeList);
		map = new HashMap<String, Object>();
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) continue;
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				map = new HashMap<String, Object>();
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					for (int i = 0; i < length; i++) {
						String type = typeList.get(i);
						HSSFCell field = hssfRow.getCell(i);
						if(field == null) {
							map.put(valueList.get(i), getNull(type));
							continue;
						}
						map.put(valueList.get(i), getXlsValue(type, field));
					}
				}
				try {
					list.add((T) Baisc.mapToBean(clazz, map));
				} catch (InstantiationException e) {
					Baisc.println(ExclMsg.InstantiationError.error());
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					Baisc.println(ExclMsg.IllegalAccessError.error());
					e.printStackTrace();
				} catch (IntrospectionException e) {
					Baisc.println(ExclMsg.IntrospectionError.error());
					e.printStackTrace();
				}
			}
		}
		Baisc.println(ExclMsg.End.error());
		return list;
	}
	
	/**
	 * 读取2007以上版本excl文件
	 * @author HuTrace
	 * @param clazz 实体类
	 * @param path 文件路径
	 * @return List<Map>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> readXlsxForMap(Class<?> clazz, String path)
			throws IOException {
		Baisc.println(ExclMsg.Begin.error());
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = HTFiledInfo.getFieldInfo(clazz);
		int length = (int) map.get(ParameterList.beanLength);
		List<String> valueList = (List<String>) map.get(ParameterList.beanValueList);
		List<String> typeList = (List<String>) map.get(ParameterList.beanTypeList);
		map = new HashMap<String, Object>();
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) continue;
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				map = new HashMap<String, Object>();
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					for (int i = 0; i < length; i++) {
						String type = typeList.get(i);
						XSSFCell field = xssfRow.getCell(i);
						if(field == null) {
							map.put(valueList.get(i), getNull(type));
							continue;
						}
						map.put(valueList.get(i), getXlsxValue(type, field));
					}
				}
				list.add(map);
			}
		}
		Baisc.println(ExclMsg.End.error());
		return list;
	}

	/**
	 * 读取97/2003等老版本excl文件
	 * @author HuTrace
	 * @param clazz 实体类
	 * @param path 文件路径
	 * @return List<Map>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> readXlsForMap(Class<?> clazz, String path)
			throws IOException {
		Baisc.println(ExclMsg.Begin.error());
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = HTFiledInfo.getFieldInfo(clazz);
		int length = (int) map.get(ParameterList.beanLength);
		List<String> valueList = (List<String>) map.get(ParameterList.beanValueList);
		List<String> typeList = (List<String>) map.get(ParameterList.beanTypeList);
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) continue;
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				map = new HashMap<String, Object>();
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					for (int i = 0; i < length; i++) {
						String type = typeList.get(i);
						HSSFCell field = hssfRow.getCell(i);
						if(field == null) {
							map.put(valueList.get(i), getNull(type));
							continue;
						}
						map.put(valueList.get(i), getXlsValue(type, field));
					}
				}
				list.add(map);
			}
		}
		Baisc.println(ExclMsg.End.error());
		return list;
	}

	/**
	 * 读取2007以上版本excl文件
	 * @author HuTrace
	 * @param path 文件路径
	 * @return List<Map> 返回数据按data1,data2...至数据条数接收
	 */
	public List<Map<String, Object>> readXlsxForMap(String path)
			throws IOException {
		Baisc.println(ExclMsg.Begin.error());
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) continue;
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				map = new HashMap<String, Object>();
				if (xssfRow != null) {
					for (int i = 0; i < xssfRow.getLastCellNum(); i++) {
						XSSFCell field = xssfRow.getCell(i);
						if(field == null) {
							map.put(ParameterList.defaultMapKey + i, ParameterList.nullStr);
							continue;
						}
				        switch(field.getCellType()) {
					        case 0: 
					        	map.put(ParameterList.defaultMapKey + i, Double.valueOf(field.getNumericCellValue()));
					            break;
					        case 1: 
					        	map.put(ParameterList.defaultMapKey + i, field.getStringCellValue());
					            break;
					        case 2: 
					        	map.put(ParameterList.defaultMapKey + i, ParameterList.nullStr);
					            break;
					        case 3: 
					        	map.put(ParameterList.defaultMapKey + i, ParameterList.nullStr);
					            break;
					        case 4: 
					        	map.put(ParameterList.defaultMapKey + i, field.getBooleanCellValue());
					            break;
					        case 5: 
					        	map.put(ParameterList.defaultMapKey + i, ExclMsg.GetDataFailError.error());
					            break;
				        }
					}
				}
				list.add(map);
			}
		}
		Baisc.println(ExclMsg.End.error());
		return list;
	}

	/**
	 * 读取97/2003等老版本excl文件
	 * @author HuTrace
	 * @param path 文件路径
	 * @return List<Map> 返回数据按data1,data2...至数据条数接收
	 */
	public List<Map<String, Object>> readXlsForMap(String path)
			throws IOException {
		Baisc.println(ExclMsg.Begin.error());
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) continue;
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				map = new HashMap<String, Object>();
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					for (int i = 0; i < hssfRow.getLastCellNum(); i++) {
						HSSFCell field = hssfRow.getCell(i);
						if(field == null) {
							map.put(ParameterList.defaultMapKey + i, ParameterList.nullStr);
							continue;
						}
						switch(field.getCellType()) {
					        case 0: 
					        	map.put(ParameterList.defaultMapKey + i, Double.valueOf(field.getNumericCellValue()));
					            break;
					        case 1: 
					        	map.put(ParameterList.defaultMapKey + i, field.getStringCellValue());
					            break;
					        case 2: 
					        	map.put(ParameterList.defaultMapKey + i, ParameterList.nullStr);
					            break;
					        case 3: 
					        	map.put(ParameterList.defaultMapKey + i, ParameterList.nullStr);
					            break;
					        case 4: 
					        	map.put(ParameterList.defaultMapKey + i, field.getBooleanCellValue());
					            break;
					        case 5: 
					        	map.put(ParameterList.defaultMapKey + i, ExclMsg.GetDataFailError.error());
					            break;
				        }
					}
				}
				list.add(map);
			}
		}
		Baisc.println(ExclMsg.End.error());
		return list;
	}
	
	/**
	 * 读取xls单个字段
	 * @author HuTrace
	 * @param type 字段类型
	 * @param field excl字段
	 * @return java实体类变量
	 */
	public Object getXlsValue(String type, HSSFCell field) {
		switch (type) {
			case ParameterList.dateType:
				return field.getStringCellValue();
			case ParameterList.integerType:
			case ParameterList.number:
			case ParameterList.LongType:
			case ParameterList.longType:
			case ParameterList.DoubleType:
			case ParameterList.doubleType:
				return field.getNumericCellValue();
			case ParameterList.BooleanType:
			case ParameterList.booleanType:
				return field.getBooleanCellValue();
			default:
				return field.getStringCellValue();
		}
	}
	
	/**
	 * 读取xlsx单个字段
	 * @author HuTrace
	 * @param type
	 * @param field
	 * @return
	 */
	public Object getXlsxValue(String type, XSSFCell field) {
		switch (type) {
			case ParameterList.dateType:
				return field.getStringCellValue();
			case ParameterList.integerType:
			case ParameterList.number:
			case ParameterList.LongType:
			case ParameterList.longType:
			case ParameterList.DoubleType:
			case ParameterList.doubleType:
				return field.getNumericCellValue();
			case ParameterList.BooleanType:
			case ParameterList.booleanType:
				return field.getBooleanCellValue();
			default:
				return field.getStringCellValue();
		}
	}
	
	/**
	 * 如果字段为空，对JaveBean变量的赋值
	 * @author HuTrace
	 * @param type
	 * @return Obj
	 */
	public Object getNull(String type) {
		switch (type) {
			case ParameterList.dateType:
				return new Date();
			case ParameterList.integerType:
			case ParameterList.number:
			case ParameterList.LongType:
			case ParameterList.longType:
				return 0;
			case ParameterList.DoubleType:
			case ParameterList.doubleType:
				return 0.0;
			default:
				return ParameterList.nullStr;
		}
	}
	
}
