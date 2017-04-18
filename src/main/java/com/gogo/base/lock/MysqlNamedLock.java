package com.gogo.base.lock;

import java.util.concurrent.Callable;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gogo.base.lock.dao.LockDao;
import com.gogo.base.lock.dao.LockDaoImpl;
import com.gogo.base.utils.SpringContextUtil;

public class MysqlNamedLock implements NamedLock {
	private static final Logger LOGGER = LoggerFactory.getLogger(MysqlNamedLock.class);
	
	private static final Integer DEFAULT_TIMEOUT = 10000;//unit is second
	private String key;
	private Integer timeout;
	private Session session;
	
	public MysqlNamedLock(String key, Session session) {
		this.key = key;
		this.timeout = DEFAULT_TIMEOUT;
		this.session = session;
	}
	
	public MysqlNamedLock(String key, Integer timeout) {
		this.key = key;
		this.timeout = timeout;
	}

	@Override
	public <T> T enter(Callable<T> callback) throws Exception {
		LockDao lockDao = SpringContextUtil.getBean(LockDaoImpl.class);
		try {
			lockDao.getLock(session, key, timeout);
			T result = callback.call();
			return result;
		} catch (Exception e) {
			LOGGER.error("lock execute exception.key=" + key);
			throw e;
		} finally {
			lockDao.releaseLock(session, key);
		}
	}

}
