package com.simple.mybatis.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author FanXing
 * @date 2023年02月15日 23:47
 */
@SpringBootApplication(scanBasePackages = "com.simple.mybatis")
public class SampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
