package com.example.codekata;

public class CodeKataActivityOne extends CodeKataActivityBase {
	public CodeKataActivityOne() {
		super();
		kataId = 1;
		numTextInputs = 1;
	}
	
	protected int getLayoutId() {
		return R.layout.code_kata_activity_one;
	}
	
	protected CodeKataTaskBase getNewCodeKataTask() {
		return new CodeKataTaskOne(this);
	}
}
