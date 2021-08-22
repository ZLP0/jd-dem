package com.example.jddemo.zookeeper.leaderSelector;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Closeable;
import java.io.IOException;

/**
 * leader 选举
 */
@Component
public class SelectorLeaderExample extends LeaderSelectorListenerAdapter implements Closeable {

    private LeaderSelector leaderSelector;
    private String name;

    public SelectorLeaderExample() {
        this.leaderSelector = new LeaderSelector(getCuratorFramework(), "/leader", this);
        this.name = Thread.currentThread().getName();
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }


    /**
     * 竞争 leader
     *
     * @param client
     * @throws Exception
     */
    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        System.out.println(name + " 成为Leader");
        Thread.sleep(1000);
    }


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

    @PostConstruct
    public void init() {
        leaderSelector.start();
    }

    public static void main(String[] args) throws IOException {
        String path = "/leader";
        for (int i = 0; i < 10; i++) {
            SelectorLeaderExample selectorLeaderExample =
                    new SelectorLeaderExample();
            selectorLeaderExample.start();
        }
        System.in.read();
    }

    private void start() {
        leaderSelector.start();
    }


}
