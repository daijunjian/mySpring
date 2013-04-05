package com.dale.elec.utils;

/**
 * 系统数据导出Excel 生成器
 * @version 1.0
 */
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelFileGenerator {

	private final int SPLIT_COUNT = 15; //Excel每个工作簿的行数

	private ArrayList fieldName = null; //excel标题数据集

	private ArrayList fieldData = null; //excel数据内容	

	private HSSFWorkbook workBook = null;//POI核心对象（顶层对象）

	/**
	 * 构造器
	 * @param fieldName 结果集的字段名
	 * @param data
	 */
	public ExcelFileGenerator(ArrayList fieldName, ArrayList fieldData) {

		this.fieldName = fieldName;
		this.fieldData = fieldData;
	}

	/**
	 * 创建HSSFWorkbook对象
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook createWorkbook() {

		workBook = new HSSFWorkbook();//实例化HSSFWorkbook对象
		int rows = fieldData.size();//数据结果集
		int sheetNum = 0;

		//这个地方就是算出总共有几页
		if (rows % SPLIT_COUNT == 0) {
			sheetNum = rows / SPLIT_COUNT;
		} else {
			sheetNum = rows / SPLIT_COUNT + 1;
		}

		
		for (int i = 1; i <= sheetNum; i++) {//按照sheetNum进行分页
			HSSFSheet sheet = workBook.createSheet("Page " + i);//1、使用workbook对象创建sheet对象
			HSSFRow headRow = sheet.createRow((short) 0); //2、使用sheet对象创建row对象，下标从0开始，表示第一行
			for (int j = 0; j < fieldName.size(); j++) {//根据fieldName封装excel标题
				HSSFCell cell = headRow.createCell((short) j);//3、使用row对象用来创建cell列
				//添加样式
				//设置每个单元格的间距
				sheet.setColumnWidth((short)j, (short)6000);
				HSSFCellStyle style = workBook.createCellStyle();//创建单元格样式
				HSSFFont font = workBook.createFont();//创建字体
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//设置字体加粗
				short color = HSSFColor.RED.index;//从HSSFColor获取红色的索引
				font.setColor(color);//设置字体的颜色为红色
				style.setFont(font);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);//设置单元格类型为String
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);//设置单元格的编码格式为UTF-16
				if(fieldName.get(j) != null){
					cell.setCellStyle(style);
					cell.setCellValue((String) fieldName.get(j));//设置单元格中的值
				}else{
					cell.setCellStyle(style);
					cell.setCellValue("-");
				}
			}

			for (int k = 0; k < (rows < SPLIT_COUNT ? rows : SPLIT_COUNT); k++) {
				if (((i - 1) * SPLIT_COUNT + k) >= rows)//如果第一页显示15条数据之后，就在第二也继续显示
					break;
				HSSFRow row = sheet.createRow((short) (k + 1));//使用sheet创建row
				//将数据内容放入excel单元格
				ArrayList rowList = (ArrayList) fieldData.get((i - 1)
						* SPLIT_COUNT + k);//获取excel的数据结果集，并完成分页
				for (int n = 0; n < rowList.size(); n++) {
					HSSFCell cell = row.createCell((short) n);//使用row创建cell对象
					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					if(rowList.get(n) != null){
						cell.setCellValue((String) rowList.get(n).toString());
					}else{
						cell.setCellValue("");
					}
				}
			}
		}
		return workBook;
	}

	public void expordExcel(OutputStream os) throws Exception {
		workBook = createWorkbook();
		workBook.write(os);//输出excel文件
		os.close();
	}

}
