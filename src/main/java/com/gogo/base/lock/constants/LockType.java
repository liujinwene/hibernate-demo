package com.gogo.base.lock.constants;

public enum LockType {
	STOCK((byte)1, "库存相关锁");
	
	private byte code;
	private String msg;
	
	private LockType(byte code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static LockType fromCode(Byte code) {
		if(code == null) {
			return null;
		}
		for(LockType r : LockType.values()) {
			if(r.code == code.byteValue()) {
				return r;
			}
		}
		return null;
	}
	
	public byte getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
	

}
