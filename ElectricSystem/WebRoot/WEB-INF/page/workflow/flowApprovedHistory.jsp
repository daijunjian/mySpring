<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
    <title>流转记录</title>
    <LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
	<script language="javascript" src="${pageContext.request.contextPath }/script/function.js"></script>
    <script type="text/javascript">
	  
    </script> 
</head>
<body>
	<form id="Form1" name="Form1" action="" method="post" style="margin:0px;"> 
		<table cellspacing="1" cellpadding="0" width="90%" align="center" bgcolor="#f5fafe" border="0">
			<TR height=10><td></td></TR>
			<tr>
				<td class="ta_01" colspan=2 align="center" background="${pageContext.request.contextPath }/images/b-info.gif">
					<font face="宋体" size="2"><strong>流转记录管理</strong></font>
				</td>
				
			</tr>
	    </table>	
	</form>

	<form id="Form2" name="Form2" action="" method="post">
	
		<table cellSpacing="1" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0">
			<TBODY>
				<TR height=10><td></td></TR>			
				<tr>
				  	<td>
		                <TABLE style="WIDTH: 105px; HEIGHT: 20px" border="0">
							<TR>
								<TD align="center" background="${pageContext.request.contextPath }/images/cotNavGround.gif"><img src="${pageContext.request.contextPath }/images/yin.gif" width="15"></TD>
								<TD class="DropShadow" background="${pageContext.request.contextPath }/images/cotNavGround.gif">流转记录列表</TD>
							</TR>
			             </TABLE>
	                   </td>
					<td class="ta_01" align="right">
						<input style="font-size:12px; color:black; height=20;width=80" id="BT_Back" type="button" value="返回" name="BT_Back" 
						 onclick="javascript:history.go(-1);">&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
				  	<td class="ta_01" align="left" bgColor="#f5fafe" colspan="2">
		                说明：<br />
							1，审批信息列表按审批时间升序排列。<br />
	                   </td>
				</tr>
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe" colspan="2">			
						<table cellspacing="0" cellpadding="1" rules="all" bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT:gray 1px solid; BORDER-TOP:gray 1px solid; BORDER-LEFT:gray 1px solid; WIDTH:100%; WORD-BREAK:break-all; BORDER-BOTTOM:gray 1px solid; BORDER-COLLAPSE:collapse; BACKGROUND-COLOR:#f5fafe; WORD-WRAP:break-word">
						
							<tr style="FONT-WEIGHT:bold;FONT-SIZE:12pt;HEIGHT:25px;BACKGROUND-COLOR:#afd1f3">
							    <td align="center" width="30%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">审批人</td>
								<td align="center" width="25%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">审批时间</td>
								<td align="center" width="10%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">是否通过</td>
								<td width="35%" align="center" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">审批意见</td>
							</tr>
							<s:if test="#request.appList!=null && #request.appList.size()>0">
								<s:iterator value="%{#request.appList}" var="app">
									<tr onmouseover="this.style.backgroundColor = 'white'" onmouseout="this.style.backgroundColor = '#F5FAFE';">
										<td style="HEIGHT:22px" align="center" width="30%">
											${app.approveUserName }
										</td>
										<td style="HEIGHT:22px" align="center" width="25%">
											${app.approveTime }
										</td>
										<td style="HEIGHT:22px" align="center" width="10%">
											${app.approval?'同意':'不同意' }
										</td>
										<td style="HEIGHT:22px" align="center" width="35%">
											${app.comment }
										</td>
									</tr>
								</s:iterator>
							</s:if>
						</table>		
					</td>
				</tr>     
			</TBODY>
		</table>
	</form>
</body>
</html>
