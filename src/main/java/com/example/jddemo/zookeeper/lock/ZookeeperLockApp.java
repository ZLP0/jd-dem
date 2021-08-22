package com.example.jddemo.zookeeper.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * zookeeper 分布式锁应用
 */
public class ZookeeperLockApp {

    public static CuratorFramework getCuratorFramework() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .builder()
                .connectString("182.92.97.65:2181")
                .sessionTimeoutMs(15000)
                .connectionTimeoutMs(20000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
        return curatorFramework;
    }


    public static void main(String[] args) throws Exception {
        lock2();
    }

    public static void lock1() {
        CuratorFramework curatorFramework = getCuratorFramework();
        //基于zk临时有序节点 实现分布式锁
        //没创建一个临时节点 都会判断当前节点是否为最小节点  如果是则获取锁
        //否则 监听上一个临时节点
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                InterProcessMutex locks = new InterProcessMutex(curatorFramework, "/Locks");
                try {
                    locks.acquire();//抢占分布式锁资源（阻塞的）
                    System.out.println("获取到锁：" + Thread.currentThread().getName());
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        locks.release();//释放锁
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public static void lock2() throws InterruptedException {
        CuratorFramework curatorFramework = getCuratorFramework();

        new Thread(() -> {
            InterProcessMutex locks = new InterProcessMutex(curatorFramework, "/Locks");
            try {
                //争抢锁 最多等待五秒 抢不到则放弃
                if (locks.acquire(5, TimeUnit.SECONDS)) {
                    System.out.println("1获取到锁：" + Thread.currentThread().getName());
                    Thread.sleep(10000);//10s
                } else {
                    System.out.println("1获取锁失败：" + Thread.currentThread().getName());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    locks.release();//释放锁
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(10);

        new Thread(() -> {
            InterProcessMutex locks = new InterProcessMutex(curatorFramework, "/Locks");
            try {
                //争抢锁 最多等待五秒 抢不到则放弃
                if (locks.acquire(5, TimeUnit.SECONDS)) {
                    System.out.println("2获取到锁：" + Thread.currentThread().getName());
                } else {
                    System.out.println("2获取锁失败：" + Thread.currentThread().getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    locks.release();//释放锁
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
