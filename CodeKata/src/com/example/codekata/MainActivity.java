package com.example.codekata;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView label;
	Spinner spinner;
	Button submit;
	ImageView splashImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		Resources resources = getResources();
		label = (TextView)findViewById(resources.getIdentifier("main_label", "id", "com.example.codekata"));
		spinner = (Spinner)findViewById(resources.getIdentifier("main_spinner", "id", "com.example.codekata"));
		submit = (Button)findViewById(resources.getIdentifier("main_submit", "id", "com.example.codekata"));
		splashImage = (ImageView)findViewById(resources.getIdentifier("main_splash_image", "id", "com.example.codekata"));
		
		if (splashImage != null) {
			splashImage.setVisibility(View.VISIBLE);
			Message msg = new Message();
			msg.what = CodeKataConfig.MESSAGE_SPLASH_STOP;
			splashHandler.sendMessageDelayed(msg, CodeKataConfig.MESSAGE_SPLASH_TIME);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}

	public void submitClicked(View v) {
		int index = spinner.getSelectedItemPosition();
		Class correspondingActivity = CodeKataConfig.instance().indexToActivityMap.get(index);
		if (correspondingActivity != null) {
			// If CodeKataConfig has an activity for the index the user chose, then start that activity
			Intent intent = new Intent(this, correspondingActivity);
	        startActivity(intent);
		} else {
			// If CodeKataConfig couldn't locate an activity for the user's selection, then show
			// a dialog notifying the user of this...
			Intent intent = new Intent(this, DialogActivity.class);
			intent.putExtra("messageId", R.string.cannot_start_activity);
			startActivity(intent);
		}
//		String selection = String.valueOf(spinner.getSelectedItemPosition());
//		label.setText(selection);
	}
	
	private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                switch (msg.what) {
                case CodeKataConfig.MESSAGE_SPLASH_STOP:
                    //remove SplashScreen from view
                	if (splashImage != null) {
                		splashImage.setVisibility(View.GONE);
                	}
                	break;
                }
                super.handleMessage(msg);
        }
};
}
