package com.ssongk.accongbox.redis;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssongk.accongbox.websocket.dto.SyncMessage;

@Service
public class RedisSyncMessageSubscriber implements MessageListener {
	//redis에서 받은 메시지를 stomp구독자들에게 뿌린다
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	private ObjectMapper mapper = new ObjectMapper();
	@Override
	public void onMessage(Message message, byte[] pattern) {
		// TODO Auto-generated method stub
		try {
			SyncMessage syncMessage = mapper.readValue(message.getBody(), SyncMessage.class);
			messagingTemplate.convertAndSend("/topic/" + syncMessage.getRoomId(), syncMessage);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
