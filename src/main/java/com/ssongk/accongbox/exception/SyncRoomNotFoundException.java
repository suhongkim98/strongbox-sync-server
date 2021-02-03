package com.ssongk.accongbox.exception;

public class SyncRoomNotFoundException extends RuntimeException  {
	public SyncRoomNotFoundException(){
        super(ErrorCode.SYNC_ROOM_FIND_FAILED.getMessage());
    }

    public SyncRoomNotFoundException(Exception ex){
        super(ex);
    }
}
