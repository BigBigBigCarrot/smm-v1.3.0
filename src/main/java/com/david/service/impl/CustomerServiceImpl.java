package com.david.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.mybatis.dao.CustomerDao;
import com.david.mybatis.entity.Customer;
import com.david.service.CustomerService;
import com.david.util.excel.ExportUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service("CustomerService")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;

	@Override
	public Customer getCustomerById(Integer id) {
		return customerDao.get(id);
	}

	@Override
	public List<Map<String, Object>> findByCondition(Map<String, Object> map) {
		return customerDao.findByCondition(map);
	}

	@Override
	public List<Map<String, Object>> findByCondition(Map<String, Object> map,
			PageBounds pageBounds) {
		return customerDao.findByCondition(map, pageBounds);
	}

	@Override
	public void exportExcel(ServletOutputStream outputStream) {
		List<Map<String, Object>> list = customerDao.findByCondition(new HashMap<String, Object>());

		String[] titles = { "id", "firstName", "lastName", "dob", "phone" };
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet();
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}

		// 构建表体数据
		if (list != null && list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				XSSFRow bodyRow = sheet.createRow(j + 1);
				Map<String, Object> customer = list.get(j);
				cell = bodyRow.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(String.valueOf(customer.get("id")));

				cell = bodyRow.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(String.valueOf(customer.get("firstName")));

				cell = bodyRow.createCell(2);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(String.valueOf(customer.get("lastName")));

				cell = bodyRow.createCell(3);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(String.valueOf(customer.get("dob")));

				cell = bodyRow.createCell(4);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(String.valueOf(customer.get("phone")));

			}

			try {
				workBook.write(outputStream);
				outputStream.flush();
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void exportExcelCellCombined(ServletOutputStream outputStream) {
		Workbook wb=new HSSFWorkbook();  
        
        Sheet sheet=wb.createSheet();  
        /* 
         * 设定合并单元格区域范围 
         *  firstRow  0-based 
         *  lastRow   0-based 
         *  firstCol  0-based 
         *  lastCol   0-based 
         */  
        CellRangeAddress cra=new CellRangeAddress(0, 3, 3, 9);
          
        //在sheet里增加合并单元格  
        sheet.addMergedRegion(cra);  
          
        Row row = sheet.createRow(0);  
          
        Cell cell_1 = row.createCell(3);  
          
        cell_1.setCellValue("When you're right , no one remembers, when you're wrong ,no one forgets .");  
          
        //cell 位置3-9被合并成一个单元格，不管你怎样创建第4个cell还是第5个cell…然后在写数据。都是无法写入的。  
        Cell cell_2 = row.createCell(10);  
          
        cell_2.setCellValue("what's up ! ");  
         
		try {
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void exportQuoteDetails(ServletOutputStream outputStream) {
		String[] titles = { "物料单号", "物料名称", "规格型号", "询价次数", "需求区间","报价有效时间" ,"设计人员",
				"报价单状态","备注","历史最低报价（￥）","中标供应商","报价（￥）","中标供应商历史最低报价（￥）"};
		
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook wb = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = wb.createSheet();
		ExportUtil exportUtil = new ExportUtil(wb, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		
		//设置样式
		//CellStyle cellStyle = wb.createCellStyle(); 
		//cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
		
        //sheet.autoSizeColumn(1, true);
        /* 
         * 设定合并单元格区域范围 
         *  firstRow  0-based 
         *  lastRow   0-based 
         *  firstCol  0-based 
         *  lastCol   0-based 
         */  
        CellRangeAddress cra;
        Cell cell;
        // 构建表头
     	XSSFRow row0 = sheet.createRow(0);
     	XSSFRow row1 = sheet.createRow(1);
     	
        //合并第一行和第二行的单元格（供应商所在列除外）
        for(int i=0;i<=titles.length-1;i++){
        	cra=new CellRangeAddress(0, 1, i, i);
        	//在sheet里增加合并单元格  
            sheet.addMergedRegion(cra);
            //生成单元
            cell=row0.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(headStyle);
        	
            cell=row1.createCell(i);
            cell.setCellStyle(headStyle);
        }
        int supplierListLength=10;
        int supplierNameLength=10;
        
        for(int i=titles.length;i<=titles.length+supplierListLength*3;i=i+3){
        	cra=new CellRangeAddress(0, 0, i, i+2);
        	//在sheet里增加合并单元格  
            sheet.addMergedRegion(cra);
            
            //生成单元
            cell=row0.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue("供应商"+i);
            
            //第二行的“报价”、“对比价”、“历史最低价”
            cell=row1.createCell(i);//报价
            cell.setCellStyle(headStyle);
            cell.setCellValue("报价");
            cell=row1.createCell(i+1);//对比价
            cell.setCellStyle(headStyle);
            cell.setCellValue("对比价");
            cell=row1.createCell(i+2);//实力最低价
            cell.setCellStyle(headStyle);
            cell.setCellValue("实力最低价");
        }
        
		try {
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
