package com.gogo.lock;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.gogo.CoreServerApp;
import com.gogo.base.lock.constants.CoordinationLocks;
import com.gogo.base.lock.service.GenerateLockService;
import com.gogo.base.utils.SpringContextUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreServerApp.class)
@WebAppConfiguration
public class LockTest {

	@Autowired
	private GenerateLockService generateLockService;

	@Test
	public void lock() throws Exception {
		try {
			Integer THREAD_SIZE = 4;
			String key = generateLockService.getKey(CoordinationLocks.STOCK.getCode(), "444");

			for(int i=0; i<THREAD_SIZE; i++) {
				Thread thread = new Thread(new TestRunable(key), "Thread-"+i);
				thread.start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Thread.sleep(20000);
	}
	
	@Test
	@Ignore
	public void lock2() throws Exception {
		String key = generateLockService.getKey(CoordinationLocks.STOCK.getCode(), "444");
		
		TransactionTemplate transactionTemplate = SpringContextUtil.getBean(TransactionTemplate.class);
		String result = transactionTemplate.execute(new TransactionCallback<String>() {

			@Override
			public String doInTransaction(TransactionStatus status) {
				try {
					return generateLockService.getNamedLock(key).enter(() -> {
						System.out.println("hi,I'm in lock");
						return "success";
					});
				} catch (Exception e) {
					e.printStackTrace();
					return "fail";
				}
			}
		});
		System.out.println("result=" + result);
	}
	
}

class TestRunable implements Runnable {
	private String key;

	public TestRunable(String key) {
		this.key = key;
	}

	@Override
	public void run() {
		GenerateLockService generateLockService = SpringContextUtil.getBean(GenerateLockService.class);
		
		try {
			generateLockService.getNamedLock(key).enter(() -> {
				System.out.println("hi,I'm in lock");
				
				SessionFactory sessionFatory = SpringContextUtil.getBean(SessionFactory.class);
				Session session = sessionFatory.openSession();
				
				System.out.println("session-hashCode=" + session.hashCode());
				
				String sql = "select * from bb_order";
				SQLQuery query = session.createSQLQuery(sql);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				query.setMaxResults(4);
				List<Map<String, Object>> list = query.list();
				if(list != null && !list.isEmpty()) {
					for(Map<String, Object> order : list) {
						System.out.println("orderNo=" + order.get("order_no"));
					}
				}
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
