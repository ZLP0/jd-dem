package com.example.jddemo;

import org.apache.poi.ss.usermodel.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 程序员  by dell
 * time  2020-12-09
 **/

@RestController
public class App {

    Logger logger = LoggerFactory.getLogger(App.class);

    ExecutorService executor = Executors.newFixedThreadPool(1);

    static ReentrantLock lock = new ReentrantLock(true);

    static  int count=1;

    public static void main(String[] args) {


        new  Thread(()->{
            while (true){

                System.out.println("count:"+count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                if(count==5){
                    Thread.currentThread().interrupt();
                    System.out.println("中断表后："+  Thread.interrupted());
                    System.out.println("中断表示："+  Thread.interrupted());
                }
            }
        }).start();


    }

    @RequestMapping(value = "/thread")
    public void testThread(String str) {


    }

    public void thread(String str) {


    }

    public void date() {
        LocalDate localDate = LocalDate.of(2021, 2, 2);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date start = Date.from(instant);
        long startTime = start.getTime();

        System.out.println("开始：" + startTime);

        localDate = LocalDate.of(2021, 2, 15);
        zone = ZoneId.systemDefault();
        instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date end = Date.from(instant);
        long endTime = end.getTime();

        System.out.println("结束：" + endTime);
    }


}
