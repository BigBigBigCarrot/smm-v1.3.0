package com.david.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.david.service.CustomerService;
import com.david.util.excel.ExcelFileNameUtil;

@Controller
@RequestMapping("/DownloadController")
public class DownloadController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/downloadPage")
	public String download( HttpServletRequest request, HttpServletResponse response) {
		return "downloadPage";		 	
	}
	
	/**
	 * 生成并导出excel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exportExcel",method = RequestMethod.GET)
	public String exportExcel(
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = "myExcel";
			fileName = ExcelFileNameUtil.processFileName(request, fileName);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xls").getBytes()));
			customerService.exportExcel(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成并导出excel(合并单元格)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exportExcelCellCombined",method = RequestMethod.GET)
	public String exportExcelCellCombined(
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = "myExcel";
			fileName = ExcelFileNameUtil.processFileName(request, fileName);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xls").getBytes()));
			customerService.exportExcelCellCombined(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成并导出excel(合并单元格)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/exportQuoteDetails",method = RequestMethod.GET)
	public String exportQuoteDetails(
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			String fileName = "myExcel";
			fileName = ExcelFileNameUtil.processFileName(request, fileName);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((fileName + ".xls").getBytes()));
			customerService.exportQuoteDetails(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
