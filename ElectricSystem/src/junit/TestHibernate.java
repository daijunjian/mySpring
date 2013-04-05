package junit;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import com.dale.elec.domain.ElecText;

public class TestHibernate {

	@Test
	public void testHibernate(){
		Configuration cf = new Configuration();
		cf.configure();  //这是里加载hibernate.cfg.xml文件
		SessionFactory sf = cf.buildSessionFactory();
		Session s = sf.openSession();
		Transaction tr = s.beginTransaction();
		
		ElecText elecText = new ElecText();
		elecText.setTextName("测试hibernate连接数据库");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("测试hibernate");
		s.save(elecText);
		tr.commit();
		s.close();
		
	}
}
