package com.ssongk.accongbox.controller.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //.build로 객체 생성하게 해줌
public class CommonResponse {
	// restcontroller 응답에 사용되는 dto객체
	private HttpStatus status;
	private String message;
	private List<Object> data;
}
