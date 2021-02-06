package com.ssongk.accongbox.core.service;

import java.util.Optional;

import com.ssongk.accongbox.controller.dto.SyncRequestDTO;
import com.ssongk.accongbox.controller.dto.SyncResponseDTO;
import com.ssongk.accongbox.provider.dto.SyncRoom;

public interface SyncServiceInterface {
	public Optional<SyncRoom> createSyncRoom(SyncRequestDTO syncRequestDTO);
	public Optional<SyncRoom> findRoom(String vertificationCode);
	public Optional<SyncRoom> responseSync(SyncResponseDTO syncResponseDTO);
	public void addWebSocketSessionToRoom(String vertificationCode, String sessionId);
	public void removeWebSocketSessionFromRoom(String sessionId);
	public int getSessionCount(String vertificationCode);
}
