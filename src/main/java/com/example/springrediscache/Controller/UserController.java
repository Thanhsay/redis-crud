package com.example.springrediscache.Controller;

import com.example.springrediscache.Model.User;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private RedissonClient redissonClient;

    @PutMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            pushUserToCache(user);
            return ResponseEntity.ok().body("Save successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Save failed!");
        }
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> saveUser() {
        RList<User> users = redissonClient.getList("getUser");
        return ResponseEntity.ok().body(users.readAll());
    }

    public void pushUserToCache(User user) {
        RBlockingQueue<User> rBlockingQueue = redissonClient.getBlockingQueue("user");
        try {
            rBlockingQueue.offer(user, 5, TimeUnit.MINUTES);
        } catch (InterruptedException i) {
            i.printStackTrace();
        }
    }
}
