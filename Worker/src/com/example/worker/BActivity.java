package com.example.worker;

import com.example.worker.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
//        mStatusView = (TextView)findViewById(R.id.status_view_b);
//        mStatusAllView = (TextView)findViewById(R.id.status_view_all_b);
    }
    
    public void exit(View v) {
    	this.finish();
    }
}
