<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
  <head>
   <title>
   <s:if test="viewflag==null">
  	 编辑用户
   </s:if>
   <s:else>
   	 查看明细
   </s:else>
   </title>
   <LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
   <script language="javascript" src="${pageContext.request.contextPath }/script/function.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/script/validate.js"></script>
   <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath }/script/calendar.js" charset="gb2312"></script>
   <Script language="javascript">

	function check_null(){
	
		var theForm=document.Form1;
	    
	  
	    if(Trim(theForm.userName.value)=="")
		{
			alert("用户姓名不能为空");
			theForm.userName.focus();
			return false;
		}
	    if(theForm.jctID.value=="")
		{
			alert("请选择所属单位");
			theForm.jctID.focus();
			return false;
		}
	
        if(theForm.logonPwd.value!=theForm.passwordconfirm.value){
		
		  alert("两次输入密码不一致，请重新输入");
		  return;
		}
		if(checkNull(theForm.contactTel)){
         if(!checkPhone(theForm.contactTel.value))
		  {
			alert("请输入正确的电话号码");
			theForm.contactTel.focus();
			return false;
		  }
		}
		
	    if(checkNull(theForm.mobile)){
         if(!checkMobilPhone(theForm.mobile.value))
		  {
			alert("请输入正确的手机号码");
			theForm.mobile.focus();
			return false;
		  }
		}
		
	   if(checkNull(theForm.email))	{
         if(!checkEmail(theForm.email.value))
		 {
			alert("请输入正确的EMail");
			theForm.email.focus();
			return false;
		 }
	   }
		
	   if(theForm.remark.value.length>250){
     
        	alert("备注字符长度不能超过250");
			theForm.remark.focus();
			return false; 
        }
	   //判断当前修改用户操作是否修改了密码
	   var currentValue = document.forms[0].logonPwd.value;//当前文本框的值
	   var defaultValue = document.forms[0].logonPwd.defaultValue;//跳转到页面的初始值
	   //alert(currentValue);
	   //alert(defaultValue);
	   //不需要加密设置md5flag == 1
	   if(currentValue == defaultValue){
	   	   document.forms[0].md5flag.value = "1";
	   }
	   document.Form1.action="system/elecUserAction_save.do";
	   document.Form1.submit();
	    var roleflag = document.getElementById("roleflag").value;
	   if(roleflag == ""){
	   		refreshOpener();
	   }
	  	
	}
	//将确认密码赋值，与密码的值相同
	window.onload = function(){
		var logonPwd = document.forms[0].logonPwd.value;
		document.forms[0].passwordconfirm.value = logonPwd;
	}
