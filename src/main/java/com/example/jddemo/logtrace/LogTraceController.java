package com.example.jddemo.logtrace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 程序员  by dell
 * time  2021-07-07
 **/

@Slf4j
@RestController
public class LogTraceController {


    @RequestMapping(value = "log")
    public void test1(){
        log.info("日志1");

        System.out.println("***************************************************************");
        //test2();
        log.info("日志2");
       /*   new Thread(()->{
              log.info("线程");
        }).start();*/
        System.out.println("结束");


    }

    public void test2(){
        log.info("test2日志1");

        System.out.println("***************************************************************");

        log.info("test2日志2");
        System.out.println("结束2");
    }
}
