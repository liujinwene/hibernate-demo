package com.gogo.other;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gogo.base.core.CoreServerApp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreServerApp.class)
@WebAppConfiguration
public class OtherTest {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void test1() {
		System.out.println("test begin");
		
		String sql = "select * from bb_order";
		SQLQuery query = sessionFactory.openSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		List<Map<String, Object>> list = query.list();
		
		for(Map<String, Object> obj : list) {
			System.out.println(obj.get("order_no"));
		}
	}

}
