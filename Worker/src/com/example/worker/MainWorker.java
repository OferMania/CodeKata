package com.example.worker;

import android.os.AsyncTask;

public class MainWorker extends AsyncTask<String, Integer, Long> {
	WorkerNotificationInterface notifierActivity;
	
	public MainWorker(WorkerNotificationInterface _notifierActivity) {
		super();
		notifierActivity = _notifierActivity;
	}
	
    protected Long doInBackground(String... urls) {
        int count = urls.length;
        long totalSize = 0;
        for (int i = 0; i < count; i++) {
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            ++totalSize;
            publishProgress((int) (((i+1) / (float) count) * 100));
            // Escape early if cancel() is called
            if (isCancelled()) break;
        }
        return totalSize;
    }

    protected void onProgressUpdate(Integer... progress) {
    	if ((notifierActivity != null) && (progress != null) && (progress.length >= 1)) {
    		StringBuffer progressBuffer = new StringBuffer("COMPLETED ");
    		progressBuffer.append(progress[0].toString());
    		progressBuffer.append(" %");
    		notifierActivity.setStatusText(progressBuffer.toString());
    	}
    }

    protected void onCancelled() {
    	if (notifierActivity != null) {
    		super.onCancelled();
			notifierActivity.workerGotCancelled();
    	}
    }
    
    protected void onCancelled(Long result) {
    	if (notifierActivity != null) {
    		StringBuffer progressBuffer = new StringBuffer("COMPLETED ");
    		progressBuffer.append(result.toString());
    		progressBuffer.append(" UNITS. ");
    		progressBuffer.append("JOB INCOMPLETE!!!");
    		notifierActivity.setStatusText(progressBuffer.toString());
    		notifierActivity.workerGotCancelled();
    	}
    }
    
    protected void onPostExecute(Long result) {
    	if (notifierActivity != null) {
    		StringBuffer progressBuffer = new StringBuffer("COMPLETED ");
    		progressBuffer.append(result.toString());
    		progressBuffer.append(" UNITS. ");
    		progressBuffer.append("JOB FINISHED!!!!");
    		notifierActivity.setStatusText(progressBuffer.toString());
    		notifierActivity.workerEnded();
    	}
    }
}