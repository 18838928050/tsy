package com.ssm.tsy.util;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ssm.tsy.bean.TsyScillorPic;

/**
 * 集合排序
 * @author asus
 *
 * @param <T>
 */
public class ListSortUtil<T> {
	/**
	 * @param targetList
	 *            目标排序List
	 * @param sortField
	 *            排序字段(实体类属性名)
	 * @param sortMode
	 *            排序方式（asc or desc）
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sort(List<T> targetList, final String sortField, final String sortMode) {
		Collections.sort(targetList, new Comparator() {
			public int compare(Object obj1, Object obj2) {
				int retVal = 0;
				try {
					// 首字母转大写
					String newStr = sortField.substring(0, 1).toUpperCase() + sortField.replaceFirst("\\w", "");
					String methodStr = "get" + newStr;
					Method method1 = ((T) obj1).getClass().getMethod(methodStr, null);
					Method method2 = ((T) obj2).getClass().getMethod(methodStr, null);
					if (sortMode != null && "desc".equals(sortMode)) {
						retVal = method2.invoke(((T) obj2), null).toString().compareTo(method1.invoke(((T) obj1), null).toString()); // 倒序
					} else {
						retVal = method1.invoke(((T) obj1), null).toString().compareTo(method2.invoke(((T) obj2), null).toString()); // 正序
					}
				} catch (Exception e) {
					throw new RuntimeException();
				}
				return retVal;
			}
		});
	}
	
	/**
	 * 返回公告list线上的长度
	 * @return
	 */
	public int List_Size(List<TsyScillorPic> targetList){
		int size = 0;
		for(TsyScillorPic bean : targetList){
			if(bean.getScollorPicDisplay()==Integer.parseInt(Constants.TSY_SCOLLOR_PIC_STATE_SX)){//上线
				size++;
			}else{
				break;
			}
		}
		return size;
	}

}
