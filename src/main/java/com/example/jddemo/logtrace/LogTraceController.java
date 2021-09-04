package com.example.jddemo.logtrace;

import com.example.jddemo.common.utils.ExecutorsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

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

        ExecutorService ex = ExecutorsUtil.newCachedThreadPool("测试log子线程");
        ex.submit(new Thread(()->{
            log.info("子线程 日志++++");
        }));
        log.info("日志2");
        System.out.println("结束");


    }

    public void test2(){
        log.info("test2日志1");

        System.out.println("***************************************************************");

        log.info("test2日志2");
        System.out.println("结束2");
    }
}
