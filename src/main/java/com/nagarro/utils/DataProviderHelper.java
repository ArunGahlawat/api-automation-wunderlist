package com.nagarro.utils;

import com.nagarro.utils.enums.Config;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.Test;

public class DataProviderHelper {

	@DataProvider(name = "TestDataProvider")
	public static Object[][] getTableArray(Method method) {
		Test test = method.getAnnotation(Test.class);
		int firstRow = Common.getConfig(Config.COMMON).getInt("FIRST_DATA_ROW_INDEX");
		String fileName = "";
		boolean isFound = false;
		for (String group : test.groups()) {
			switch (group.toUpperCase()) {
				case "LISTS":
					fileName = Common.getConfig(Config.COMMON).getString("TEST_DATA_LISTS");
					isFound = true;
					break;
				case "TASKS":
					fileName = Common.getConfig(Config.COMMON).getString("TEST_DATA_TASKS");
					isFound = true;
					break;
				case "SUBTASKS":
					fileName = Common.getConfig(Config.COMMON).getString("TEST_DATA_SUBTASKS");
					isFound = true;
					break;
				case "NOTES":
					fileName = Common.getConfig(Config.COMMON).getString("TEST_DATA_NOTES");
					isFound = true;
					break;
				case "TASK_COMMENTS":
					fileName = Common.getConfig(Config.COMMON).getString("TEST_DATA_TASK_COMMENTS");
					isFound = true;
					break;
			}
			if (isFound)
				break;
		}

		String sheetName = Common.getConfig(Config.COMMON).getString("DEFAULT_SHEET_NAME");
		String[][] tabArray = null;
		try {
			if (!isFound)
				throw new IOException();
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = classloader.getResourceAsStream(fileName);
			if (inputStream == null)
				throw new IOException();
			String fileExtensionName = fileName.substring(fileName.indexOf("."));
			Workbook workbook;
			if(fileExtensionName.equals(".xlsx")){
				workbook = new XSSFWorkbook(inputStream);
			}
			else if (fileExtensionName.equals(".xls")){
				workbook = new HSSFWorkbook(inputStream);
			}
			else {
				System.out.println("Invalid file type");
				throw(new IOException());
			}
			Sheet sheet = workbook.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum();
			int maxColCount = 0;
			for (int i=0;i<=rowCount;i++) {
				maxColCount = maxColCount < sheet.getRow(i).getLastCellNum() ? sheet.getRow(i).getLastCellNum() : maxColCount;
			}


			tabArray = new String[rowCount+1-firstRow][maxColCount];
			for (int i=firstRow;i<=rowCount;i++) {
				Row row = sheet.getRow(i);
				for (int j=0;j<maxColCount;j++) {
					CellType cellType = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType();
					if (cellType == CellType._NONE || cellType == CellType.BLANK)
						tabArray[i-firstRow][j] = "";
					else
						tabArray[i-firstRow][j] = row.getCell(j).getStringCellValue();
				}
			}
		} catch (IOException e){
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return tabArray;
	}
}