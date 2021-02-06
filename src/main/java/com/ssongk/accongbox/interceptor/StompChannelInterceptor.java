package com.ssongk.accongbox.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.ssongk.accongbox.exception.CustomJwtRuntimeException;
import com.ssongk.accongbox.provider.dto.SyncRoom;
import com.ssongk.accongbox.provider.security.JwtAuthToken;
import com.ssongk.accongbox.provider.security.JwtAuthTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StompChannelInterceptor implements ChannelInterceptor {
//stomp 채널로 들어오는 메시지 가로채서 검사하자
	//연결, 연결 종료는 이벤트리스너에서 
	
    private static final String ROOM_ID_KEY = "roomId";
	private final JwtAuthTokenProvider jwtAuthTokenProvider;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		
		if(accessor.getCommand() == StompCommand.SUBSCRIBE) {
			//구독 시 토큰검사
			JwtAuthToken token = jwtAuthTokenProvider.convertAuthToken(accessor.getFirstNativeHeader("token")); // 토큰을 헤더에서 꺼내 객체로 만든다.
			token.validate(); // 토큰 유효성 검사
			
			//토큰에 들어가 있는 방정보와 실제 구독하려는 방 정보가 일치하는지 검사
			String tokenRoomId = "/topic/" + token.getData().get(ROOM_ID_KEY); // 토큰에서 방아이디 꺼내오고
			if(tokenRoomId.equals(accessor.getDestination()) == false) {
				//일치하지 않는다면
				throw new CustomJwtRuntimeException();
			}
		} else if (accessor.getCommand() == StompCommand.SEND) {
			JwtAuthToken token = jwtAuthTokenProvider.convertAuthToken(accessor.getFirstNativeHeader("token")); // 토큰을 헤더에서 꺼내 객체로 만든다.
			token.validate(); // 토큰 유효성 검사
		}
		return message;
	}
	
}
