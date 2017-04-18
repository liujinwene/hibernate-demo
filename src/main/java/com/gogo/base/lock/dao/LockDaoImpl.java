package com.gogo.base.lock.dao;

import java.math.BigInteger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class LockDaoImpl implements LockDao {
	
	@Override
	public void clear(Session session) {
		session.clear();
	}

	@Override
	public Integer getLock(Session session, String key, Integer timeout) {
		String format = "SELECT COALESCE(GET_LOCK('%s', %s),0)";
		String sql = String.format(format, key, timeout);
		
		Query query = session.createSQLQuery(sql);
		BigInteger result = (BigInteger) query.uniqueResult();
		System.out.println("getLock-result=" + result.intValue());
		return result.intValue();
	}

	@Override
	public Integer releaseLock(Session session, String key) {
		System.out.println("releaseLock");
		System.out.println();
		
		String format = "SELECT COALESCE(RELEASE_LOCK('%s'),0);";
		String sql = String.format(format, key);
		
		Query query = session.createSQLQuery(sql);
		BigInteger result = (BigInteger) query.uniqueResult();
		return result.intValue();
	}

}
