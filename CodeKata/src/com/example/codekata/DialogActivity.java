package com.example.codekata;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class DialogActivity extends Activity {
	TextView dialogText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	setFinishOnTouchOutside(false);
        }
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_activity);
        dialogText = (TextView)findViewById(R.id.dialog_text);
        
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
        	if (bundle.containsKey("messageId")) {
        		int messageId = bundle.getInt("messageId");
        		dialogText.setText(messageId);
        	}
        }
    }

    /**
     * Callback method defined by the View
     * @param v
     */
    public void okay(View v) {
        DialogActivity.this.finish();
    }
}
