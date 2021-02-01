package com.ssongk.accongbox.provider.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssongk.accongbox.controller.dto.SyncRequestDTO;
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
	
}
