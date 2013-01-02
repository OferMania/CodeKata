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
        boolean gotCancelled = false;
        StringBuffer progressBuffer = null;
        for (int i = 0; i < count; i++) {
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            ++totalSize;
            progressBuffer = new StringBuffer("COMPLETED ");
    		progressBuffer.append(String.valueOf((int) (((i+1) / (float) count) * 100)));
    		progressBuffer.append(" %");
            publishProgress(progressBuffer.toString());
            // Escape early if cancel() is called
            if (isCancelled()) {
            	gotCancelled = true;
            	break;
            }
        }
        
        progressBuffer = new StringBuffer("COMPLETED ");
		progressBuffer.append(String.valueOf(totalSize));
		progressBuffer.append(" UNITS. ");
		if (gotCancelled) {
			progressBuffer.append("JOB INCOMPLETE!!!");
		} else {
			progressBuffer.append("JOB FINISHED!!!!");
		}
		
        return progressBuffer.toString();
    }

    protected void onProgressUpdate(String... progress) {
    	StringBuffer progressBuffer = new StringBuffer("");
    	if (progress != null) {
    		for (String progress_i : progress) {
    			progressBuffer.append(progress_i);
    			progressBuffer.append('\n');
    		}
    	}
    	if (notifierActivity != null) {
    		notifierActivity.setStatusText(progressBuffer.toString());
    	}
    }

    protected void onCancelled() {
		super.onCancelled();
    	if (notifierActivity != null) {
			notifierActivity.taskGotCancelled();
    	}
    }
    
    protected void onCancelled(String result) {
    	if (notifierActivity != null) {
    		notifierActivity.setStatusText(result);
    		notifierActivity.taskGotCancelled();
    	}
    }
    
    protected void onPostExecute(String result) {
    	if (notifierActivity != null) {
    		notifierActivity.setStatusText(result);
    		notifierActivity.taskEnded();
    	}
    }
}
