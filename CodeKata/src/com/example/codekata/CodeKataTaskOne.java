package com.example.codekata;

public class CodeKataTaskOne extends CodeKataTaskBase {

	public CodeKataTaskOne(TaskNotificationInterface _notifierActivity) {
		super(_notifierActivity);
	}

    protected String doInBackground(String... commands) {
        int count = commands.length;
        float total = 0;
        boolean gotCancelled = false;
        StringBuffer progressBuffer = null;
        StringBuffer resultsBuffer = new StringBuffer();
        for (int i = 0; i < count; i++) {
            progressBuffer = new StringBuffer("Processing Line ");
    		progressBuffer.append(String.valueOf(i+1));
            publishProgress(progressBuffer.toString());
            
            try {
        	String[] tokens = commands[i].split(",");
        	if (tokens.length < 4) {
        		continue;
        	}
        	String name = tokens[1];
        	int quantity = Integer.valueOf(tokens[2].trim());
        	float price = Float.valueOf(tokens[3].trim());
        	float subtotal = quantity * price;
        	
        	resultsBuffer.append(String.valueOf(quantity));
        	resultsBuffer.append(" * ");
        	resultsBuffer.append(name);
        	resultsBuffer.append(" (");
        	resultsBuffer.append(String.valueOf(price));
        	resultsBuffer.append(") = ");
        	resultsBuffer.append(String.valueOf(subtotal));
        	resultsBuffer.append('\n');
        	
        	total += subtotal;
            } catch (NumberFormatException e) {
            	resultsBuffer.append("Input format on line ");
            	resultsBuffer.append(String.valueOf(i+1));
            	resultsBuffer.append(" is invalid. Ignoring...\n");
            	continue;
            }
            // Escape early if cancel() is called
            if (isCancelled()) {
            	gotCancelled = true;
            	break;
            }
        }
        
        progressBuffer = new StringBuffer("");
		if (gotCancelled) {
			progressBuffer.append("JOB INCOMPLETE!!!");
		} else {
			progressBuffer.append(resultsBuffer);
			progressBuffer.append("------------------------\n");
			progressBuffer.append("Total Charge = ");
			progressBuffer.append(String.valueOf(total));
			progressBuffer.append('\n');
		}
		
        return progressBuffer.toString();
    }

}
