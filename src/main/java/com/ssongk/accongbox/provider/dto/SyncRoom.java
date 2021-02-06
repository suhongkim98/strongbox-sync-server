package com.ssongk.accongbox.provider.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SyncRoom implements Serializable {
	private static final long serialVersionUID = 6494678977089006639L; // redis에 저장되는 객체들은 serialize가능해야하므로 serializable을 참조하도록 선언
	private String roomId;
	private String vertificationCode; // 인증코드
	private int count;
	private String requestorName;
	private String responsorName;
}
