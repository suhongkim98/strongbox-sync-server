package com.ssongk.accongbox.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisSyncMessageSubscriber implements MessageListener {
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	private ObjectMapper mapper = new ObjectMapper();
	@Override
	public void onMessage(Message message, byte[] pattern) {
		// TODO Auto-generated method stub
		
	}
	
	
}
