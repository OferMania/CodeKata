package com.example.worker;

public interface WorkerNotificationInterface {
    public void workerEnded();
    
    public void workerGotCancelled();
    
    public void setStatusText(String status);
    
    public void appendStatusText(String status);
}
