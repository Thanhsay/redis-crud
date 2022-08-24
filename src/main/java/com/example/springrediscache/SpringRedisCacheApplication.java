package com.example.springrediscache;

import com.example.springrediscache.Service.UserConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class SpringRedisCacheApplication {
    private final ApplicationContext applicationContext;

    public SpringRedisCacheApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisCacheApplication.class, args);
    }

    @PostConstruct
    public void executive() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(applicationContext.getBean(UserConsumer.class));
    }

}
