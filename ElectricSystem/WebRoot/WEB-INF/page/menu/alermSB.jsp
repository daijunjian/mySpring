


<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>











<html>
  <head>
    <title>load</title>
    <link href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet" />
   </head>
  
  <body>
    <table width="100%" border="0" id="table8">
    
	
				<tr align="left">
					<td valign="middle"  style="word-break: break-all">
					<span class="style1">
						${devRun}
					</span></td>
				</tr>
						
				<tr>
					<td align="left" valign="middle"  style="word-break: break-all">
					<span class="style1">
						日期：<s:property value="%{createDate}"/>
					</span></td>
				</tr>		
	
	
				
	</table>
  </body>
</html>