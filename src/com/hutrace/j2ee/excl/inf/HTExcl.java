package com.hutrace.j2ee.excl.inf;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface HTExcl {

	public <T> List<T> readXlsxForBean(Class<?> clazz, String path) throws IOException;
	
	public <T> List<T> readXlsForBean(Class<?> clazz, String path) throws IOException;
	
	public List<Map<String, Object>> readXlsxForMap(Class<?> clazz, String path) throws IOException;
	
	public List<Map<String, Object>> readXlsForMap(Class<?> clazz, String path) throws IOException;
	
	public List<Map<String, Object>> readXlsxForMap(String path) throws IOException;
	
	public List<Map<String, Object>> readXlsForMap(String path) throws IOException;
	
}
