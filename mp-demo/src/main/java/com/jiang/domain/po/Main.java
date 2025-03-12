package com.jiang.domain.po;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss SSS");
        SchedileService schedileService = new SchedileService();
        schedileService.schedile(()->
        {

            System.out.println(LocalDateTime.now().format(dateTimeFormatter) + "这是100ms一次的");
        },100);
        Thread.sleep(1000);
        System.out.println("添加一个每200毫秒打印一个2");
        schedileService.schedile(()->{
                System.out.println(2);
        },200);
    }
}
