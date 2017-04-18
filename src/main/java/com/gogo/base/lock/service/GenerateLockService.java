package com.gogo.base.lock.service;

import java.util.concurrent.locks.Lock;

import com.gogo.base.lock.NamedLock;

public interface GenerateLockService {
	Lock getLock(Byte lockType, String key) throws Exception;
	
	NamedLock getNamedLock(String key);
	
	String getKey(String type, String numberNo);

}
