package com.gogo.base.lock.service;

import java.util.concurrent.locks.Lock;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gogo.base.lock.MysqlNamedLock;
import com.gogo.base.lock.NamedLock;

@Service
public class GenerateLockServiceImpl implements GenerateLockService {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Lock getLock(Byte lockType, String key) throws Exception {
		return null;
	}

	@Override
	public NamedLock getNamedLock(String key) {
		return new MysqlNamedLock(key, sessionFactory.openSession());
	}

	@Override
	public String getKey(String type, String numberNo) {
		return type + "-" + numberNo;
	}
}
