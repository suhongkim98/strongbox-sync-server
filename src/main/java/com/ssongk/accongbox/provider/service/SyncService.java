package com.ssongk.accongbox.provider.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssongk.accongbox.controller.dto.SyncRequestDTO;
import com.ssongk.accongbox.controller.dto.SyncResponseDTO;
import com.ssongk.accongbox.core.service.SyncServiceInterface;
import com.ssongk.accongbox.provider.dto.SyncRoom;
import com.ssongk.accongbox.provider.repository.SyncRoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SyncService implements SyncServiceInterface {
	private final SyncRoomRepository syncRoomRepository;
	
	@Override
	public Optional<SyncRoom> createSyncRoom(SyncRequestDTO syncRequestDTO) {
		SyncRoom room = syncRoomRepository.createRoom(syncRequestDTO);
		return Optional.ofNullable(room);
	}

	@Override
	public Optional<SyncRoom> findRoom(String vertificationCode) {
		SyncRoom room = syncRoomRepository.searchRoom(vertificationCode);
		return Optional.ofNullable(room);
	}

	@Override
	public Optional<SyncRoom> responseSync(SyncResponseDTO syncResponseDTO) {
		SyncRoom room = syncRoomRepository.updateResponsorName(syncResponseDTO);
		return Optional.ofNullable(room);
	}

	@Override
	public void addWebSocketSessionToRoom(String vertificationCode, String sessionId) {
		//방에 해당 세션값을 리스트에 추가한다
		syncRoomRepository.addWebSocketSessionToRoom(vertificationCode, sessionId);
	}

	@Override
	public void removeWebSocketSessionFromRoom(String sessionId) {
		//방에 해당 세션값을 리스트에서 삭제한다
		//세션 카운트 수가 0이라면 해당 방을 삭제한다
		SyncRoom room = syncRoomRepository.searchRoomBySessionId(sessionId);
		syncRoomRepository.removeWebSocketSessionFromRoom(sessionId); // 세션삭제
		if(room.getCount() <= 1) {
			// 나만 구독한 상태였다면 방 삭제
			syncRoomRepository.deleteRoom(room.getVertificationCode());
		}
	}

	@Override
	public int getSessionCount(String vertificationCode) {
		//세션 카운트를 반환한다
		return syncRoomRepository.getRoomSessionCount(vertificationCode);
	}
	
	
}
