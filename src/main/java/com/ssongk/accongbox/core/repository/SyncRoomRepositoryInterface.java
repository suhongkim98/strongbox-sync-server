package com.ssongk.accongbox.core.repository;

import com.ssongk.accongbox.controller.dto.SyncRequestDTO;
import com.ssongk.accongbox.controller.dto.SyncResponseDTO;
import com.ssongk.accongbox.provider.dto.SyncRoom;

public interface SyncRoomRepositoryInterface {
	public SyncRoom createRoom(SyncRequestDTO syncRequestDTO);
	public SyncRoom searchRoom(SyncResponseDTO syncResponseDTO);
}
