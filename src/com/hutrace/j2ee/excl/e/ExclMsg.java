package com.hutrace.j2ee.excl.e;


public enum ExclMsg {
	
	LengthError("传入的标题与数据列数不匹配,"), 
	NullDataError("传入的数据为空,不能创建文件,"), 
	Begin("执行操作开始,请稍等..."), 
	End("执行操作结束,"), 
	ReadData("读取数据成功,创建文件中,请稍后..."), 
	AddFileFinish("创建文件完成..."), 
	AndFileError("储存文件失败,请检测路径,"), 
	TransformationError("转换错误: "), 
	InstantiationError("实例化对象失败..."), 
	IllegalAccessError("安全级别异常...请检查您的JavaBean内方法是否为public"), 
	IntrospectionError("实例化对象失败..."), 
	GetDataFailError("获取数据失败");
	
	private ExclMsg(String st) {
		str = st;
	}
	
	public String error() {
		return str;
	}
	
	private final String str;
	
}
