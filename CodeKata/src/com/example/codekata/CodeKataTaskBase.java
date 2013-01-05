package com.example.codekata;

import java.util.Date;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

public class CodeKataTaskBase extends AsyncTask<String, String, String> {
	TaskNotificationInterface notifierActivity;
	MemoryStats initialMemoryStats;
	
	public CodeKataTaskBase(TaskNotificationInterface _notifierActivity) {
		super();
		notifierActivity = _notifierActivity;
		initialMemoryStats = getMemoryStats();
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
    		StringBuffer postBuffer = new StringBuffer(result);
    		postBuffer.append(getMemoryAndTimeReport());
    		notifierActivity.setStatusText(postBuffer.toString());
    		notifierActivity.taskGotCancelled();
    	}
    }
    
    protected void onPostExecute(String result) {
    	if (notifierActivity != null) {
    		StringBuffer postBuffer = new StringBuffer(result);
    		postBuffer.append(getMemoryAndTimeReport());
    		notifierActivity.setStatusText(postBuffer.toString());
    		notifierActivity.taskEnded();
    	}
    }
    
    protected String getMemoryAndTimeReport() {
    	MemoryStats finalMemoryStats = getMemoryStats();
    	StringBuffer report = new StringBuffer("\nMEMORY USAGE: ");
    	report.append(String.valueOf(finalMemoryStats.availableMemory));
    	report.append('/');
    	if (finalMemoryStats.totalMemory > 0) {
    		report.append(String.valueOf(finalMemoryStats.totalMemory));
    	} else {
    		report.append("UNKNOWN");
    	}
    	report.append(" Bytes\nTOTAL MEMORY CONSUMED: ");
    	report.append(String.valueOf(initialMemoryStats.availableMemory - finalMemoryStats.availableMemory));
    	report.append(" Bytes\nTIME ELAPSED: ");
    	report.append(String.valueOf(finalMemoryStats.time - initialMemoryStats.time));
    	report.append(" ms\n");
    	
    	initialMemoryStats = finalMemoryStats;
    	return report.toString();
    }
    
    private MemoryStats getMemoryStats() {
    	MemoryStats memoryStats = new MemoryStats();
    	
    	ActivityManager activityManager = (ActivityManager) notifierActivity.getSystemService();
    	ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
    	activityManager.getMemoryInfo(memoryInfo);
    	memoryStats.availableMemory = memoryInfo.availMem;
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    		memoryStats.totalMemory = memoryInfo.totalMem;
    	} else {
    		memoryStats.totalMemory = memoryInfo.threshold;
    	}
    	Date date = new Date();
    	memoryStats.time = date.getTime();
    	
    	return memoryStats;
    }
}
