package com.hutrace.j2ee.excl.e;

public enum PrintMsg {
	
	PrintSimpleDateFormat("MM月 dd, yyyy HH:mm:ss "), 
	Print(" 信息: "), 
	Read("读取 "), 
	Write("写入 "), 
	DataAllHave("数据共有 "), 
	Strip(" 条"), 
	StripDataSuccess(" 条数据成功"), 
	StripDataFial(" 条数据失败,请检查数据格式与JavaBean是否一致"), 
	ConsumeTime("耗时: "), 
	Millisecond(" 毫秒");
	
	private PrintMsg(String st) {
		str = st;
	}
	
	public String print() {
		return str;
	}
	
	private final String str;
	
}
