package com.ssongk.accongbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccongBoxApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.default", "dev"); // 프로파일을 설정한다. 개발 중에는 dev, 빌드 시에는 prod로
		SpringApplication.run(AccongBoxApplication.class, args);
	}

}
