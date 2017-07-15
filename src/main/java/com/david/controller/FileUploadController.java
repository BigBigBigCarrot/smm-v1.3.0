package com.david.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.david.util.excel.ExcelReadUtils;

@Controller
@RequestMapping("/FileUploadController")
public class FileUploadController {
	@RequestMapping(value = "/fileUploadPage")
	public String FileUploadPage( HttpServletRequest request, HttpServletResponse response) {
		return "fileUploadPage";		 	
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest request,
			@RequestParam("file") MultipartFile file){
		// 1、读取excel数据.......start...........................
		String filename = file.getOriginalFilename();
		if (filename == null || "".equals(filename)) {
			return"你未上传文件";
		}
		if (filename.indexOf(".xls") == -1 && filename.indexOf(".xlsx") == -1) {
			return "上传文件格式错误";
		}
		Workbook wb = null;
		InputStream input = null;
		try {
			input = file.getInputStream();
			wb = WorkbookFactory.create(input);
		} catch (Exception e) {
			return "读取excel数据失败";
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e1) {
					return "关闭excel失败";
				}
			}
		}
		
		
		List<String[]> dataList = ExcelReadUtils.readExcel(wb, 0, 0, 0);//读取excel
		return "success";
	}
}
