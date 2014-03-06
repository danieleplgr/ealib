package com.ealib.utils.csv;

import java.util.ArrayList;
import java.util.List; 

public abstract class CsvDataPreparer<T extends Object> {

	public List<String[]> prepare(List<T> listObjects){
		List<String[]> list = new ArrayList<String[]>();
		
		int i = 0; 
		
		for (T obj : listObjects) {
			if(i==0)
				list.add(getHeader());
			String[] stringValuesObject = prepareLine(obj);
			list.add(stringValuesObject);
			i++;
		} 
		return list;
	}
	
	public abstract String[] prepareLine(T obj);
	
	public abstract String[] getHeader();
	
}
