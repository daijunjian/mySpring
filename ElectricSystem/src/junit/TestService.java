package junit;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dale.elec.domain.ElecText;
import com.dale.elec.service.IElecTextService;
import com.dale.elec.web.form.ElecTextForm;

public class TestService {

	@Test
	public void testService(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		IElecTextService service = (IElecTextService) ac.getBean(IElecTextService.SERVER_NAME);
		ElecText elecText = new ElecText();
		elecText.setTextName("测试service连接");
		elecText.setTextDate(new Date());
		elecText.setTextRemark("测试service");
		service.save(elecText);
	}
	
	/**模拟通过条件组织查询条件，查询对应的结果集
	 * 对应Action层的操作
	 * */
	@Test
	public void find(){
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");//加载类路径下
		IElecTextService elecTextService = (IElecTextService)context.getBean(IElecTextService.SERVER_NAME);
		ElecTextForm elecTextForm = new ElecTextForm();
		elecTextForm.setTextName("测试");
		elecTextForm.setTextRemark("测试");
		elecTextService.findCollectionByConditionNoPage(elecTextForm);
	}
}
