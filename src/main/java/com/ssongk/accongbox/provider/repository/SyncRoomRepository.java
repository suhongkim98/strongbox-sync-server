package com.ssongk.accongbox.provider.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ssongk.accongbox.controller.dto.SyncRequestDTO;
import com.ssongk.accongbox.core.repository.SyncRoomRepositoryInterface;
import com.ssongk.accongbox.provider.dto.SyncRoom;

@Repository
public class SyncRoomRepository implements SyncRoomRepositoryInterface {
	private HashOperations<String, String, SyncRoom> opsSyncRooms;// 방을 redis에 저장
	private static final String CHAT_ROOMS = "SYNC_ROOM";
	
	@Autowired
	public SyncRoomRepository(RedisTemplate<String, SyncRoom> syncRoomRedisTemplate) {
		opsSyncRooms = syncRoomRedisTemplate.opsForHash(); // redis template 주입받아서 생성
	}
	@Override
	public SyncRoom createRoom(SyncRequestDTO syncRequestDTO) {
		// 랜덤 방번호 생성
		String roomId = UUID.randomUUID().toString();
		//랜덤 인증번호 생성
		String vId = String.format("%06d", (int)(Math.random()*1000000));
		// 생성한사람 이름 삽입해서 방 생성
		SyncRoom room = SyncRoom.builder()
				.roomId(roomId)
				.vertificationCode(vId)
				.requestorName(syncRequestDTO.getName())
				.build();
		// redis에 삽입
		opsSyncRooms.put(CHAT_ROOMS, vId, room); // 인증코드를 key로 사용하자!
		return room;
	}
}
