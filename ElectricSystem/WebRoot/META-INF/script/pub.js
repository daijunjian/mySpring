function Pub(){  } 

Pub.submit=function(formindex) {
			
  				document.forms[formindex].submit();
  				
}

Pub.formsubmitAction=function(formindex,action) {
            
			document.forms[formindex].action=action;
  				document.forms[formindex].submit();
  				
}


Pub.submitform=function(formname){
   

   var theform =document.forms[formname];
   theform.submit();
}

Pub.reset=function(formindex){
				document.forms[formindex].reset();
}


////////////////////////////////////////////////////////////////////////////////
Pub.checkAll=function(parent, itemName)
{
  
  var parentbox = document.getElementsByName(itemName);
  
  for (var i=0; i<parentbox.length; i++){
  
  
   parentbox[i].checked = parent.checked;
   
   }
}
Pub.checkItem=function (child, allName)
{
  var all = document.getElementsByName(allName)[0];
  if(!child.checked) all.checked = false;
  else
  {
    var childbox = document.getElementsByName(child.name);
    for (var i=0; i<childbox.length; i++)
     if(!childbox[i].checked) return;
    all.checked = true;
  }
}

Pub.getAllCheckItemValue=function(storename,removevalue){


var obj_all = document.getElementsByTagName("INPUT");
var all_value="";
for(i=0;i<obj_all.length;i++){
if(obj_all[i].type=="checkbox")
   if(obj_all[i].checked ){
      if(obj_all[i].value!=removevalue)
      all_value=all_value+obj_all[i].value+",";
   }
      
   
}
var obj = document.getElementById(storename);
obj.value=all_value;

}

Pub.getAllCheckItemValueWithRemove=function(storename,removevalue){


var obj_all = document.getElementsByTagName("INPUT");
var all_value="";
for(i=0;i<obj_all.length;i++){
if(obj_all[i].type=="checkbox")
   if(obj_all[i].checked ){
      if(obj_all[i].value.indexOf("NULL")==-1){ 
      if(obj_all[i].value!=removevalue)
      all_value=all_value+obj_all[i].value+",";
   }
      }
   
}
var obj = document.getElementById(storename);
obj.value=all_value;

}

///////////////////////////////////////////////////////////////////////////////////////////////////////
Pub.copyRole=function(paramname,action){

var obj_all = document.getElementsByTagName("INPUT");
var value="";
for(i=0;i<obj_all.length;i++){
if(obj_all[i].type=="radio")
   if(obj_all[i].checked ){
       
        value = obj_all[i].value;
      }
   
}

 if(value==""){
 return false;}
  document.forms[0].action=action+"?"+paramname+"="+value;
  document.forms[0].submit();

}
///////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**创建ajax引擎*/
Pub.newXMLHttpRequest=function newXMLHttpRequest() {
  var xmlreq = false;
  if (window.XMLHttpRequest) {
  
    xmlreq = new XMLHttpRequest();
  } else if (window.ActiveXObject) {
     
    try {
      
      xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (e1) {
       
      try {
      
        xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
      } catch (e2) {
          
        alert(e2);
      }
    }
  }
  return xmlreq;
}



/**
req:ajax引擎对象
eleid：表单Form2的名称
responseXmlHandler：对应函数体Pub.handleResponse

*/
Pub.getReadyStateHandler =function getReadyStateHandler(req, eleid,responseXmlHandler) {
  
  return function () {
    
    if (req.readyState == 4) {
       
      if (req.status == 200) {
        //获取到返回值
        /**
        参数1：封装服务器的返回值
       参数2：表单中Form2的对象
        */
        responseXmlHandler(req.responseText,eleid);
 
      } else {
        
        alert("HTTP error: "+req.status);
        return false;
      }
    }
  }
}





Pub.getStatisticReadyStateHandler =function (req, eleid,responseXmlHandler,noteId) {
  
  return function () {
    
    if (req.readyState == 4) {
       
      if (req.status == 200) {
          
        responseXmlHandler(req.responseText,eleid);
 
      } else {
        
        alert("HTTP error: "+req.status);
        return false;
      }
    }else{
   
     var ele11 =document.getElementById(noteId);
    ele11.innerHTML="Loading Data";
    }
  }
}

/**
data:封装服务器的返回值
eleid：表单中Form2的名称
*/
Pub.handleResponse= function handleResponse(data,eleid){

      var ele =document.getElementById(eleid);
      //将将服务器的返回值放置到表单的Form2中
      //alert(data);//把相应的数据放到form2表单中
      ele.innerHTML = data;
    
}


Pub.submitAction=function(domId,action){

  var req = Pub.newXMLHttpRequest();
  
  var handlerFunction = Pub.getReadyStateHandler(req, domId,Pub.handleResponse);
  req.onreadystatechange = handlerFunction;
  req.open("POST", action, false);
  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

   
  req.send(null);

}




Pub.submitActionWithNote=function(domId,action,noteId){


  var req = Pub.newXMLHttpRequest();
  
  var handlerFunction = Pub.getStatisticReadyStateHandler(req, domId,Pub.handleResponse,noteId);
  req.onreadystatechange = handlerFunction;
  req.open("POST", action, true);
  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

  req.send(null);

}



/**处理发送请求的参数
sForm:表单中From1的参数
*/
Pub.getParams2Str=function getParams2Str(sForm){

 var strDiv="";
      
 try {
    var objForm=document.forms[sForm];
  if (!objForm)
    return strDiv;
  var elt,sName,sValue;
  for (var fld = 0; fld < objForm.elements.length; fld++) {
      elt = objForm.elements[fld];    
      sName=elt.name;
      sValue=""+elt.value;
      if(fld==objForm.elements.length-1)
          strDiv=strDiv + sName+"="+sValue+"";
       else   
          strDiv=strDiv + sName+"="+sValue+"&";
   }


  }
  catch (ex) {
    return strDiv;
	}
   //传递表单Form1的中的元素作为参数
   //alert("strDiv"+strDiv);
   return strDiv;
}


/**
domId:表单Form2的名称
action:URL路径
sForm:表单From1的名称
*/
Pub.submitActionWithForm=function(domId,action,sForm){
  /**创建ajax引擎*/
  var req = Pub.newXMLHttpRequest();
  /**事件处理函数*/
  var handlerFunction = Pub.getReadyStateHandler(req, domId,Pub.handleResponse);
  req.onreadystatechange = handlerFunction;
  /**打开连接*/
  req.open("POST", action, false);//false加载完数据后再显示
  
  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
  /**发送请求*/
  var str = Pub.getParams2Str(sForm); 
 
  req.send(str);
  //alert("3");
}

