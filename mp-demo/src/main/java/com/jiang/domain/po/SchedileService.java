package com.jiang.domain.po;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.LockSupport;

public class SchedileService {
    Trigger trigger = new Trigger();
    ExecutorService executorService = Executors.newFixedThreadPool(6);

    void  schedile(Runnable  task, long delay){
         Job job = new Job();
         job.setTask(task);
         job.setDelay(delay);
         job.setStartTime(System.currentTimeMillis() + delay);
         trigger.queue.offer(job);
         trigger.wakeUp();
        }

        //等待合适的时间，把对应task任务丢给线程池
        class Trigger{
        PriorityBlockingQueue<Job> queue= new PriorityBlockingQueue<>();

        Thread thread = new Thread(()-> {
            while (true) {
                while (queue.isEmpty()) {
                    LockSupport.park();
                }
                Job latelyJob = queue.peek();
                if (latelyJob.getStartTime() < System.currentTimeMillis()) {
                    latelyJob = queue.poll();
                    executorService.execute(latelyJob.getTask());
                    Job nextJob = new Job();
                    nextJob.setTask(latelyJob.getTask());
                    nextJob.setDelay(latelyJob.getDelay());
                    nextJob.setStartTime(System.currentTimeMillis() + latelyJob.getDelay());
                    queue.offer(nextJob);
//                    System.out.println("下一个任务的运行时间为 %d ms后",);
                } else {
                    LockSupport.parkUntil(latelyJob.getStartTime());
                }
//                //1 job wait 1s  2job wait 500ms
//                if(!queue.isEmpty()){
//                    Job job =queue.poll();
//                    long waitTime = job.getStartTime() - System.currentTimeMillis();
//                    if (waitTime > 0) {
//                        LockSupport.park();
//                    }
//                    executorService.execute(job.getTask());
//            }
        }
        });
            {
                thread.start();
                System.out.println("触发器启动了");

            }

        void wakeUp(){
            LockSupport.unpark(thread);
        }

        }
}


