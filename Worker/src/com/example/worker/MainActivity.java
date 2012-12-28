package com.example.worker;

import com.example.worker.BActivity;
import com.example.worker.DialogActivity;
import com.example.worker.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements WorkerNotificationInterface {

	Context context;
	TextView statusView;
	Button buttonWorker;
	
	MainWorker mainWorker;
	boolean workerIsRunning;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = MainActivity.this;
		statusView = (TextView)findViewById(R.id.status_view);
		buttonWorker = (Button)findViewById(R.id.btn_worker);
		workerIsRunning = false;
//        mStatusView = (TextView)findViewById(R.id.status_view_a);
//        mStatusAllView = (TextView)findViewById(R.id.status_view_all_a);
	}
	
	public void pingWorker(View v) {
		if (!workerIsRunning) {
			mainWorker = new MainWorker(this);
			workerIsRunning = true;
			mainWorker.execute("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
			buttonWorker.setText(R.string.btn_worker_stop_label);
		} else if (mainWorker != null) {
			mainWorker.cancel(true);
		}
	}

    public void startDialog(View v) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }

    public void startActivityB(View v) {
        Intent intent = new Intent(this, BActivity.class);
        startActivity(intent);
    }

    public void exit(View v) {
    	this.finish();
    }

    public void workerEnded() {
    	workerIsRunning = false;
		buttonWorker.setText(R.string.btn_worker_start_label);
    }
    
    public void workerGotCancelled() {
    	workerEnded();
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
