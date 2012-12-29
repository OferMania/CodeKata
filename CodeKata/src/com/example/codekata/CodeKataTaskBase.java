package com.example.codekata;

import android.os.AsyncTask;

public class CodeKataTaskBase extends AsyncTask<String, String, String> {
	TaskNotificationInterface notifierActivity;
	
	public CodeKataTaskBase(TaskNotificationInterface _notifierActivity) {
		super();
		notifierActivity = _notifierActivity;
	}
	
    protected String doInBackground(String... commands) {
        int count = commands.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            ++totalSize;
            publishProgress(String.valueOf((int) (((i+1) / (float) count) * 100)));
            // Escape early if cancel() is called
            if (isCancelled()) break;
        }
        return String.valueOf(totalSize);
    }

    protected void onProgressUpdate(String... progress) {
    	if ((notifierActivity != null) && (progress != null) && (progress.length >= 1)) {
    		StringBuffer progressBuffer = new StringBuffer("COMPLETED ");
    		progressBuffer.append(progress[0]);
    		progressBuffer.append(" %");
    		notifierActivity.setStatusText(progressBuffer.toString());
    	}
    }

    protected void onCancelled() {
    	if (notifierActivity != null) {
    		super.onCancelled();
			notifierActivity.taskGotCancelled();
    	}
    }
    
    protected void onCancelled(String result) {
    	if (notifierActivity != null) {
    		StringBuffer progressBuffer = new StringBuffer("COMPLETED ");
    		progressBuffer.append(result);
    		progressBuffer.append(" UNITS. ");
    		progressBuffer.append("JOB INCOMPLETE!!!");
    		notifierActivity.setStatusText(progressBuffer.toString());
    		notifierActivity.taskGotCancelled();
    	}
    }
    
    protected void onPostExecute(String result) {
    	if (notifierActivity != null) {
    		StringBuffer progressBuffer = new StringBuffer("COMPLETED ");
    		progressBuffer.append(result);
    		progressBuffer.append(" UNITS. ");
    		progressBuffer.append("JOB FINISHED!!!!");
    		notifierActivity.setStatusText(progressBuffer.toString());
    		notifierActivity.taskEnded();
    	}
    }
}
