package com.example.codekata;

import java.util.HashMap;

import android.util.SparseArray;

public class CodeKataConfig {
	public SparseArray<Class> indexToActivityMap;
	private CodeKataConfig() {
		indexToActivityMap = new SparseArray<Class>();
		indexToActivityMap.append(0, CodeKataActivityBase.class);
	}
	
	private static CodeKataConfig instance = null;	
	public static CodeKataConfig instance() {
		if (null == instance) {
			instance = new CodeKataConfig();
		}
		return instance;
	}
}
