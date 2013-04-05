%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<HTML>
	<HEAD>
		<title>系统设置</title>		
		<LINK href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet">
		<script language="javascript" src="${pageContext.request.contextPath }/script/function.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/script/pub.js"></script>
		<script language="javascript">
		
			
			
		function changetype(){
		  /**
		    * document.Form1.keyword.value=="jerrynew"
		      此时表示类型列表的值为空，那么允许用户填写类型名称：<input type="text" name="keywordname" maxlength="50" size="24">
		        表示的业务逻辑，是要新增数据类型，同时填写对应该数据类型数据项的值
		    * document.Form1.keyword.value!="jerrynew"
		     此时表示类型列表的值不为null，那么此时类型名称要不显示
		       表示的业务逻辑，要在选中数据类型中，进行修改和编辑该数据类型的数据项
		  
		  */
		  if(document.Form1.keyword.value=="jerrynew"){
		    
		     //alert("1")
		  	 var textStr="<input type=\"text\" name=\"keywordname\" maxlength=\"50\" size=\"24\"> ";
		     document.getElementById("newtypename").innerHTML="类型名称：";
		     document.getElementById("newddlText").innerHTML=textStr;
		     
		     //document.Form1.action = "system/elecSystemDDLAction_home.do";
		     //document.Form1.submit(); 
		     Pub.submitActionWithForm('Form2','system/elecSystemDDLAction_edit.do','Form1');
		    
		  }else{
		    //alert("2")
		    var textStr="";
		    document.getElementById("newtypename").innerHTML="";
		    document.getElementById("newddlText").innerHTML=textStr;
		    /**
		    使用ajax框架进行操作
		    1、在使用的页面上添加：<script type="text/javascript" src="${pageContext.request.contextPath }/script/pub.js">:
		    2、调用Pub.submitActionWithForm，并传递3个参数
		        参数1：Form2：表单中Form2的名称
		       参数2：URL连接，访问服务器
		       参数3：Form1：表单中Form1的名称
		    3、实现原理
		      （1）通过URL访问服务器，同时传递表单Form1中元素作为传递的参数，并通过服务器处理返回对应数据，
		      （2）将返回的数据放置到dictionaryEdit.jsp中，并且将整个页面放置到表单Form2中
		    4、比较dictionaryIndex.jsp和dictionaryEdit.jsp
		       dictionaryEdit.jsp的内容就是dictionaryIndex.jsp的表单Form2中的内容
		    */
		    Pub.submitActionWithForm('Form2','system/elecSystemDDLAction_edit.do','Form1');
		  }  
	   }
	   
     function saveDict(){
	      /**
	        * 如果document.Form1.keyword.value=="jerrynew"
	           * 此时表示新增数据类型，在新增的数据类型中添加数据项的值和编号
	               
	               <input type="hidden" name="keywordname" >：keywordname用来存储数据类型
                   <input type="hidden" name="typeflag" >：typeflag用来判断当前操作是新增数据类型还是在原有的数据类型进行修改
                       * new：新增数据类型
                       * add：在原有的数据类型上，进行修改和编辑     
                   
	           
	      */
	      if(document.Form1.keyword.value=="jerrynew"){
	          if(Trim(document.Form1.keywordname.value)==""){
	             alert("请输入类型名称");
	             return false;
	          }
	          
	         var allkeywords= document.Form1.keyword;
	         for(var i=0;i<allkeywords.length;i++){
	    
	            if(allkeywords[i].value==Trim(document.Form1.keywordname.value)){           

	               alert("已存在此类型名称,请重新输入");
	               return false;
	             }
	             
	         }
	         
	          document.Form2.keywordname.value=document.Form1.keywordname.value;
	          alert(document.Form2.keywordname.value);
	          document.Form2.typeflag.value="new";
	          
	      }else{
	      
	          document.Form2.keywordname.value=document.Form1.keyword.value;
	          document.Form2.typeflag.value="add";	
	      }
	      var tbl=document.getElementById("dictTbl");
	      for (i=1;i<tbl.rows.length;i++){        
		   	  	var name = tbl.rows[i].cells[1].children.item(0).value;
		   	  	//alert(name);
		   	  	if(Trim(name)==""){
		   	  	    alert("名称不能为空！");
		   	  	    
		   	  	    return false;
	   	  	    }
	   	  }
	   	  for(k=1;k<=tbl.rows.length-2;k++)
		  {
	 	  	for(m=k+1;m<=tbl.rows.length-1;m++)
	 	  	{     
		  	  	var name1 = tbl.rows[k].cells[1].children.item(0).value;
		  	  	var name2 = tbl.rows[m].cells[1].children.item(0).value;
		  	  	if(name1 == name2){
		  	  		alert("名称不能相同！"); 
		  	  		 return false;
		        }	
		    }
		  }
	      document.Form2.action="system/elecSystemDDLAction_save.do";
	      document.Form2.submit();     
	}
	  
  
       
  function removecheck(seqid){
     if( !confirm("你确定删除吗?")) return false;
      
      document.Form2.keywordname.value=document.Form1.keyword.value;
      document.Form2.action="removeDict.do?seqid="+seqid;
      document.Form2.submit();
  }       
  
     
     
     
       

 function insertRows(){ 

  var tempRow=0; 
  var tbl=document.getElementById("dictTbl");
  tempRow=tbl.rows.length; 
  //alert(tbl.rows.length);//2
  var Rows=tbl.rows;//类似数组的Rows 
  var newRow=tbl.insertRow(tbl.rows.length);//插入新的一行 
  //alert("ok");
  var Cells=newRow.cells;//类似数组的Cells 
  for (i=0;i<3;i++)//每行的3列数据 
  { 
     //alert(newRow.rowIndex+"   "+Cells.length);
     var newCell=Rows(newRow.rowIndex).insertCell(Cells.length); //在当前行插入一个指定的列
     newCell.align="center"; 
     switch (i) 
    { 
      case 0 : newCell.innerHTML="<td class=\"ta_01\" align=\"center\"  width=\"15%\">"+tempRow+"</td>";break; 
      case 1 : newCell.innerHTML="<td class=\"ta_01\" align=\"center\"  width=\"60%\"><input name=\"itemname\" type=\"text\" id=\""+tempRow+"\" size=\"45\" maxlength=25></td>";break; 
      case 2 : newCell.innerHTML="<td class=\"ta_01\" align=\"center\"  width=\"25%\"><a href='javascript:delTableRow(\""+tempRow+"\")'><img src=${pageContext.request.contextPath }/images/delete.gif width=15 height=14 border=0 style=CURSOR:hand></a></td>";break;

    } 
  } 
 } 


