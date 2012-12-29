package com.example.codekata;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView label;
	Spinner spinner;
	Button submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		Resources resources = getResources();
		label = (TextView)findViewById(resources.getIdentifier("main_label", "id", "com.example.codekata"));
		spinner = (Spinner)findViewById(resources.getIdentifier("main_spinner", "id", "com.example.codekata"));
		submit = (Button)findViewById(resources.getIdentifier("main_submit", "id", "com.example.codekata"));		
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
}
