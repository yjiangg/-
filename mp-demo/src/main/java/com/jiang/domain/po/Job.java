package com.jiang.domain.po;

public class Job implements  Comparable<Job>{
    private  Runnable task;
    private  long delay;
    private long StartTime;

    public long getDelay() {
        return delay;
    }



    public void setDelay(long delay) {
        this.delay = delay;
    }




    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public long getStartTime() {
        return StartTime;
    }

    public void setStartTime(long startTime) {
        StartTime = startTime;
    }


    @Override
    public int compareTo(Job o) {
        return Long.compare(this.StartTime,o.StartTime);
    }
}
