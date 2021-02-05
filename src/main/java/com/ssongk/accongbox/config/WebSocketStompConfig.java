package com.ssongk.accongbox.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.ssongk.accongbox.interceptor.StompChannelInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker // stomp 메시지 브로커 활성화
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {
	private final StompChannelInterceptor stompChannelInterceptor;
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		//클라이언트에서 stomp이용한 웹소켓 연결을 할 EndPoint를 설정한다.
		registry.addEndpoint("/socket")
		.setAllowedOriginPatterns("*") //cors 문제 해결 부들부들
		.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
    	// subscribe, publish요청 prefix 설정
    	registry.enableSimpleBroker("/topic"); // subscribe prefix
    	registry.setApplicationDestinationPrefixes("/app"); // publish 요청 prefix
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		//토큰검사위한 채널 인터셉터 설정
		registration.interceptors(stompChannelInterceptor);
	}
	
	
}
