package com.ssongk.accongbox.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private String code;
	private String message;
	private int status;
	
	static public ErrorResponse create() {
        return new ErrorResponse();
    }

    public ErrorResponse code(String code) {
        this.code = code;
        return this;
    }

    public ErrorResponse status(int status) {
        this.status = status;
        return this;
    }

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }
    
}