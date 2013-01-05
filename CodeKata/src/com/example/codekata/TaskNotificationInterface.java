package com.example.codekata;

public interface TaskNotificationInterface {
    public void taskEnded();
    
    public void taskGotCancelled();
    
    public void setStatusText(String status);
    
    public void appendStatusText(String status);
    
    public Object getSystemService();
}