</script>
</head>

  
 <body>
    <s:form name="Form1" method="post">	
    <br>
    
    <table cellSpacing="1" cellPadding="5" width="90%" align="center" bgColor="#eeeeee" style="border:1px solid #8ba7e3" border="0">
	
	 <tr>
		<td class="ta_01" align="center" colSpan="4" background="${pageContext.request.contextPath }/images/b-info.gif">
		 <font face="宋体" size="2"><strong>
			 <s:if test="viewflag==null">
			  	 编辑用户
			 </s:if>
			 <s:else>
			   	 查看明细
			 </s:else>
		 </strong></font>
		</td>
    </tr>
    	<s:hidden name="userID"></s:hidden>
    	<s:hidden name="md5flag"></s:hidden>
    	<s:hidden name="roleflag" id="roleflag"></s:hidden>
     <tr>
         <td align="center" bgColor="#f5fafe" class="ta_01">登&nbsp;&nbsp;录&nbsp;&nbsp;名：</td>
         <td class="ta_01" bgColor="#ffffff">
         	<s:textfield name="logonName" maxlength="25" id="logonName" size="20" readonly="true"></s:textfield>
         <font color="#FF0000">*</font></td>
         <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">用户姓名：</td>
		 <td class="ta_01" bgColor="#ffffff">
		 	<s:textfield name="userName" maxlength="25" id="userName" size="20"></s:textfield>
		 <font color="#FF0000">*</font></td>
    </tr>
	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
		<td class="ta_01" bgColor="#ffffff">
		<s:if test="#request.sexList!=null">
			<s:select list="#request.sexList" name="sexID"
					  listKey="ddlCode" listValue="ddlName"
					  headerKey="" headerValue=""
					  cssStyle="width:155px">
			</s:select>
		</s:if>
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">所属单位：</td>
		<td class="ta_01" bgColor="#ffffff">
		<s:if test="#request.jctList!=null">
			<s:select list="#request.jctList" name="jctID"
					  listKey="ddlCode" listValue="ddlName"
					  headerKey="" headerValue=""
					  cssStyle="width:155px">
			</s:select>
			</s:if>
			<font color="#FF0000">*</font>
		</td>
	</tr>
	<s:if test="viewflag==null">
		<tr>
			<td align="center" bgColor="#f5fafe" class="ta_01">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
			<td class="ta_01" bgColor="#ffffff">
				<s:password name="logonPwd" maxlength="25" size="22" showPassword="true"></s:password>
			</td>
			<td align="center" bgColor="#f5fafe" class="ta_01">确认密码：</td>
			<td class="ta_01" bgColor="#ffffff">
				<s:password name="passwordconfirm" maxlength="25" size="22" showPassword="true"></s:password>	
			</td>
		</tr>
	</s:if>

	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">出生日期：</td>
		<td class="ta_01" bgColor="#ffffff">
			<s:textfield name="birthday" maxlength="50" size="20" onclick="JavaScript:calendar(document.Form1.birthday)"></s:textfield>
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">联系地址：</td>
		<td class="ta_01" bgColor="#ffffff">
			<s:textfield name="address" maxlength="50"  size="20"></s:textfield>
		</td>
	</tr>

	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">联系电话：</td>
		<td class="ta_01" bgColor="#ffffff">
			<s:textfield name="contactTel" maxlength="25"  size="20"></s:textfield>
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
		<td class="ta_01" bgColor="#ffffff">
			<s:textfield name="mobile" maxlength="25"  size="20"></s:textfield>
		</td>
	</tr>

	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">电子邮箱：</td>
		<td class="ta_01" bgColor="#ffffff">
			<s:textfield name="email" maxlength="50"  size="20"></s:textfield>
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">是否在职：</td>
		<td class="ta_01" bgColor="#ffffff">
		<s:if test="#request.isDutyList!=null">
			<s:select list="#request.isDutyList" name="isDuty"
					  listKey="ddlCode" listValue="ddlName"
					  cssStyle="width:155px">
			</s:select>
		</s:if>
		</td>
	</tr>
	
	<tr>
		<td align="center" bgColor="#f5fafe" class="ta_01">入职日期：</td>
		<td class="ta_01" bgColor="#ffffff">
			<s:textfield name="onDutyDate" maxlength="50" size="20" onclick="JavaScript:calendar(document.Form1.onDutyDate)"></s:textfield>
		</td>
		<td align="center" bgColor="#f5fafe" class="ta_01">离职日期：</td>
		<td class="ta_01" bgColor="#ffffff">
			<s:textfield name="offDutyDate" maxlength="50" size="20" onclick="JavaScript:calendar(document.Form1.offDutyDate)"></s:textfield>
		</td>
	</tr>

	<TR>
		<TD class="ta_01" align="center" bgColor="#f5fafe">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</TD>
		<TD class="ta_01" bgColor="#ffffff" colSpan="3">
			<s:textarea name="remark" cssStyle="WIDTH:95%" rows="4" cols="52"></s:textarea>	
		</TD>
		</TR>
		<TR>
		<td  align="center"  colSpan="4"  class="sep1"></td>
	</TR>
	<tr>
		<td class="ta_01" style="WIDTH: 100%" align="center" bgColor="#f5fafe" colSpan="4">
			<s:if test="viewflag==null">
				<input type="button" name="BT_Submit" value="保存"  style="font-size:12px; color:black; height=22;width=55"  onClick="check_null()">
		    </s:if>
		    <FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
		    <s:if test="roleflag==null">
		    <input style="font-size:12px; color:black; height=22;width=55" type="button" value="关闭"  name="Reset1"  onClick="window.close()">
		    </s:if>
	    </td>
	</tr>
</table>　
</s:form>

</body>
</html>
