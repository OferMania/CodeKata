package com.example.codekata;

public class CodeKataTaskTwo extends CodeKataTaskBase {

	public CodeKataTaskTwo(TaskNotificationInterface _notifierActivity) {
		super(_notifierActivity);
	}

	protected int chop(float item, float[] item_list, int lwb, int upb, int line, int level) {
		if (isCancelled()) {
			return CodeKataConfig.ERROR_USER_CANCELLED;
		}
		StringBuffer progressBuffer = new StringBuffer("Processing Line ");
		progressBuffer.append(String.valueOf(line));
		progressBuffer.append(", Level ");
		progressBuffer.append(String.valueOf(level));
        publishProgress(progressBuffer.toString());
		if (lwb > upb) {
			return CodeKataConfig.ERROR_NOT_FOUND;
		}
		int mid = (lwb + upb) / 2;
		
		if (Math.abs(item_list[mid] - item) < CodeKataConfig.ERROR_FLOAT_TOLERANCE) {
			return mid;
		} else if (item < item_list[mid]) {
			return chop(item, item_list, lwb, mid-1, line, level+1);
		} else {
			return chop(item, item_list, mid+1, upb, line, level+1);
		}
	}
	
	protected String doInBackground(String... commands) {
        int count = commands.length;
        boolean gotCancelled = false;
        StringBuffer progressBuffer = null;
        StringBuffer resultsBuffer = new StringBuffer();
        for (int i = 0; i < count; i++) {
        	String line = commands[i].trim();
        	if (line.length() <= 0) {
        		continue;
        	}
            progressBuffer = new StringBuffer("Beginning Processing Line ");
    		progressBuffer.append(String.valueOf(i+1));
            publishProgress(progressBuffer.toString());
            
            float item = 0;
            float[] item_list = null;
            try {
	        	String[] tokens = line.split(":");
	        	if (tokens.length < 2) {
	        		throw new NumberFormatException();
	        	}
	        	item = Float.valueOf(tokens[0].trim());
	        	String item_list_string = tokens[1].trim();
	        	String[] item_list_tokens = item_list_string.split(",");
	        	if (item_list_tokens.length <= 0) {
	        		throw new NumberFormatException();	        		
	        	}
	        	item_list = new float[item_list_tokens.length];
	        	for (int ii = 0; ii < item_list.length; ++ii) {
	        		item_list[ii] = Float.valueOf(item_list_tokens[ii].trim());
	        	}
            } catch (NumberFormatException e) {
            	resultsBuffer.append("Input format on line ");
            	resultsBuffer.append(String.valueOf(i+1));
            	resultsBuffer.append(" is invalid. Ignoring...\n");
            	continue;
            }            
            int result = chop(item, item_list, 0, item_list.length-1, i+1, 1);
            if (result == CodeKataConfig.ERROR_USER_CANCELLED) {
            	gotCancelled = true;
            	break;            	
            } else {
            	resultsBuffer.append("Line ");
            	resultsBuffer.append(String.valueOf(i+1));
            	resultsBuffer.append(" : Item ");
            	resultsBuffer.append(String.valueOf(item));
            	resultsBuffer.append(" was found at index ");
            	resultsBuffer.append(String.valueOf(result));
            	resultsBuffer.append('\n');
            }
        }
        
        progressBuffer = new StringBuffer("");
		if (gotCancelled) {
			progressBuffer.append("JOB INCOMPLETE!!!");
		} else {
			progressBuffer.append(resultsBuffer);
			progressBuffer.append('\n');
		}
		
        return progressBuffer.toString();
	}
}
