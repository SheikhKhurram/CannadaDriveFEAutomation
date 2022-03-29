package com.qa.Utility;

import javafx.scene.input.DataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class ExcelUtility {

    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private Config testConfig;

    public ExcelUtility(Config testConfig, String excelPath, String sheetName)
    {
        try {
            workbook = new XSSFWorkbook(excelPath);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.testConfig = testConfig;
    }

    public Object[][] getAllDetails() {
        int rowNumber = sheet.getLastRowNum();
        int lastColumn = sheet.getRow(0).getLastCellNum();

        Object[][] details = new Object[rowNumber][lastColumn+1];
        DataFormatter formatter = new DataFormatter();

        for (int i =1; i <=rowNumber ; i++)
        {
            details[i-1][0] = testConfig;
            for (int j=0; j <lastColumn; j++) {
                Object value = formatter.formatCellValue(sheet.getRow(i).getCell(j));
                details[i-1][j+1] = value;
            }

        }
        return details;
    }


}
