package com.ssongk.accongbox.websocket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyncMessage {
	public enum MessageType{
		CONNECT_SUCCESS, 
		SYNC_AGREE, 
		SYNC_DENY, 
		DATA,
		SYNC_RECEIVE,
	}
	private MessageType type;
	private String roomId;
	private String senderToken;
	private String message;
}
