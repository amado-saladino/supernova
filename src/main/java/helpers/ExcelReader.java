package helpers;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ExcelReader {

	private static XSSFWorkbook workBook;
	private static XSSFSheet sheet;

	private static void setDataSource(String path) {
		File excelFile = new File(path);
		try {
			FileInputStream inputStream = new FileInputStream(excelFile);
			workBook = new XSSFWorkbook(inputStream);
		}

		catch (FileNotFoundException e) {
			e.getMessage();
		}

		catch (IOException e) {
			e.getMessage();
		}
	}

	private static void selectSheet(String sheetName) {
		sheet = workBook.getSheet(sheetName);
	}

	private static String readCell(int rowIndex, int colIndex) {
		return sheet.getRow(rowIndex).getCell(colIndex).getStringCellValue();
	}

	public static Object[][] loadTabularData(String file, String sheetName) {
		setDataSource(file);
		selectSheet(sheetName);
		
		Object[][] testData = null;

		int rows = sheet.getLastRowNum() + 1;
		int columns = sheet.getRow(0).getLastCellNum();

		testData = new Object[rows - 1][columns];

		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				testData[i - 1][j] = readCell(i, j);
			}
		}
		return testData;
	}

	public static Iterator<Object[]> dataIterator(String file, String sheetName) {
		setDataSource(file);
		selectSheet(sheetName);
		List<Object[]> data = new ArrayList<>();
		List<String> cells = new ArrayList<>();
		int rows = sheet.getLastRowNum() + 1;
		int columns = sheet.getRow(0).getLastCellNum();

		for (int i = 1; i < rows; i++) {
			cells.clear();
			for (int j = 0; j < columns; j++) {
				cells.add(readCell(i, j));
			}
			data.add(cells.toArray());
		}
		return data.iterator();
	}

	public static Iterator<Object> dataMapIterator(String file, String sheetName) {
		setDataSource(file);
		selectSheet(sheetName);
		List<Object> data = new ArrayList<>();
		Map row;
		int rows = sheet.getLastRowNum() + 1;
		int columns = sheet.getRow(0).getLastCellNum();

		for (int i = 1; i < rows; i++) {
			row = new HashMap();
			for (int j = 0; j < columns; j++) {
				row.put(readCell(0,j),readCell(i,j));
			}
			data.add(row);
		}
		return data.iterator();
	}

	public static Object[] getMappedSheet(String file,String sheetName) {
		setDataSource(file);
		selectSheet(sheetName);

		Object[] testData = null;
		Map row;
		int rows = sheet.getLastRowNum() + 1;
		int columns = sheet.getRow(0).getLastCellNum();

		testData = new Object[rows - 1];

		for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
			row = new HashMap();
			for (int columnIndex = 0; columnIndex < columns; columnIndex++) {
				row.put(readCell(0,columnIndex),readCell(rowIndex,columnIndex));
				testData[rowIndex-1] = row;
			}
		}
		return testData;
	}

}
