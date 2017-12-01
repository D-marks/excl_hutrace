package com.hutrace.j2ee.excl.imp;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hutrace.api.Baisc;
import com.hutrace.api.note.HTFiledInfo;
import com.hutrace.j2ee.excl.e.ExclMsg;
import com.hutrace.j2ee.excl.e.ParameterList;
import com.hutrace.j2ee.excl.e.PrintMsg;
import com.hutrace.j2ee.excl.inf.HTExport;

/**
 * 外部导出数据为Excl文件
 * @author HuTrace
 * @since JDK1.7
 * @version 1.0
 * <br>创建时间: 2016年9月30日
 * <br>文件名: HTExportImp.java
 * <br>包路径: com.hutrace.j2ee.excl.imp
 * <br>项目名: excl_hutrace
 */
public class HTExportImp implements HTExport {
	
	private final String SimpleDateFormat = "yyyy/MM/dd";

	
	@Override
	public <T> void exportExcl(String sheetName, List<String> title,
			List<T> data, Class<?> clazz, boolean arg, String arg1) {
		if(arg) exportExclMonitor(sheetName, title, data, clazz, arg1);
		else exportExclNoMonitor(sheetName, title, data, clazz, arg1);
	}
	
	@SuppressWarnings("unchecked")
	public <T> void exportExclMonitor(String sheetName, List<String> title, 
			List<T> data, Class<?> clazz, String arg1) {
		Baisc.println(ExclMsg.Begin.error());
		long a;//时间
		Map<String, Object> map;//JavaBean中的变量数量、变量类型与变量名称
		int length;//JavaBean的变量数量
		List<String> valueList;//JavaBean中的变量名称
		List<String> typeList;//JavaBean中的变量类型
		HSSFWorkbook wb;//EXCL对象
		HSSFSheet sheet;//EXCL文档的某页
		HSSFRow row;//某行
		HSSFCellStyle style;//样式
		HSSFCell cell;//某格
		FileOutputStream fos;
		a = System.currentTimeMillis();
		if (clazz == null) {
			Baisc.println(ExclMsg.End.error());
			return;
		}
		map = HTFiledInfo.getFieldInfo(clazz);
		length = (int) map.get(ParameterList.beanLength);
		if (!checkParams(title, data, length)) {
			Baisc.println(ExclMsg.End.error());
		}
		valueList = (List<String>) map.get(ParameterList.beanValueList);
		typeList = (List<String>) map.get(ParameterList.beanTypeList);
		wb = new HSSFWorkbook();// 创建Excl
		sheet = wb.createSheet((ParameterList.nullStr).equals(sheetName) || sheetName == null ? ParameterList.sheetOne : sheetName);// 创建页名
		row = sheet.createRow((int) 0);// 创建第一行
		style = wb.createCellStyle();// Excl样式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		if(title != null && title.size() != 0) {
			for (int i = 0; i < length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(title.get(i));
				cell.setCellStyle(style);
			}
		}
		Baisc.println(PrintMsg.DataAllHave.print() + data.size() + PrintMsg.Strip.print());
		for (int i = 0; i < data.size(); i++) {
			row = sheet.createRow((int) i + 1);
			map = Baisc.BeanToMap(data.get(i));
			for (int j = 0; j < length; j++) {
				switch (typeList.get(j)) {
					case ParameterList.dateType:
						row.createCell(j).setCellValue(new SimpleDateFormat(SimpleDateFormat).format(map.get(valueList.get(j))));
						break;
					case ParameterList.integerType:
					case ParameterList.number:
					case ParameterList.LongType:
					case ParameterList.longType:
					case ParameterList.DoubleType:
					case ParameterList.doubleType:
						row.createCell(j).setCellValue(Double.parseDouble(map.get(valueList.get(j)).toString()));
						break;
					case ParameterList.BooleanType:
					case ParameterList.booleanType:
						row.createCell(j).setCellValue(ParameterList.trueStr.equals(map.get(valueList.get(j))) ? true : false);
						break;
					default:
						row.createCell(j).setCellValue(map.get(valueList.get(j)).toString());
						break;
				}
			}
			Baisc.println(PrintMsg.Write.print() + (i+1) + PrintMsg.StripDataSuccess.print());
		}
		try {
			fos = new FileOutputStream(arg1);
			wb.write(fos);
			Baisc.println(ExclMsg.AddFileFinish.error());
			Baisc.println(ExclMsg.End.error() + PrintMsg.ConsumeTime.print() + (System.currentTimeMillis() - a) + PrintMsg.Millisecond.print());
			fos.close();
		}catch (Exception e) {
			Baisc.println(ExclMsg.AndFileError.error());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> void exportExclNoMonitor(String sheetName, List<String> title, 
			List<T> data, Class<?> clazz, String arg1) {
		Baisc.println(ExclMsg.Begin.error());
		long a;//时间
		Map<String, Object> map;//JavaBean中的变量数量、变量类型与变量名称
		int length;//JavaBean的变量数量
		List<String> valueList;//JavaBean中的变量名称
		List<String> typeList;//JavaBean中的变量类型
		HSSFWorkbook wb;//EXCL对象
		HSSFSheet sheet;//EXCL文档的某页
		HSSFRow row;//某行
		HSSFCellStyle style;//样式
		HSSFCell cell;//某格
		FileOutputStream fos;
		a = System.currentTimeMillis();
		if (clazz == null) {
			Baisc.println(ExclMsg.End.error());
			return;
		}
		map = HTFiledInfo.getFieldInfo(clazz);
		length = (int) map.get(ParameterList.beanLength);
		if (!checkParams(title, data, length)) {
			Baisc.println(ExclMsg.End.error());
			return;
		}
		valueList = (List<String>) map.get(ParameterList.beanValueList);
		typeList = (List<String>) map.get(ParameterList.beanTypeList);
		wb = new HSSFWorkbook();// 创建Excl
		sheet = wb.createSheet((ParameterList.nullStr).equals(sheetName) || sheetName == null ? ParameterList.sheetOne : sheetName);// 创建页名
		row = sheet.createRow((int) 0);// 创建第一行
		style = wb.createCellStyle();// Excl样式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		if(title != null && title.size() != 0) {
			for (int i = 0; i < length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(title.get(i));
				cell.setCellStyle(style);
			}
		}
		Baisc.println(PrintMsg.DataAllHave.print() + data.size() + PrintMsg.Strip.print());
		for (int i = 0; i < data.size(); i++) {
			row = sheet.createRow((int) i + 1);
			map = Baisc.BeanToMap(data.get(i));
			for (int j = 0; j < length; j++) {
				switch (typeList.get(j)) {
					case ParameterList.dateType:
						row.createCell(j).setCellValue(new SimpleDateFormat(SimpleDateFormat).format(map.get(valueList.get(j))));
						break;
					case ParameterList.integerType:
					case ParameterList.number:
					case ParameterList.LongType:
					case ParameterList.longType:
					case ParameterList.DoubleType:
					case ParameterList.doubleType:
						row.createCell(j).setCellValue(Double.parseDouble(map.get(valueList.get(j)).toString()));
						break;
					case ParameterList.BooleanType:
					case ParameterList.booleanType:
						row.createCell(j).setCellValue(ParameterList.trueStr.equals(map.get(valueList.get(j))) ? true : false);
						break;
					default:
						row.createCell(j).setCellValue(map.get(valueList.get(j)).toString());
						break;
				}
			}
		}
		try {
			fos = new FileOutputStream(arg1);
			wb.write(fos);
			Baisc.println(ExclMsg.AddFileFinish.error());
			Baisc.println(ExclMsg.End.error() + PrintMsg.ConsumeTime.print() + (System.currentTimeMillis() - a) + PrintMsg.Millisecond.print());
			fos.close();
		}catch (Exception e) {
			Baisc.println(ExclMsg.AndFileError.error());
			e.printStackTrace();
		}
	}

	public <T> boolean checkParams(List<String> title, List<T> data, int length) {
		if (title.size() != length) {
			Baisc.println(ExclMsg.LengthError.error());
			return false;
		}
		if (data == null || data.size() == 0) {
			Baisc.println(ExclMsg.NullDataError.error());
			return false;
		}
		return true;
	}

}