function delTableRow(rowNum){ 

   var tbl=document.getElementById("dictTbl");
    
    if (tbl.rows.length >rowNum){ 
       //alert(tbl.rows.length+ "       " +rowNum);
       tbl.deleteRow(rowNum); //指定rowNum删除表格中对应的行
       //alert(tbl.rows.length+ "       " +rowNum);
      for (i=rowNum;i<tbl.rows.length;i++)
      {
         tbl.rows[i].cells[0].innerText=i;
         tbl.rows[i].cells[2].innerHTML="<a href='javascript:delTableRow(\""+i+"\")'><img src=${pageContext.request.contextPath }/images/delete.gif width=15 height=14 border=0 style=CURSOR:hand></a>";      
      }
   }
} 
    
	 </script>
 </HEAD>
		
	<body>
	 <Form name="Form1" id="Form1"  method="post" style="margin:0px;">
		<table cellSpacing="1" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0">
			<TBODY>
				<tr>
					<td class="ta_01" colspan=3 align="center" background="${pageContext.request.contextPath }/images/b-info.gif">
						<font face="宋体" size="2"><strong>数据字典维护</strong></font>
					</td>
				</tr>
				<TR height=10><td colspan=3></td></TR>		
				<tr>
					<td class="ta_01" align="right" width="35%" >类型列表：</td>
					<td class="ta_01" align="left"  width="30%" >
					<s:if test="#request.systemList!=null && #request.systemList.size()>0">
						<s:select list="#request.systemList" name="keyword"
								  listKey="keyword" listValue="keyword"
								  headerKey="jerrynew" headerValue=""
								  cssClass="bg" cssStyle="width:180px" onchange="changetype()">
						</s:select>
					</s:if>
					</td>
						
					 <td class="ta_01"  align="right" width="35%" >					 	    
				    </td>	  		
				</tr>
				
				
				
			    <tr>
			    <!-- 下面赋值目的是为了初始化时,显示,当选择空时,上面的会覆盖下面的 -->
			       <td class="ta_01" align="right" width="35%" id="newtypename">类型名称:</td>
				   <td class="ta_01"  align="left" width="30%"  height=20 id="newddlText">
				    <input type="text" name="keywordname" maxlength="25" size=24>	
				   </td>
				   <td class="ta_01"  align="right" width="35%" ></td>
				</tr>
				
				
				<TR height=10><td colspan=3 align="right">
				   <input type="button" name="saveitem" value="添加选项" style="font-size:12px; color:black; height=20;width=80" onClick="insertRows()">
				 </td></TR>   
			</TBODY>
		</table>
		</Form>
		
 <Form name="Form2" id="Form2"  method="post" style="margin:0px;">
    <table cellSpacing="1" cellPadding="0" width="90%" align="center" bgColor="#f5fafe" border="0" >
    <tr>
     <td >
	   <table cellspacing="0"   cellpadding="1" rules="all" bordercolor="gray" border="1" id="dictTbl"
		    style="BORDER-RIGHT:gray 1px solid; BORDER-TOP:gray 1px solid; BORDER-LEFT:gray 1px solid; WIDTH:100%; WORD-BREAK:break-all; BORDER-BOTTOM:gray 1px solid; BORDER-COLLAPSE:collapse; BACKGROUND-COLOR:#f5fafe; WORD-WRAP:break-word">
			
		
						
				<tr style="FONT-WEIGHT:bold;FONT-SIZE:12pt;HEIGHT:25px;BACKGROUND-COLOR:#afd1f3">
					<td class="ta_01" align="center"  width="20%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">编号</td>
					<td class="ta_01" align="center"  width="60%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">名称</td>
					<td class="ta_01" align="center"  width="20%" height=22 background="${pageContext.request.contextPath }/images/tablehead.jpg">删除</td>					
				</tr>
			    
			   
			     <tr>
				   <td class="ta_01" align="center"  width="20%">1</td>
				   <td class="ta_01" align="center"  width="60%">
				   <input name="itemname" type="text"  size="45" maxlength="25"></td>
				   <td class="ta_01" align="center"  width="20%"></td>
				</tr>
	          
	            
			
	     </table>
	   </td>
	 </tr>
  <tr>
     <td >   
	 </td>
 </tr>
 <TR height=10><td colspan=3></td></TR>
  <tr>
     <td align="center" colspan=3>
       <input type="button" name="saveitem" value="保存" style="font-size:12px; color:black; height=20;width=50" onClick="saveDict();">
	 </td>
 </tr>
 
       <input type="hidden" name="keywordname" >
       <input type="hidden" name="typeflag" >
	 
  </table>
   
    
   
  </Form>
  </body>
</HTML>


