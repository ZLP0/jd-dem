package com.example.jddemo;

import com.example.jddemo.enmu.CodFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 程序员  by dell
 * time  2020-12-09
 **/

@RestController
public class App {

    Logger logger = LoggerFactory.getLogger(App.class);

    ExecutorService executor = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {

    }

    @RequestMapping(value = "/thread")
    public void testThread(String  str){

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程："+str);
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {

                }
                System.out.println("线程执行结束："+str);
            }
        });
    }
    public  void thread(String str){


    }

    public void  date(){
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
