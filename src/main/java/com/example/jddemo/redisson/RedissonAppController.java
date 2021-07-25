package com.example.jddemo.redisson;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/redisson")
public class RedissonAppController {

    @Resource
    private RedissonClient redissonClient;

    @RequestMapping(value = "/lock")
    public void lock() {
        RLock rLock = redissonClient.getLock("lock_id");
        try {
            if (rLock.tryLock()) {
                System.out.println("获取锁成功");

                Thread.sleep(60000);
            } else {
                System.out.println("获取锁失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }


    }


    @RequestMapping(value = "/lock2")
    public void lock2() {
        RLock rLock = redissonClient.getLock("lock_id");
        try {
            //争抢锁最多等待100秒、上锁10s以后自动解锁
            if (rLock.tryLock(100, 10, TimeUnit.SECONDS)) {
                System.out.println("获取锁成功");

                //睡眠 60s  锁的时间为10s
                Thread.sleep(60000);
            } else {
                System.out.println("获取锁失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }


    }


    @RequestMapping(value = "/lock3")
    public String lock3() {
        RLock rLock = redissonClient.getLock("lock_id");
        try {
            //未设置 过期时间  默认锁30S   2/3 时自动续期 30s 除非宕机 否则不释放
            if (rLock.tryLock()) {
                System.out.println("获取锁成功");
            } else {
                System.out.println("获取锁失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return "执行完毕";
    }

}
