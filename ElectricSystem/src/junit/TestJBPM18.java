package junit;

import org.hibernate.SessionFactory;
import org.jbpm.api.ProcessEngine;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJBPM18 {
	//创建JBPM4.4的18张表
	@Test
	public void createTable18(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		SessionFactory sf = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println("SessionFactory:"+sf);
	}
	
	//测试流程引擎对象
	@Test
	public void testProcessEnginee(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		ProcessEngine processEngine = (ProcessEngine) ac.getBean("processEngine");
		System.out.println("ProcessEngine:"+processEngine);
	}
}
