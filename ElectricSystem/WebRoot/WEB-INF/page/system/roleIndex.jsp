
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<HTML>
	<HEAD>
		<title>角色权限管理</title>		
		<LINK href="${pageContext.request.contextPath }/css/Style.css"  type="text/css" rel="stylesheet">
		<script language="javascript"  src="${pageContext.request.contextPath }/script/function.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/script/pub.js"></script>
		<script language="javascript">
		  
		 function saveRole(){
		 	alert("1");
           document.Form2.roleID.value=document.Form1.roleID.value;
		   document.Form2.action="system/elecRoleAction_save.do";
		   document.Form2.submit();
		   alert("2");
		}
		
       
       function selectRole(){
         // alert("1");
          if(document.Form1.roleID.value=="0"){
          
             document.Form1.action="system/elecRoleAction_home.do";
             document.Form1.submit();            
          }else{
            alert("2");
            Pub.submitActionWithForm('Form2','system/elecRoleAction_edit.do','Form1');
          }
       }
	   function checkAllOper(oper){
          var selectoper = document.getElementsByName("selectoper");
          for(var i=0;i<selectoper.length;i++){
          	selectoper[i].checked = oper.checked;
          }
       }
	   function checkAllUser(user){
          var selectuser = document.getElementsByName("selectuser");
          for(var i=0;i<selectuser.length;i++){
          	selectuser[i].checked = user.checked;
          }
       }
		</script>
	</HEAD>
		
	<body>
	 <s:form name="Form1" id="Form1"  method="post" style="margin:0px;">
		<table cellSpacing="1" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" colspan=2 align="center" background="${pageContext.request.contextPath }/images/b-info.gif">
						<font face="宋体" size="2"><strong>角色管理</strong></font>
					</td>
				</tr>	
				<tr>
				   <td class="ta_01" colspan=2 align="right" width="100%"  height=10>
				   </td>
				</tr>		
				<tr>
					<td class="ta_01" align="right" width="35%" >角色类型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td class="ta_01" align="left"  width="65%" >
					
					
					 <s:if test="#request.roleList!=null&&#request.roleList.size()>0">
						 <s:select list="#request.roleList" name="roleID" 
						 	listKey="ddlCode" listValue="ddlName"
						 	headerKey="0" headerValue="请选择"
						 	cssClass="bg" cssStyle="width:180px" 
						 	onchange="selectRole()">
						 </s:select>
					 </s:if>
					  
					</td>				
				</tr>
			    
			    <tr>
				   <td class="ta_01" align="right" colspan=2 align="right" width="100%"  height=10></td>
				</tr>
				
			</TBODY>
		  </table>
 </s:form>

<s:form  name="Form2" id="Form2"  method="post" style="margin:0px;">
 
  <table cellSpacing="1" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0">
 <tr>
  <td>
   <fieldset style="width:100%; border : 1px solid #73C8F9;text-align:left;COLOR:#023726;FONT-SIZE: 12px;"><legend align="left">权限分配</legend>
 
     <table cellSpacing="0" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0">			 
			  <tr>
				 <td class="ta_01" colspan=2 align="left" width="100%" > 
				 <!-- 
				  	流程分析：
				  		1 从页面获取权限对象集合，使用struts标签进行遍历
				  		2 遍历前，对父级节点设置一个空值，主要用它的code值进行区分父子关系
				  		3 当第一次循环遍历时，比较父级节点的值和子节点的值不相等，然后，走else语句，并为父节点赋值，该值存放在request作用域中，并显示父节点和子节点的名称
				  		4 当第二次循环遍历时，父节点值和子节点值相等，然后走循环语句，循环n此后，父节点值和子节点值不想等，又走else语句
				  		5 然后比较，相等，又走if，遍历、n次，依次类推
				  -->
				 <s:if test="%{#request.popedomList!=null && #request.popedomList.size()>0}">
				 		<s:set name="parentCode" scope="request" value="%{''}"></s:set>
				 		<s:iterator value="%{#request.popedomList}" var="popedom">
				 			<s:if test="%{#request.parentCode==#popedom.parentCode}">
				 				<input type="checkbox"  name="selectoper" value="<s:property value='%{#popedom.code}'/>" >
				 				<s:property value="%{#popedom.name}"/>
				 			</s:if>
				 			<s:else>
				 				<s:set name="parentCode" scope="request" value="%{#popedom.parentCode}"></s:set>
				 				<br>
				 				<!-- 添加空格，让页面美观 -->
				 				<s:iterator begin="0" end="%{8-#popedom.parentName.length()}" step="1">
				 				&nbsp;&nbsp;&nbsp;
				 				</s:iterator>
				 				<s:property value="%{#popedom.parentName}"/>:
				 				<input type="checkbox"  name="selectoper" value="<s:property value='%{#popedom.code}'/>" >
				 				<s:property value="%{#popedom.name}"/>
				 			</s:else>
				 		</s:iterator>
				 	</s:if>
				  
				   </td>
				</tr>						
				 <input type="hidden" name="roleID" >						
		 </table>	
        </fieldset>
	  </td>
	 </tr>					
  </table>
		    				    
	</s:form>
	</body>
</HTML>
