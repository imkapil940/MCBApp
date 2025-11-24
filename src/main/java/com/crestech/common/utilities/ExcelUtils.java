package com.crestech.common.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.crestech.appium.utils.CommonAppiumTest;



public class ExcelUtils {
	
	public final HashMap<String, HashMap<String, String>> TestDataMap;
	static int UsernameColumnIndex;
	private final String TestData = "Test Data";
	private final String Username = "Username";

	
	/**
	 * @author KAPIL SHARMA
	 * @param name- file path, key and sheet name
	 * @exception file not found handles, IO Exception
	 * @implSpec Read excel at a specified path, sheet and a key value
	 * @return List of String
	 * @throws Exception 
	 */

	public static List<String> readExcel(String path, String key, String sheetName) throws Exception {
		try {
			// initialize variables
			XSSFRow row = null;
			XSSFCell cell = null;
			XSSFWorkbook wb = null;
			XSSFSheet sheet = null;
			DataFormatter formatter = new DataFormatter();
			List<String> val = new ArrayList<String>();
			// Open excel
			try (FileInputStream fis = new FileInputStream(path)) {
				wb = new XSSFWorkbook(fis);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Open sheet of the excel and add all column values of the specified row in a
			// list
			sheet = wb.getSheet(sheetName);
			for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
				row = sheet.getRow(i);
				cell = row.getCell(0);
				if(formatter.formatCellValue(cell).equals(key))
				//if (cell.getStringCellValue().equals(key)) 
					{
					
					for (Iterator<Cell> cit = row.iterator(); cit.hasNext();) {
						Cell cell_value = cit.next();
						val.add(formatter.formatCellValue(cell_value));
						//val.add(cell_value.getStringCellValue());
					}
					break;
				}
			}
				wb.close();
			return val;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @author Shubham Kumar Gupta
	 * @param name- file path
	 * @exception file not found handles, IO Exception
	 * @implSpec Opens specified excel
	 * @return workbook
	 * @throws Exception 
	 */
	public static XSSFWorkbook openExcel(String path) throws Exception {
		try {
			// initialize variables
			XSSFWorkbook wb = null;
			// Open excel in read-only mode via stream to avoid file locking/write attempts
			try (FileInputStream fis = new FileInputStream(path)) {
				wb = new XSSFWorkbook(fis);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return wb;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @author Sneha Aggarwal
	 * @param name- file path
	 * @exception file not found handles, IO Exception
	 * @implSpec Opens specified excel
	 * @return workbook
	 * @throws Exception 
	 */
	public static void closeExcel(XSSFWorkbook wb) throws Exception {
		try {
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @author Sneha Aggarwal
	 * @param name- file path, key and sheet name
	 * @exception file not found handles, IO Exception
	 * @implSpec Read already opened excel using sheet and a key value
	 * @return List of String
	 * @throws Exception 
	 */
	public static List<String> readExcel(String key, String sheetName, XSSFWorkbook wb) throws Exception {
		try {
			// initialize variables
			XSSFRow row = null;
			XSSFCell cell = null;
			XSSFSheet sheet = null;
			List<String> val = new ArrayList<String>();
			DataFormatter formatter = new DataFormatter();
			//String val = formatter.formatCellValue(sheet.getRow(row).getCell(col));
			// Open sheet of the excel and add all column values of the specified row in a
			// list
			sheet = wb.getSheet(sheetName);
			for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
				row = sheet.getRow(i);
				cell = row.getCell(0);
				//if (cell.getStringCellValue().equals(key)) {
				if (formatter.formatCellValue(cell).equals(key)) {
					for (Iterator<Cell> cit = row.iterator(); cit.hasNext();) {
						Cell cell_value = cit.next();						
						//val.add(cell_value.getStringCellValue());
						val.add(formatter.formatCellValue(cell_value));
					}
					break;
				}
			}
			return val;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @author Sneha Aggarwal
	 * @param name- file path, key
	 * @exception file not found handles, IO Exception
	 * @implSpec Read excel at a specified path for the first sheet in the excel and
	 *           a key value
	 * @return List of String
	 * @throws Exception 
	 */

	public static List<String> readExcel(String path, String key) throws Exception {
		try {
			// initialize
			XSSFRow row = null;
			XSSFCell cell = null;
			XSSFWorkbook wb = null;
			XSSFSheet sheet = null;
			List<String> val = new ArrayList<String>();
			// Open workbook
			try (FileInputStream fis = new FileInputStream(path)) {
				wb = new XSSFWorkbook(fis);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Open sheet of the excel and add all column values of the specified row in a
			// list
			sheet = wb.getSheetAt(0);
			for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
				row = sheet.getRow(i);
				cell = row.getCell(0);
				if (cell.getStringCellValue().equals(key)) {
					for (Iterator<Cell> cit = row.iterator(); cit.hasNext();) {
						Cell cell_value = cit.next();
						val.add(cell_value.getStringCellValue());
					}
					break;
				}
			}
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return val;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @author Sneha Aggarwal
	 * @param name- file path, key and sheet name
	 * @exception file not found handles, IO Exception
	 * @implSpec Read excel with row and cell value
	 * @return String
	 * @throws Exception 
	 */

	public static String readExcel(String path, String key, String sheetName, int rowNum, int colNum) throws Exception {
		try {
			XSSFRow row = null;
			XSSFCell cell = null;
			XSSFWorkbook wb = null;
			XSSFSheet sheet = null;
			String val = null;
			try (FileInputStream fis = new FileInputStream(path)) {
				wb = new XSSFWorkbook(fis);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// Open sheet of the excel and get value of the specified cell
			sheet = wb.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);
			val = cell.getStringCellValue();
				wb.close();
			return val;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @author Sneha Aggarwal
	 * @param name- file path, data to be written and sheet name
	 * @exception file not found handles, IO Exception
	 * @implSpec Create a new excel and write to the first sheet and first row
	 * @return List of String
	 * @throws Exception 
	 */

	public static void writeToNewExcel(String sheetName, List<String> data, String path) throws Exception {
		try {
			// Create a Workbook
			XSSFWorkbook workbook = new XSSFWorkbook();
			// Create a Sheet
			XSSFSheet sheet = workbook.createSheet(sheetName);
			// Create a row
			XSSFRow row = sheet.createRow(0);
			// Create cell
			for (int i = 0; i < data.size(); i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(data.get(i));
			}
			// Save file and close
			FileOutputStream fileOut;
				fileOut = new FileOutputStream(path);
				workbook.write(fileOut);
				fileOut.close();
				workbook.close();
		} catch (FileNotFoundException e) {
			throw new Exception(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @author Sneha Aggarwal
	 * @param name- file path, data to be appended and sheet name
	 * @exception file not found handles, IO Exception
	 * @implSpec Create a new excel and write to the first sheet and first row
	 * @return List of String
	 * @throws Exception 
	 */

	public static void writeToExcel(String sheetName, List<String> data, String path) throws Exception {
		// Obtain a workbook from the excel file
		File file = new File(path);
		FileInputStream inputStream = null;
		// Create an object of FileInputStream class to read excel file
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(inputStream);
			// Close input stream as we no longer need to read once workbook is loaded
			inputStream.close();
			inputStream = null;
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		// Create a Sheet
		XSSFSheet sheet = null;
		boolean temp = false;
		XSSFRow row = null;
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			System.out.println(wb.getSheetAt(i).getSheetName());
			if (wb.getSheetAt(i).getSheetName().equalsIgnoreCase(sheetName)) {
				sheet = wb.getSheet(sheetName);
				// Read the excel
				int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
				if (rowCount == 0) {
					row = sheet.createRow(0);
					for (int k = 0; k < data.size(); k++) {
						Cell cell = row.createCell(k);
						cell.setCellValue(data.get(k));
						System.out.println(data.get(k));
					}
				} else {
					for (int j = 0; j < rowCount; j++) {
						row = sheet.createRow(rowCount + 1);
						for (int k = 0; k < data.size(); k++) {
							Cell cell = row.createCell(k);
							cell.setCellValue(data.get(k));
						}
					}
				}
				temp = true;
				break;
			}
		}
		System.out.println(temp);
		if (!temp) {
			sheet = wb.createSheet(sheetName);
			row = sheet.createRow(0);
			// Create cell
			for (int i = 0; i < data.size(); i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(data.get(i));
			}
		}
		// Save file and close
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(path);
			try {
				wb.write(fileOut);
			} finally {
				fileOut.close();
			}
			wb.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @author Sneha Aggarwal
	 * @param name- file path and sheet name
	 * @exception file not found handles, IO Exception
	 * @return data in excel sheet in the hashmap
	 * @throws Exception 
	 */
	public static HashMap<String, String> getMapData(String path, String sheetName) throws Exception {
		FileInputStream fis;
		XSSFWorkbook wb = null;
		HashMap<String, String> dataMap = new HashMap<String, String>();
		try {
			fis = new FileInputStream(path);
			try {
				wb = new XSSFWorkbook(fis);
			} finally {
				fis.close();
			}
			XSSFSheet sheet = wb.getSheet(sheetName);
			int lastRow = sheet.getLastRowNum();

			// Looping over entire row
			for (int i = 0; i <= lastRow; i++) {
				XSSFRow row = sheet.getRow(i);
				// 1st Cell as Value
				Cell valueCell = row.getCell(1);
				// 0th Cell as Key
				Cell keyCell = row.getCell(0);
				String value = valueCell.getStringCellValue().trim();
				String key = keyCell.getStringCellValue().trim();
				// Putting key & value in dataMap
				dataMap.put(key, value);
			}
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		// Returning excelFileMap
		return dataMap;
	}
	
	public ExcelUtils() throws Exception {
		try {
			File sourceFile = new File(System.getProperty("user.dir") + "//TestData//TestData.xlsx");
			FileInputStream inputStream = new FileInputStream(sourceFile);
			Workbook workbook = null;
			try {
				workbook = new XSSFWorkbook(inputStream);
				this.TestDataMap = getTestData(workbook);
			} catch (Exception e) {
				throw new Exception("Unable to read file. Exception : " + e.getLocalizedMessage());
			} finally {
				inputStream.close();
				workbook.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private HashMap<String, HashMap<String, String>> getTestData(Workbook workbook) throws Exception {
		try {
			Sheet sheet = workbook.getSheet(this.TestData);
			int rowCount = sheet.getLastRowNum();
			DataFormatter dataFormatter = new DataFormatter();
			Row headerRow = sheet.getRow(0);
			int columnCount = headerRow.getLastCellNum();
			/*
			 * The keys of the OuterMap will be the TestDataIDs (e.g.: Username1, Username2,..., etc.)
			 * The values of the OuterMap will be an Inner HashMap which will contain
			 * dynamic number of keys and values for each TestDataID.
			 */
			HashMap<String, HashMap<String, String>> OuterMap = new HashMap<String, HashMap<String, String>>();

			// getting column index of test data ID's.
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				String columnName = dataFormatter.formatCellValue(headerRow.getCell(columnIndex)).trim();
				if (columnName.equalsIgnoreCase(this.Username)) {
					UsernameColumnIndex = columnIndex;
				}
			}

			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++) {

				Row row = sheet.getRow(rowIndex);

				// This map will become the value for the keys in OuterMap.
				HashMap<String, String> InnerMap = new HashMap<String, String>();

				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					/*
					 * InnerValue will contain the values for the keys of InnerMap. E.g.: For Username1
					 * (key of OuterMap), the value will be InnerMap. For InnerMap, InnerKey ->
					 * PortType (header) and InnerValue -> "NPT".
					 */

					String InnerValue = dataFormatter.formatCellValue(row.getCell(columnIndex), evaluator);
					if (!InnerValue.trim().isEmpty() && columnIndex != UsernameColumnIndex) {
						//InnerValue = XYZ
						// At columnIndex = 0, the headerCell refers to the cell containing "S.No."
						Cell headerCell = headerRow.getCell(columnIndex);

						// Store HeaderCell string as key of InnerDataMap
						String InnerKey = dataFormatter.formatCellValue(headerCell).trim();
						if ((InnerKey.length() > 0) && (InnerKey.charAt(0) != '#')) {
							if (InnerMap.containsKey(InnerKey))
								throw new Exception("Duplicate entries in Test Data sheet for the Header Name : '"
										+ InnerKey + "'");
							else
								InnerMap.put(InnerKey, InnerValue);
						}
					}

					String Username = dataFormatter.formatCellValue(row.getCell(UsernameColumnIndex));
					OuterMap.put(Username, InnerMap);
				}
			}
			return OuterMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
