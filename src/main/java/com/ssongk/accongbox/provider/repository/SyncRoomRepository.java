package com.ssongk.accongbox.provider.repository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.ssongk.accongbox.controller.dto.SyncRequestDTO;
import com.ssongk.accongbox.controller.dto.SyncResponseDTO;
import com.ssongk.accongbox.core.repository.SyncRoomRepositoryInterface;
import com.ssongk.accongbox.exception.SyncRoomNotFoundException;
import com.ssongk.accongbox.provider.dto.SyncRoom;

@Repository
public class SyncRoomRepository implements SyncRoomRepositoryInterface {
	private HashOperations<String, String, SyncRoom> opsSyncRooms;// 방을 redis에 저장
	private HashOperations<String, String, SyncRoom> opsSocketSessions; // 웹소켓 세션정보 redis에 저장
	private static final String CHAT_ROOMS = "SYNC_ROOM";
	private static final String SESSION_INFO = "SYNC_SESSION";
	
	@Autowired
	public SyncRoomRepository(RedisTemplate<String, SyncRoom> syncRoomRedisTemplate) {
		opsSyncRooms = syncRoomRedisTemplate.opsForHash(); // redis template 주입받아서 생성
		opsSocketSessions = syncRoomRedisTemplate.opsForHash();
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
	
	@Override
	public SyncRoom searchRoom(String vertificationCode) {
		SyncRoom room = opsSyncRooms.get(CHAT_ROOMS, vertificationCode); // 인증코드로 방을 찾는다.
		return room;
	}
	
	@Override
	public SyncRoom searchRoomBySessionId(String sessionId) {
		//해당 세션이 속한 방을 반환한다
		//socket세션이 가르키는 방 안에 있는 값은 바뀔 수 있으니 syncRoom의 room을 반환하자
		SyncRoom room = opsSocketSessions.get(SESSION_INFO, sessionId);
		return opsSyncRooms.get(CHAT_ROOMS, room.getVertificationCode());
	}
	
	@Override
	public void deleteRoom(String vertificationCode) {
		opsSyncRooms.delete(CHAT_ROOMS, vertificationCode);
	}
	@Override
	public SyncRoom updateResponsorName(SyncResponseDTO syncResponseDTO) {
		SyncRoom room = searchRoom(syncResponseDTO.getVertificationCode());
		if(room != null) {
			room.setResponsorName(syncResponseDTO.getName());
			opsSyncRooms.put(CHAT_ROOMS, syncResponseDTO.getVertificationCode(), room);
		}
		return room;
	}
	
	@Override
	public void addWebSocketSessionToRoom(String vertificationCode, String sessionId) {
		// 해당 방에 세션정보를 추가한다
		SyncRoom room = searchRoom(vertificationCode);
		if(room == null) {
			throw new SyncRoomNotFoundException();
		}
		room.setCount(room.getCount() + 1); // 카운트 업
		opsSocketSessions.put(SESSION_INFO, sessionId, room); // 세션등록
		opsSyncRooms.put(CHAT_ROOMS, vertificationCode, room); // 카운트 정보 방 업데이트
	}
	@Override
	public void removeWebSocketSessionFromRoom(String sessionId) {
		// 방에서 해당 세션을 삭제한다
		SyncRoom room = searchRoomBySessionId(sessionId); // 세션id를 통해 방을 가져온다
		if(room == null) {
			throw new SyncRoomNotFoundException();
		}
		room.setCount(room.getCount() - 1); // 카운트 다운
		opsSocketSessions.delete(SESSION_INFO, sessionId); // 세션삭제
		opsSyncRooms.put(CHAT_ROOMS, room.getVertificationCode(), room); // 카운트 정보 방 업데이트
	}
	@Override
	public int getRoomSessionCount(String vertificationCode) {
		SyncRoom room = searchRoom(vertificationCode);
		if(room == null) {
			throw new SyncRoomNotFoundException();
		}
		return room.getCount();
	}
	@Override
	public int getRoomSessionCountBySessionId(String sessionId) {
		SyncRoom room = searchRoomBySessionId(sessionId); // 세션id를 통해 방을 가져온다
		return room.getCount();
	}
}
