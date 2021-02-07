package com.ssongk.accongbox.websocket;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.ssongk.accongbox.websocket.dto.SyncMessage;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class StompSocketController {
	private final RedisTemplate<String, SyncMessage> syncRedisTemplate;
	
	@MessageMapping("/syncPub")
	public void chatting(SyncMessage message) { //필드변수명과 이름이 일치하면 알아서 객체에 담아준다.
		syncRedisTemplate.convertAndSend("syncServers", message); // redis에게 syncServers토픽에 대해 publish 요청
	}
}
