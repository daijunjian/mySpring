package com.dale.elec.utils;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;

public class JFreeChartUtils {

	/**  
	* @Name: generatorBarChart
	* @Description: 使用结果集，生成柱状图
	* @Return: String：生成图片的文件名
	*/
	public static String generatorBarChart(List<Object[]> list) {
		//构造图形的数据集合
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;list!=null && i<list.size();i++){
			Object [] object = list.get(i);
			dataset.addValue(Integer.parseInt(object[2].toString()), object[0].toString(), object[1].toString());
		}
		JFreeChart chart = ChartFactory.createBarChart3D("用户统计报表（所属单位）", //图形主标题 
										                 "所属单位名称",//X轴外的标题 
										                 "数量", //Y轴外的标题
										                 dataset, //图表的数据集合
										                 PlotOrientation.VERTICAL, //图形的显示形式（水平/垂直）
										                 true, //是否显示子标题
										                 true, //是否生成工具数据的提示
										                 true  //是否生成URL的连接
						                                 );
		//处理主标题乱码
		chart.getTitle().setFont(new Font("宋体",Font.BOLD,18));
		//处理子标题乱码
		chart.getLegend().setItemFont(new Font("宋体",Font.BOLD,15));
		//获取图表区域对象(xxxxPlot)
		CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
		//获取X轴的对象（xxxxDomainAxis）
		CategoryAxis3D categoryAxis3D = (CategoryAxis3D) categoryPlot.getDomainAxis();
		//获取Y轴的对象（xxxxRangeAxis）
		NumberAxis3D numberAxis3D = (NumberAxis3D) categoryPlot.getRangeAxis();
		//处理X轴上的乱码
		categoryAxis3D.setTickLabelFont(new Font("宋体",Font.BOLD,15));
		//处理X轴外的乱码
		categoryAxis3D.setLabelFont(new Font("宋体",Font.BOLD,15));
		//处理Y轴上的乱码
		numberAxis3D.setTickLabelFont(new Font("宋体",Font.BOLD,15));
		//处理Y轴外的乱码
		numberAxis3D.setLabelFont(new Font("宋体",Font.BOLD,15));
		
		//改变Y轴上刻度，以一为单位
		numberAxis3D.setAutoTickUnitSelection(false);
		NumberTickUnit unit = new NumberTickUnit(1);
		numberAxis3D.setTickUnit(unit);//设置刻度
		
		//获取绘图区域对象（xxxxRenderer）
		BarRenderer3D barRenderer3D = (BarRenderer3D) categoryPlot.getRenderer();
		//改变图形的宽度
		barRenderer3D.setMaximumBarWidth(0.07);
		//在图形上生成值所对应的数字
		barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		barRenderer3D.setBaseItemLabelsVisible(true);
		barRenderer3D.setBaseItemLabelFont(new Font("宋体",Font.BOLD,15));
		//项目中的使用，在项目路径下要生成图片，以文件的形式生成
		//获取chart的路径
		String path = ServletActionContext.getServletContext().getRealPath("/chart");
		String filename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".png";
		File file = new File(path + "/" + filename);
		try {
			ChartUtilities.saveChartAsPNG(file, chart, 800, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return filename;
	}

}
