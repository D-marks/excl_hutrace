package com.hutrace.api.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hutrace.exception.DateInputException;

public class HTDate {
	
	/**
	 * 使用: 
	 * <br>调用getDate方法获取,
	 * <br>eg: java.util.Date = new com.hutrace.api.date.HTDate().getDate();
	 * @author HuTrace
	 * @Time 2017年3月14日
	 * @param str 传入字符串说明,
	 * <br>yyyy-MM-dd HH:mm:ss格式或者yyyy/MM/dd HH:mm:ss格式
	 * <br>参数位数最大可为年,最小为秒
	 * @throws DateInputException
	 */
	public HTDate(String str) throws DateInputException {
		length = str.length();
		if(length == 0) {
			new HTDate();
			return;
		}
		if(length == 4) {
			format = "yyyy";
		}else if(length > 4){
			index = str.indexOf('-');
			if(index > -1) 
				istype = 0;
			else {
				index = str.indexOf('/');
				if(index > -1) 
					istype = 1;
				else 
					throw new DateInputException("Incoming parameter error");
			}
			try {
				switch (istype) {
					case 0:
						switch (length) {
							case 7:
								format = "yyyy-MM";
								break;
							case 10:
								format = "yyyy-MM-dd";
								break;
							case 13:
								format = "yyyy-MM-dd HH";
								break;
							case 16:
								format = "yyyy-MM-dd HH:mm";
								break;
							case 19:
								format = "yyyy-MM-dd HH:mm:ss";
								break;
						}
						break;
					case 1:
						switch (length) {
							case 7:
								format = "yyyy/MM";
								break;
							case 10:
								format = "yyyy/MM/dd";
								break;
							case 13:
								format = "yyyy/MM/dd HH";
								break;
							case 16:
								format = "yyyy/MM/dd HH:mm";
								break;
							case 19:
								format = "yyyy/MM/dd HH:mm:ss";
								break;
						}
						break;
				}
			} catch (Exception e) {
				throw new DateInputException("Incoming parameter error");
			}
		}else {
			throw new DateInputException("length error: " + length);
		}
		try {
			sdf = new SimpleDateFormat(format);
			date = sdf.parse(str);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DateInputException("Incoming parameter error");
		}
	}
	
	public HTDate() throws DateInputException {
		try {
			date = new Date();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DateInputException("Incoming parameter error");
		}
	}
	
	public Date getDate() {
		return date;
	}
	
	private String format;
	
	private int istype;
	
	private int index, length;
	
	private SimpleDateFormat sdf;
	
	private Date date;
	
}
