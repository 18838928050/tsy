package com.ssm.tsy.object;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



public class InputObject extends PutObject {

	public static Map<String, Object> map;
	public static Set<String> keySet;
	

	public InputObject() {
		map = new HashMap<String, Object>();
		setParams();
	}

	@SuppressWarnings("unchecked")
	public static void setParams(){
		//以Map集合存储页面表单传递过来的所有参数的键值对
		Map<String, String[]> formMap = getRequest().getParameterMap();
		keySet = formMap.keySet();
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()){
			String key = it.next();
			String[] value = (String[]) formMap.get(key);
			StringBuffer stb = new StringBuffer();
			for(int i = 0;i<value.length;i++){
				stb.append(value[i]);
			}
			map.put(key, stb.toString());
		}
	}
	
	public static void setParams(Map<String, Object> map){
		setMap(map);
	}

	public Map<String, Object> getParams() {
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getLogParams() throws Exception {
		return (Map<String, Object>) getRequest().getSession().getAttribute("admTsyUser");
	}
	
	public Set<String> getKeyForRequestMap(){
		return keySet;
	}

	public static Map<String, Object> getMap() {
		return map;
	}

	public static void setMap(Map<String, Object> map) {
		InputObject.map = map;
	}

	public static Set<String> getKeySet() {
		return keySet;
	}

	public static void setKeySet(Set<String> keySet) {
		InputObject.keySet = keySet;
	}
}
