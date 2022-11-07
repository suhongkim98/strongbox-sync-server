package com.ssongk.accongbox.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssongk.accongbox.controller.dto.CommonResponse;
import com.ssongk.accongbox.controller.dto.SyncRequestDTO;
import com.ssongk.accongbox.controller.dto.SyncResponseDTO;
import com.ssongk.accongbox.core.security.Role;
import com.ssongk.accongbox.exception.SyncRoomNotFoundException;
import com.ssongk.accongbox.provider.dto.SyncRoom;
import com.ssongk.accongbox.provider.security.JwtAuthToken;
import com.ssongk.accongbox.provider.security.JwtAuthTokenProvider;
import com.ssongk.accongbox.provider.service.SyncService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sync")
public class SyncController {
	private final SyncService syncService;
	private final JwtAuthTokenProvider jwtAuthTokenProvider;
	
	
	@PostMapping("/requestSync")
	public ResponseEntity<CommonResponse> requestSync(@RequestBody SyncRequestDTO syncRequestDTO) {
		SyncRoom room = syncService.createSyncRoom(syncRequestDTO).orElseGet(() -> null); // 방을 생성한다 나중에 스레드 써서 30초 뒤에 삭제되도록 해야함
		Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault()).toInstant()); // 토큰은 2분만 유지되도록 설정, 2분 내에 동기화가 이뤄져야함
		JwtAuthToken token = jwtAuthTokenProvider.createAuthToken(syncRequestDTO.getName(), Role.USER.getCode(), room.getRoomId(), room.getVertificationCode(), expiredDate);  //토큰 발급 
		
		List<Object> dataList = List.of(room, token);
		CommonResponse response = CommonResponse.builder()
				.status(HttpStatus.OK)
				.message("요청")
				.data(dataList)
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/responseSync")
	public ResponseEntity<CommonResponse> responseSync(@RequestBody SyncResponseDTO syncResponseDTO) {
		SyncRoom room = syncService.responseSync(syncResponseDTO).orElseThrow(() -> new SyncRoomNotFoundException()); // null이면 exception 발행
		Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault()).toInstant()); // 토큰은 2분만 유지되도록 설정
		JwtAuthToken token = jwtAuthTokenProvider.createAuthToken(syncResponseDTO.getName(), Role.USER.getCode(), room.getRoomId(), room.getVertificationCode(), expiredDate);  //토큰 발급 
		
		List<Object> dataList = List.of(room, token);
		CommonResponse response = CommonResponse.builder()
				.status(HttpStatus.OK)
				.message("요청")
				.data(dataList)
				.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
