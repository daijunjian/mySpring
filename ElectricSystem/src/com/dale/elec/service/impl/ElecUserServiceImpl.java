package com.dale.elec.service.impl;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;


import com.dale.elec.dao.IElecSystemDDLDao;
import com.dale.elec.dao.IElecUserDao;
import com.dale.elec.domain.ElecUser;
import com.dale.elec.service.IElecUserService;
import com.dale.elec.utils.MD5keyBean;
import com.dale.elec.utils.PageInfo;
import com.dale.elec.utils.StringHelp;
import com.dale.elec.web.form.ElecUserForm;

@Service(IElecUserService.SERVER_NAME)
@Transactional(readOnly=true)
public class ElecUserServiceImpl implements IElecUserService{

	@Resource(name=IElecUserDao.SERVER_NAME)
	private IElecUserDao elecUserDao;
	
	@Resource(name=IElecSystemDDLDao.SERVER_NAME)
	private IElecSystemDDLDao elecSystemDDLDao;
	
	public List<ElecUserForm> findElecUserListByCondition(ElecUserForm elecUserForm,HttpServletRequest request) {
		//查询条件
		String hqlWhere = "";
		List<String> paramsList = new ArrayList<String>();
		if(StringUtils.isNotBlank(elecUserForm.getUserName())){
			hqlWhere = " and o.userName like ? ";
			paramsList.add("%"+elecUserForm.getUserName()+"%");
		}
		Object[] params = paramsList.toArray();
		LinkedHashMap<String, String> orderBy = new LinkedHashMap<String,String>();
		orderBy.put("o.onDutyDate", "desc");
		
		//添加分页，2012-7-23，ly,begin
		  PageInfo pageInfo = new PageInfo(request);
//		  currentPageNo：记录当前页，如果为空，默认值当前第一页
//		  pageSize：记录当前页显示的记录数，如果为空，默认是显示10条记录
//		  req：存放request对象
//		  //List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(hqlWhere, params, orderby);
		  List<ElecUser> list = elecUserDao.findCollectionByConditionWithPage(hqlWhere, params, orderBy,pageInfo);
		  request.setAttribute("page", pageInfo.getPageBean());//将pagebean放置到request中，命名为page，因为页面需要取值
//		  返回PageBean，放置到request中
		  //end


		List<ElecUserForm> listForm = this.elecUserPoToVo(list);
		return listForm;
	}

