package com.ssongk.accongbox.config;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import redis.embedded.RedisServer;

@Configuration
@Profile("dev") // 개발환경에서만 실행되게
public class EmbeddedRedisConfig {
//embedded redis를 사용하겠다. embedded redis 쓰는게 아니라면 삭제해야함
    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        if(isArmMac()) {
            //arm cpu mac에서 embedded redis 사용을 위해 분기작업 실시

            //아직 embedded redis가 m1을 지원하지 않아 분기하는 작업을 해주어야한다 --
            //맥이라면 컴파일한 내부 redis로 실행하자,,
            redisServer = new RedisServer(getRedisFileForArmMac(), redisPort);
        } else {
            redisServer = new RedisServer(redisPort);
        }
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
    private boolean isArmMac() {
        // m1 맥인지 검사
        System.out.println(System.getProperty("os.arch")); // ㅇㅏ니 왜 로제타로 실행되지 ㅡㅡ
        System.out.println(System.getProperty("os.name"));
        return System.getProperty("os.name").equals("Mac OS X");
    }
    private File getRedisFileForArmMac() throws IOException{
        return new ClassPathResource("binary/redis/redis-server-6.0.10-mac-arm64").getFile();
    }
}