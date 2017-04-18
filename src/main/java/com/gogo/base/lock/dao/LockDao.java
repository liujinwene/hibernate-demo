package com.gogo.base.lock.dao;

import org.hibernate.Session;

public interface LockDao {
	
	void clear(Session session);
	Integer getLock(Session session, String key, Integer timeout);
	Integer releaseLock(Session session, String key);
	

}
