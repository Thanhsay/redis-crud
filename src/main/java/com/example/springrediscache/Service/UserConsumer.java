package com.example.springrediscache.Service;

import com.example.springrediscache.Model.User;
import org.redisson.api.*;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Component
public class UserConsumer implements Runnable{

    @Autowired
    private RedissonClient redissonClient;

    private List<User> userList = new ArrayList<>();

    @Override
    public void run() {
        while (userList.size() < 3) {
            User user = getUserFromCache();
            userList.add(user);
            if (userList.size() == 2) {
                pushUserToCache(userList);
                userList.clear();
            }
        }
    }

    public User getUserFromCache() {
        RBlockingQueue<User> rBlockingQueue = redissonClient.getBlockingQueue("user");
        try {
            return rBlockingQueue.poll(5, TimeUnit.MINUTES);
        } catch (InterruptedException i) {
            i.printStackTrace();
            return null;
        }
    }

    public void pushUserToCache(List<User> userList) {
        RList<User> users = redissonClient.getList("getUser");
        for(User user : userList) {
            users.add(user);
        }
    }
}
