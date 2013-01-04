package com.example.codekata;

import java.util.HashMap;

import android.util.SparseArray;

public class CodeKataConfig {
	public SparseArray<Class> indexToActivityMap;
	public static final int ERROR_USER_CANCELLED = -2;
	public static final int ERROR_NOT_FOUND = -1;
	public static final float ERROR_FLOAT_TOLERANCE = 1e-7f;

	private CodeKataConfig() {
		indexToActivityMap = new SparseArray<Class>();
		indexToActivityMap.append(0, CodeKataActivityBase.class);
		indexToActivityMap.append(1, CodeKataActivityOne.class);
		indexToActivityMap.append(2, CodeKataActivityTwo.class);
	}
	
	private static CodeKataConfig instance = null;	
	public static CodeKataConfig instance() {
		if (null == instance) {
			instance = new CodeKataConfig();
		}
		return instance;
	}
}
