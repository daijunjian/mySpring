package com.dale.elec.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

public class StringHelp {

	/**将string类型的日期格式转换成Date类型*/
	public static Date convertStringToDate(String dateString) {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException();
		}
		return date;
	}

	/**文件的上传，上传到upload的文件夹下，同时返回上传文件的路径*/
	public static String uploadFile(File file) {
		String basePath = ServletActionContext.getServletContext().getRealPath("/upload");
		String datePath = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
		String fileName = UUID.randomUUID().toString();
		String path = basePath + datePath + fileName;
		//文件上传
		//创建年月日的目录
		File dirPath = new File(basePath+datePath);
		if(!dirPath.exists()){
			dirPath.mkdirs();
		}
		//上传文件
		File newFile = new File(path);
		file.renameTo(newFile);
		return path;
	}
	
	/**日期类型转换成String类型，并精确到年月日时分秒*/
	public static String convertDateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = dateFormat.format(date);
		return strDate;
	}

}
