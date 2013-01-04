package com.example.codekata;

public class CodeKataActivityTwo extends CodeKataActivityBase {
	public CodeKataActivityTwo() {
		super();
		kataId = 2;
		numTextInputs = 1;
	}
	
	protected int getLayoutId() {
		return R.layout.code_kata_activity_two;
	}
	
	protected CodeKataTaskBase getNewCodeKataTask() {
		return new CodeKataTaskTwo(this);
	}
}
