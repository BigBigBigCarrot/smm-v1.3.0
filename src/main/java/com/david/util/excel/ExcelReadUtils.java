package com.david.util.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * excel读取工具
 * 
 * @author ljingxiong<jingxiong.li@heysroad.com>
 * @version V1.0(2015年11月16日 下午7:47:38)
 */
public class ExcelReadUtils {

	/**
	 * 读取excel数据
	 * 
	 * @param path
	 */
	public static List<String[]> readExcelToObj(String path) {
		List<String[]> dataList = new ArrayList<String[]>();
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(new File(path));
			dataList = readExcel(wb, 0, 0, 0);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * 读取excel文件
	 * 
	 * @param wb
	 * @param sheetIndex
	 *            sheet页下标：从0开始
	 * @param startReadLine
	 *            开始读取的行:从0开始
	 * @param tailLine
	 *            去除最后读取的行
	 */
	public static List<String[]> readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine) {
		List<String[]> dataList = new ArrayList<String[]>();

		Sheet sheet = wb.getSheetAt(sheetIndex);
		int columnNum = 0;
		if (sheet.getRow(0) != null) {
			columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
		}
		if (columnNum > 0) {
			Row row = null;
			for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
				String[] singleRowValue = new String[columnNum];
				row = sheet.getRow(i);
				if(row == null){
					continue;
				}
				for (int cellnum = 0; cellnum < columnNum; cellnum++) {
					Cell c = row.getCell(cellnum, Row.CREATE_NULL_AS_BLANK);
					boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
					// 判断是否具有合并单元格
					if (isMerge) {
						singleRowValue[cellnum] = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
					} else {
						singleRowValue[cellnum] = getCellValue(c);
					}
				}
				dataList.add(singleRowValue);
			}
		}
		return dataList;

	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public static String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}

		return null;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				return "";
			case Cell.CELL_TYPE_BOOLEAN:
				return Boolean.toString(cell.getBooleanCellValue());
				// 数值
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return String.valueOf(cell.getDateCellValue());
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String temp = cell.getStringCellValue();
					// 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
					if (temp.indexOf(".") > -1) {
						return String.valueOf(new Double(temp)).trim();
					} else {
						return temp.trim();
					}
				}
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue().trim();
			case Cell.CELL_TYPE_ERROR:
				return "";
			case Cell.CELL_TYPE_FORMULA:
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String cellValue = cell.getStringCellValue();
				if (cellValue != null) {
					cellValue = cellValue.replaceAll("#N/A", "").trim();
				}
				return cellValue;
			default:
				return "";
		}
	}

	public static void main(String[] args) {
		List<String[]> dataList = readExcelToObj("C:\\Users\\admin\\Desktop\\合并.xls");
		System.out.println(dataList);
	}

}
