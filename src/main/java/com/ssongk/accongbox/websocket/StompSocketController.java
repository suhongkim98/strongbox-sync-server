package com.ssongk.accongbox.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.ssongk.accongbox.websocket.dto.SyncMessage;

@Controller
public class StompSocketController {

	@MessageMapping("/syncPub")
	public void chatting(SyncMessage message) { //필드변수명과 이름이 일치하면 알아서 객체에 담아준다.
		
	}
}
