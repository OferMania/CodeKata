package com.example.codekata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CodeKataActivityBase extends Activity implements TaskNotificationInterface {

	TextView statusView;
	Button taskButton;
	
	CodeKataTaskBase codeKataTask;
	boolean taskIsRunning;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.code_kata_activity_base);
		statusView = (TextView)findViewById(R.id.ck0_status_view);
		taskButton = (Button)findViewById(R.id.ck0_task_btn);
		taskIsRunning = false;
	}
	
	protected void onDestroy() {
		if (taskIsRunning && (codeKataTask != null)) {
			codeKataTask.cancel(true);
		}
		super.onDestroy();
	}
	
	public void pingTask(View v) {
		if (!taskIsRunning) {
			codeKataTask = new CodeKataTaskBase(this);
			taskIsRunning = true;
			codeKataTask.execute("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
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
