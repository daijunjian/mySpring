


<%@ page language="java" pageEncoding="UTF-8"%>









<html>
  <head>
    <title>load</title>
    <link href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet" />
   </head>
  
  <body>
    <table width="100%" border="0" id="table8">
    
	
				<tr>
					<td align="left" valign="middle"  style="word-break: break-all">
					<span class="style1">
						${stationRun}
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