	private List<ElecUserForm> elecUserPoToVo(List<ElecUser> list) {
		List<ElecUserForm> listForm = new ArrayList<ElecUserForm>();
		for (int i = 0; i < list.size(); i++) {
			ElecUser elecUser  = list.get(i);
			ElecUserForm elecUserForm = new ElecUserForm();
			try {
				BeanUtils.copyProperties(elecUserForm, elecUser);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			elecUserForm.setSexID(StringUtils.isNotBlank(elecUserForm.getSexID())?elecSystemDDLDao.findDdlName("性别",elecUserForm.getSexID()):"");
			elecUserForm.setIsDuty(StringUtils.isNotBlank(elecUserForm.getIsDuty())?elecSystemDDLDao.findDdlName("是否在职",elecUserForm.getIsDuty()):"");
			listForm.add(elecUserForm);
		}
		return listForm;
	}

	//保存用户信息
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveElecUser(ElecUserForm elecUserForm) {
		ElecUser elecUser = this.elecUserVoToPo(elecUserForm);
		//执行保存
		if(StringUtils.isNotBlank(elecUserForm.getUserID())){
			elecUserDao.update(elecUser);
		}else{
			elecUserDao.save(elecUser);
		}
		
	}

	//值转换
	private ElecUser elecUserVoToPo(ElecUserForm elecUserForm) {
		ElecUser elecUser = new ElecUser();
		if(elecUserForm!=null){
			elecUser.setJctID(elecUserForm.getJctID());
			elecUser.setUserName(elecUserForm.getUserName());
			elecUser.setLogonName(elecUserForm.getLogonName());
		//////////////////////////////////////////////////////////////
			
			//使用md5进行密码加密
			String logonPwd = elecUserForm.getLogonPwd();
			String md5LogonPwd = "";
			if(StringUtils.isBlank(logonPwd)){
				logonPwd = "000000";//设置初始密码是000000
			}
			//获取md5flag的值，用于判断是否对密码进行加密
			String md5flag = elecUserForm.getMd5flag();
			//不需要加密
			if(md5flag!=null && md5flag.equals("1")){
				md5LogonPwd = logonPwd;
			}
			//都需要加密
			else{
				MD5keyBean md5 = new MD5keyBean();
				md5LogonPwd = md5.getkeyBeanofStr(logonPwd);
			}
			elecUser.setLogonPwd(md5LogonPwd);
		/////////////////////////////////////////////////////////////
			elecUser.setSexID(elecUserForm.getSexID());
			elecUser.setAddress(elecUserForm.getAddress());
			elecUser.setContactTel(elecUserForm.getContactTel());
			elecUser.setEmail(elecUserForm.getEmail());
			elecUser.setMobile(elecUserForm.getMobile());
			elecUser.setIsDuty(elecUserForm.getIsDuty());
			elecUser.setRemark(elecUserForm.getRemark());
			if(StringUtils.isNotBlank(elecUserForm.getBirthday())){
				elecUser.setBirthday(StringHelp.convertStringToDate(elecUserForm.getBirthday()));
			}
			if(StringUtils.isNotBlank(elecUserForm.getOnDutyDate())){
				elecUser.setOnDutyDate(StringHelp.convertStringToDate(elecUserForm.getOnDutyDate()));
			}
			if(StringUtils.isNotBlank(elecUserForm.getUserID())){
				elecUser.setUserID(elecUserForm.getUserID());
			}
			if(StringUtils.isNotBlank(elecUserForm.getOffDutyDate())){
				elecUser.setOnDutyDate(StringHelp.convertStringToDate(elecUserForm.getOffDutyDate()));
			}
		}
		return elecUser;
	}

	public ElecUserForm findElecUserByID(ElecUserForm elecUserForm) {
		//获取userID
		String userID = elecUserForm.getUserID();
		ElecUser elecUser = elecUserDao.findObjectById(userID);
		elecUserForm = this.elecUserPOToVO(elecUser,elecUserForm);
		return elecUserForm;
	}

	/**值转换*/
	private ElecUserForm elecUserPOToVO(ElecUser elecUser,ElecUserForm elecUserForm) {
//		ElecUserForm elecUserForm = new ElecUserForm();
		try {
			BeanUtils.copyProperties(elecUserForm, elecUser);
		} catch (Exception e) {
			throw new RuntimeException();
		} 
		return elecUserForm;
	}

	/**  
	* @Name: deleteElecUserByUserID
	* @Description: 使用userID删除用户信息
	* @Author: 刘洋（作者）
	* @Version: V1.00 （版本号）
	* @Create Date: 2012-07-13（创建日期）
	* @Parameters: ElecUserForm 存放用户ID
	* @Return: 无
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteElecUser(ElecUserForm elecUserForm) {
		String userID = elecUserForm.getUserID();
		elecUserDao.deleteObjectByIds(userID);
	}
	
	
	/**  
	* @Description: 使用登录名进行校验，判断登录名在数据库中是否存在
	* @Return: 使用flag作为方法返回值
	*       flag == 1：说明当前登录名已经存在，在页面上需要提示，当前用户名已存在
	*       flag == 2：说明当前登录名包不存在，可以继续操作
	*/
	public String checkUser(String logonName) {
		//按照登录名组织查询条件
		String hqlWhere = " and o.logonName = ?";
		Object [] params = {logonName};
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(hqlWhere, params, null);
		String flag = "";
		if(list!=null && list.size()>0){
			flag = "1";
		}
		else{
			flag = "2";
		}
		return flag;
	}

	//验证用户名和密码
	public ElecUser findElecUserByLogonName(String name) {
		String hqlWhere = " and o.logonName = ?";
		Object [] params = {name};
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(hqlWhere, params, null);
		ElecUser elecUser = null;
		if(list!=null && list.size()>0){
			elecUser = list.get(0);
		}
		return elecUser;
	}

	public Hashtable<String, String> findElecRoleByLogonName(String name) {
		List<Object[]> list = elecUserDao.findElecRoleByLogonName(name);
		Hashtable<String, String> ht = null;
		if(list!=null && list.size()>0){
			ht = new Hashtable<String, String>();
			for(int i=0;i<list.size();i++){
				Object[] object = list.get(i);
				ht.put(object[0].toString(), object[1].toString());
			}
		}
		return ht;
	}

	public String fincElecPopedomByLogonName(String name) {
		List<Object> list = elecUserDao.findElecPopedomByLogonName(name);
		StringBuffer buffer = null;
		if(list!=null && list.size()>0){
			buffer = new StringBuffer("");
			for(int i=0;i<list.size();i++){
				Object object = list.get(i);
				buffer.append(object.toString());
			}
		}
		return buffer.toString();
	}

	/**  
	* 构造excel的标题,ArrayList<String>：封装excel的标题
	*/
	public ArrayList<String> exportExcelName() {
		/**
		 * ArrayList<String> fieldName //excel标题数据集
    	 *        将登录名	 用户姓名	 性别	  联系电话  是否在职封装到fieldName
    	 *        fieldName.add("登录名");
    	 *        fieldName.add("用户姓名");
    	 *        ...
		 */
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("登录名");
		fieldName.add("用户姓名");
		fieldName.add("性别");
		fieldName.add("联系电话");
		fieldName.add("是否在职");
		return fieldName;
	}

	/**  
	* 构造excel的显示的数据,ArrayList<ArrayList<String>>：封装excel的数据
	*/
	public ArrayList<ArrayList<String>> exportExcelData(
			ElecUserForm elecUserForm) {
		/**
		 * ArrayList<ArrayList<String>> fieldData //excel数据内容	
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
		//查询条件
		String hqlWhere = "";
		List<String> paramsList = new ArrayList<String>();
		if(StringUtils.isNotBlank(elecUserForm.getUserName())){
			hqlWhere += " and o.userName like ?";
			paramsList.add("%"+elecUserForm.getUserName()+"%");
		}
		Object [] params = paramsList.toArray();
		//排序条件
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.onDutyDate", "desc");
		List<ElecUser> list = elecUserDao.findCollectionByConditionNoPage(hqlWhere, params, orderby);
		List<ElecUserForm> formList = this.elecUserPoToVo(list);
		//转换导出excel需要的格式，构造excel的数据
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		for(int i=0;formList!=null && i<formList.size();i++){
			ElecUserForm userForm = formList.get(i);
			ArrayList<String> data = new ArrayList<String>();
			data.add(userForm.getLogonName());
			data.add(userForm.getUserName());
			data.add(userForm.getSexID());
			data.add(userForm.getContactTel());
			data.add(userForm.getIsDuty());
			fieldData.add(data);
		}
		return fieldData;
	}

	/**  
	* @Description: 将从excel读取的数据，放置到数据库中
	* @Parameters: ArrayList<String[]> list：封装从excel中读取的数据
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void importExcelDataToBase(ArrayList<String[]> list) {
		MD5keyBean bean = new MD5keyBean();
		for(int i=0;list!=null && i<list.size();i++){
			String[] str = list.get(i);
			/**
			 * gj	123	郭靖	1	2	长春	1
               hr	123	黄蓉	2	1	大连	1
			 */
			ElecUser elecUser = new ElecUser();
			elecUser.setLogonName(str[0]);
			elecUser.setLogonPwd(bean.getkeyBeanofStr(str[1]));
			elecUser.setUserName(str[2]);
			elecUser.setSexID(str[3]);
			elecUser.setJctID(str[4]);
			elecUser.setAddress(str[5]);
			elecUser.setIsDuty(str[6]);
			elecUserDao.save(elecUser);
		}
		
	}

	/** 
	* @Description: 使用所属单位进行分组，统计各个单位下的人员数量
	* @Return: List<Object[]>：统计结果（所属单位，北京，10人     所属单位，上海，20人）
	*/
	public List<Object[]> countUserByJctID() {
		List<Object[]> list = elecUserDao.countUserByJctID();
		return list;
	}
}

















