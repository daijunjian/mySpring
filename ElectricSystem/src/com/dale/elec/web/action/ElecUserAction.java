package com.dale.elec.web.action;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.runner.Request;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dale.elec.service.IElecSystemDDLService;
import com.dale.elec.service.IElecUserService;
import com.dale.elec.utils.ExcelFileGenerator;
import com.dale.elec.utils.GenerateSqlFromExcel;
import com.dale.elec.utils.JFreeChartUtils;
import com.dale.elec.web.form.ElecSystemDDLForm;
import com.dale.elec.web.form.ElecUserForm;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller(value="elecUserAction")
@Scope(value="prototype")
public class ElecUserAction extends BaseAction implements ModelDriven<ElecUserForm>{

	private ElecUserForm elecUserForm = new ElecUserForm();
	
	public ElecUserForm getModel() {
		
		return elecUserForm;
	}

	@Resource(name=IElecUserService.SERVER_NAME)
	private IElecUserService elecUserService;
	
	@Resource(name=IElecSystemDDLService.SREVER_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	
	@SuppressWarnings("unused")
	public String home(){
		//添加分页2012-7-23日,start
		  List<ElecUserForm> list = elecUserService.findElecUserListByCondition(elecUserForm,request);
		  String initflag = request.getParameter("initflag");
		  request.setAttribute("userList", list);
		  if(initflag!=null && initflag.equals("1")){
		   return "list";
		  }
		  //end
		return "home";
	}
	
	public String add(){
		this.initElecSystemDDL();
		return "add";
	}

	
	public String save(){
		elecUserService.saveElecUser(elecUserForm);
		String roleflag = elecUserForm.getRoleflag();
		if(roleflag!=null && roleflag.equals("1")){
			return edit();
		}
		return "save";
	}
	
	public String edit(){
		elecUserForm = elecUserService.findElecUserByID(elecUserForm);
		/**
		 * 下面的两行代码可以不添加，因为从action到dao层操作的是同一个vo对象，它本来就在栈顶
		 */
		ActionContext.getContext().getValueStack().pop();
		ActionContext.getContext().getValueStack().push(elecUserForm);
		
		//加载数据字典，性别、所属单位、是否在职
		this.initElecSystemDDL();
//		List list1 = (List) request.getAttribute("sexList");
//		List list2 = (List) request.getAttribute("jctList");
//		List list3 = (List) request.getAttribute("isDutyList");
//		System.err.println(list3.size());
		return "edit";
	}
	

	/**初始化数据字典，如性别、所属单位、是否在职*/
	private void initElecSystemDDL() {
		List<ElecSystemDDLForm> sexList = elecSystemDDLService.findSystemDDLListByKeyword("性别");
		List<ElecSystemDDLForm> jctList = elecSystemDDLService.findSystemDDLListByKeyword("所属单位");
		List<ElecSystemDDLForm> isDutyList = elecSystemDDLService.findSystemDDLListByKeyword("是否在职");
		request.setAttribute("sexList", sexList);
		request.setAttribute("jctList", jctList);
		request.setAttribute("isDutyList", isDutyList);
	}
	
	public String delete(){
		elecUserService.deleteElecUser(elecUserForm);
		return "delete";
	}
	
	public String checkUser(){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			String logonName = request.getParameter("logonName");
			String flag = elecUserService.checkUser(logonName);
			//返回结果
			PrintWriter out = response.getWriter();
			out.println(flag);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return null;
	}

	/**  
	* @Description: 组织条件查询数据库，将数据导出excel
	*/
    public String exportExcel(){
    	/***
    	 * 构造2个数据ArrayList
    	 *   ArrayList<String> fieldName //excel标题数据集
    	 *        将登录名	 用户姓名	 性别	  联系电话  是否在职封装到fieldName
    	 *        fieldName.add("登录名");
    	 *        fieldName.add("用户姓名");
    	 *        ...
    	 *   ArrayList<ArrayList<String>> fieldData //excel数据内容	
    	 *        将liubei	     刘备  	    男	 12345678	    是
                    zhugeliang	 诸葛亮  	女	 12312312	    是放置到fieldData中
                  		ArrayList<String> data1
			                  * data1.add("liubei");
			                  * data1.add("刘备");...
			            ArrayList<String> data2
			                  * data2.add("zhugeliang");
			                  * data2.add("诸葛亮");...
                  最后：fieldData.add(data1);
                       fieldData.add(data2);
    	 */
    	ArrayList<String> fieldName = elecUserService.exportExcelName();
    	ArrayList<ArrayList<String>> fieldData = elecUserService.exportExcelData(elecUserForm);
    	//导出成excel
    	ExcelFileGenerator generator = new ExcelFileGenerator(fieldName,fieldData);
		try {
			//获取输出流，导出excel文件需要输出流作为支持
	    	OutputStream os = response.getOutputStream();
	    	//清空response的缓冲区域（StringBuffer），如不加该代码，必须保证response的缓冲区中没有其他对象，否则会有问题，建议加上该代码
	    	response.reset();
	    	//添加响应的格式为excel的格式
	    	response.setContentType("application/vnd.ms-excel");
	
			generator.expordExcel(os);//导出excel
			//刷新缓存区，将缓存区中数据写到excel的文件中
			os.flush();
			if(os!=null){
				os.close();
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
    	return null;
    }

    
    /**  
   	* @Description: 跳转到导入excel的页面
   	* @Create Date: 2012-07-23（创建日期）
   	*/
       public String importExcelPage(){
       		return "importExcelPage";
       }
     
       
    /**  
	* @Description: 从excel读取数据导入到数据库中
	* @Create Date: 2012-07-23（创建日期）
	*/
    public String importExcelData(){
    	File formFile = elecUserForm.getFile();
    	GenerateSqlFromExcel fromExcel = new GenerateSqlFromExcel();
    	try {
    		/**
    		 * ArrayList<String[]>：返回结果
    		 *     String[0]：yk	    123	   杨康	    男	上海	   吉林  	是
                   String[1]：mnc	123	   莫年慈	女	北京 	沈阳	    是
                   真正项目的时候：需要用填写男和女，我们程序将使用数据字典，将男和女转换成1和2，并保存到数据库表中
    		 */
			ArrayList<String[]> list = fromExcel.generateUserSql(formFile);
			elecUserService.importExcelDataToBase(list);
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
    	return "importExcelPage";
    }
    
    /**  
	* 人员按照所属单位进行统计,跳转到userReport.jsp
	*/
    public String countPage(){
    	//获取数据集
    	List<Object[]> list = elecUserService.countUserByJctID();
    	//使用JFreeChart封装图形化的数据集合，并使用生成对应图形
    	String filename = JFreeChartUtils.generatorBarChart(list);
    	request.setAttribute("filename", filename);//将文件名放置到request中，用于页面进行显示
    	return "countPage";
    }
	
}
