package com.ssongk.accongbox.core.repository;

import com.ssongk.accongbox.controller.dto.SyncRequestDTO;
import com.ssongk.accongbox.controller.dto.SyncResponseDTO;
import com.ssongk.accongbox.provider.dto.SyncRoom;

public interface SyncRoomRepositoryInterface {
	public SyncRoom createRoom(SyncRequestDTO syncRequestDTO);
	public SyncRoom searchRoom(String vertificationCode);
	public SyncRoom searchRoomBySessionId(String sessionId);
	public void deleteRoom(String vertificationCode);
	public SyncRoom updateResponsorName(SyncResponseDTO syncResponseDTO);
	public void addWebSocketSessionToRoom(String vertificationCode, String sessionId);
	public void removeWebSocketSessionFromRoom(String sessionId);
	public int getRoomSessionCount(String vertificationCode);
	public int getRoomSessionCountBySessionId(String sessionId);
}
