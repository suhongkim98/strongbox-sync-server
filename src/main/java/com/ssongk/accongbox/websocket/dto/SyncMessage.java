package com.ssongk.accongbox.websocket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyncMessage {
	public enum MessageType{
		CONNECT_SUCCESS, DATA
	}
	private MessageType type;
	private String roomId;
	private String sender;
	private String message;
}
