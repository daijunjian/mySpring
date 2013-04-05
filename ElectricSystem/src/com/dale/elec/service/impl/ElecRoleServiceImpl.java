package com.dale.elec.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dale.elec.dao.IElecRolePopedomDao;
import com.dale.elec.dao.IElecUserRoleDao;
import com.dale.elec.domain.ElecRolePopedom;
import com.dale.elec.domain.ElecUserRole;
import com.dale.elec.service.IElecRoleService;
import com.dale.elec.web.form.ElecRoleForm;
import com.dale.elec.web.form.ElecUserForm;
import com.dale.elec.web.form.ElecXmlObject;

@Service(value=IElecRoleService.SERVER_NAME)
public class ElecRoleServiceImpl implements IElecRoleService{

	@Resource(name=IElecUserRoleDao.SERVER_NAME)
	private IElecUserRoleDao elecUserRoleDao;

	@Resource(name=IElecRolePopedomDao.SERVER_NAME)
	private IElecRolePopedomDao elecRolePopedomDao;
	
	public List<ElecXmlObject> readXml() {
		
		//定义路径，找到Function.xml
		//File file = new File("D:\\apache-tomcat-6.0.18\\webapps\\itcast0709elec\\WEB-INF\\classes\\Function.xml");
		ServletContext servletContext = ServletActionContext.getServletContext();
		String path = servletContext.getRealPath("/WEB-INF/classes/Function.xml");
		File file = new File(path);
		List<ElecXmlObject> list = new ArrayList<ElecXmlObject>();
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(file);
			Element element = document.getRootElement();
			for(Iterator<Element> iter = element.elementIterator("Function");iter.hasNext();){
				Element xmlElement = iter.next();
				ElecXmlObject xmlObject = new ElecXmlObject();
				xmlObject.setCode(xmlElement.elementText("FunctionCode"));
				xmlObject.setName(xmlElement.elementText("FunctionName"));
				xmlObject.setParentCode(xmlElement.elementText("ParentCode"));
				xmlObject.setParentName(xmlElement.elementText("ParentName"));
				list.add(xmlObject);
			}
			
		} catch (DocumentException e) {
			throw new RuntimeException();
		}
		return list;
	}
	
	public List<ElecXmlObject> editXml(ElecRoleForm elecRoleForm) {
		//获取所有的功能权限
		List<ElecXmlObject> allPopedomList = this.readXml();
		//获取roleid
		String roleid = elecRoleForm.getRoleID();
		//使用roleid获取当前角色具有的功能权限
		ElecRolePopedom elecRolePopedom = elecRolePopedomDao.findObjectById(roleid);
		String popedom = "";
		if(elecRolePopedom!=null){
			popedom = elecRolePopedom.getPopedomcode();
		}
		//用当前角色的功能权限与所有的功能权限进行匹配：
		for(int i=0;allPopedomList!=null && i<allPopedomList.size();i++){
			ElecXmlObject elecXmlObject = allPopedomList.get(i);
			if(popedom.contains(elecXmlObject.getCode())){
				elecXmlObject.setFlag("1");
			}
			else{
				elecXmlObject.setFlag("2");
			}
		}
		return allPopedomList;
	}

	public List<ElecUserForm> findUser(ElecRoleForm elecRoleForm) {
		String roleid = elecRoleForm.getRoleID();
		List<Object[]> list = elecUserRoleDao.findUserByRoleID(roleid);
		List<ElecUserForm> formList = this.elecUserPOListToVOList(list);
		return formList;
	}

	/**值转换*/
	private List<ElecUserForm> elecUserPOListToVOList(List<Object[]> list) {
		List<ElecUserForm> formList = new ArrayList<ElecUserForm>();
		for(int i=0;list!=null && i<list.size();i++){
			Object [] object = list.get(i);
			ElecUserForm elecUserForm = new ElecUserForm();
			elecUserForm.setFlag(object[0].toString());
			elecUserForm.setUserID(object[1].toString());
			elecUserForm.setLogonName(object[2].toString());
			elecUserForm.setUserName(object[3].toString());
			formList.add(elecUserForm);
		}
		return formList;
	}

	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveRole(ElecRoleForm elecRoleForm) {
		//保存角色权限关联表
		this.saveRolePopedom(elecRoleForm);
		//保存用户角色关联表
		this.saveUserRole(elecRoleForm);
	}
	/**保存用户角色关联表*/
	private void saveUserRole(ElecRoleForm elecRoleForm) {
		String roleid = elecRoleForm.getRoleID();
		String [] selectUsers = elecRoleForm.getSelectuser();
		//使用角色ID作为查询条件，查询Elec_User_Role，获取当前角色对应所有用户集合（list）
		String hqlWhere = " and o.roleID = ?";
		Object [] params = {roleid};
		List<ElecUserRole> list = elecUserRoleDao.findCollectionByConditionNoPage(hqlWhere, params, null);
		//获取的集合后执行delete，删除当前角色对应的所有的用户
		elecUserRoleDao.deleteObjectsByCollection(list);
		//遍历用户，组织PO对象，执行save
		for(int i=0;selectUsers!=null && i<selectUsers.length;i++){
			String userID = selectUsers[i];
			ElecUserRole elecUserRole = new ElecUserRole();
			elecUserRole.setRoleID(roleid);
			elecUserRole.setUserID(userID);
			elecUserRoleDao.save(elecUserRole);
		}
	}
	/**保存角色权限关联表*/
	private void saveRolePopedom(ElecRoleForm elecRoleForm) {
		String roleid = elecRoleForm.getRoleID();
		String [] selectOpers = elecRoleForm.getSelectoper();
		//组织权限的集合，字符串的形式，用于保存
		String popedom = "";
		for(int i=0;selectOpers!=null && i<selectOpers.length;i++){
			popedom += selectOpers[i];
		}
		//使用roleID进行查询（Elec_Role_Popedom）获取当前角色ID对应角色权限信息
		ElecRolePopedom elecRolePopedom = elecRolePopedomDao.findObjectById(roleid);
		//如果角色ID获取的对象有值：组织PO对象，执行update语句
		if(elecRolePopedom!=null){
			elecRolePopedom.setPopedomcode(popedom);
			elecRolePopedomDao.saveOrUpdateObject(elecRolePopedom);
		}
		//如果角色ID获取的对象没有值：组织PO对象，执行save语句
		else{
			elecRolePopedom = new ElecRolePopedom();
			elecRolePopedom.setRoleID(roleid);
			elecRolePopedom.setPopedomcode(popedom);
			elecRolePopedomDao.saveOrUpdateObject(elecRolePopedom);
		}
	}
	
}
