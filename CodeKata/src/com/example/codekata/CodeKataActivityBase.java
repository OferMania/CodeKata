package com.example.codekata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CodeKataActivityBase extends Activity implements TaskNotificationInterface {

	TextView statusView;
	Button taskButton;
	EditText[] textInputs;
	
	CodeKataTaskBase codeKataTask;
	boolean taskIsRunning;
	
	int kataId;
	int numTextInputs;
	
	public CodeKataActivityBase() {
		super();
		kataId = 0;
		numTextInputs = 2;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		Resources resources = getResources();
		statusView = (TextView)findViewById(resources.getIdentifier(getStatusViewIdentifier(), "id", "com.example.codekata"));
		taskButton = (Button)findViewById(resources.getIdentifier(getTaskButtonIdentifier(), "id", "com.example.codekata"));
		if (numTextInputs >= 1) {
			textInputs = new EditText[numTextInputs];
			for (int ii = 1; ii <= numTextInputs; ++ii) {
				textInputs[ii-1] = (EditText)findViewById(resources.getIdentifier(getTextInputIdentifier(ii), "id", "com.example.codekata"));
			}
		}
		taskIsRunning = false;
	}
	
	protected void onDestroy() {
		if (taskIsRunning && (codeKataTask != null)) {
			codeKataTask.cancel(true);
		}
		super.onDestroy();
	}
	
	protected int getLayoutId() {
		return R.layout.code_kata_activity_base;
	}
	
	protected CodeKataTaskBase getNewCodeKataTask() {
		return new CodeKataTaskBase(this);
	}
	
	private String getStatusViewIdentifier() {
		StringBuffer buffer = new StringBuffer("ck");
		buffer.append(String.valueOf(kataId));
		buffer.append("_status_view");
		return buffer.toString();
	}
	
	private String getTaskButtonIdentifier() {
		StringBuffer buffer = new StringBuffer("ck");
		buffer.append(String.valueOf(kataId));
		buffer.append("_task_btn");
		return buffer.toString();
	}
	
	private String getTextInputIdentifier(int inputNumber) {
		StringBuffer buffer = new StringBuffer("ck");
		buffer.append(String.valueOf(kataId));
		buffer.append("_input");
		buffer.append(String.valueOf(inputNumber));
		return buffer.toString();
	}
	
	public void pingTask(View v) {
		if (!taskIsRunning) {
			codeKataTask = getNewCodeKataTask();
			taskIsRunning = true;
			StringBuffer buffer = new StringBuffer();
			for (EditText textInput : textInputs) {
				if (textInput != null) {
					buffer.append(textInput.getText().toString());
					buffer.append('\n');
				}
			}
			
			String[] params = buffer.toString().split("\\n");
			// codeKataTask.execute("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
			codeKataTask.execute(params);
			taskButton.setText(R.string.btn_task_stop_label);
		} else if (codeKataTask != null) {
			codeKataTask.cancel(true);
		}
	}

//    public void startDialog(View v) {
//        Intent intent = new Intent(this, DialogActivity.class);
//        startActivity(intent);
//    }
//
//    public void startActivityB(View v) {
//        Intent intent = new Intent(this, BActivity.class);
//        startActivity(intent);
//    }

    public void back(View v) {
    	this.finish();
    }

    public void taskEnded() {
    	taskIsRunning = false;
    	taskButton.setText(R.string.btn_task_start_label);
    }
    
    public void taskGotCancelled() {
    	taskEnded();
    }
    
    public void setStatusText(String status) {
    	if (statusView != null) {
    		statusView.setText(status);
    	}
    }
    
    public void appendStatusText(String status) {
    	if (statusView != null) {
    		StringBuffer statusViewBuffer = new StringBuffer(statusView.getText());
    		statusViewBuffer.append(status);
    		statusView.setText(statusViewBuffer.toString());
    	}    	
    }

}
