package com.jeepend.sqlite;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelUtil {
	public void generateExcel(String fileName, ArrayList<Table> tableList,
			int rows) throws Exception {
		PrintUtil.println("2. Cache all data to POI...");
		SXSSFWorkbook workbook = new SXSSFWorkbook();

		int count = 0;
		int oldPercent = -1;
		int backCount = 0;
		PrintUtil.print("caching: ");
		for (int i = 0; i < tableList.size(); i++) {
			Table table = tableList.get(i);
			SXSSFSheet sheet = workbook.createSheet(table.tableName);

			org.apache.poi.ss.usermodel.Row xRow = sheet.createRow(0);
			// add title
			for (int j = 0; j < table.heads.length; j++) {
				Cell cell = xRow.createCell(j);
				cell.setCellValue(table.heads[j]);
			}
			// add rows
			for (int r = 0; r < table.rows.size(); r++) {
				xRow = sheet.createRow(r + 1);
				Row row = table.rows.get(r);
				for (int c = 0; c < row.cells.length; c++) {
					Cell cell = xRow.createCell(c);
					cell.setCellValue(row.cells[c]);
				}
				count++;
				int percent = (int) (((float) count) / rows * 100);
				if (oldPercent != percent) {
					printBack(backCount);
					System.out.print(String.format("%d%%", percent));
					oldPercent = percent;
					backCount = percent < 10 ? 2 : 3;
				}
			}
			sheet.trackAllColumnsForAutoSizing();
			// Set column to auto-width
			for (int c = 0; c < table.heads.length; c++) {
				sheet.autoSizeColumn(c);
			}
			sheet.untrackAllColumnsForAutoSizing();
			// Freeze first row
			sheet.createFreezePane(0, 1, 0, 1);
			// Set auto filter
			CellRangeAddress rangeAddress = new CellRangeAddress(0,
					table.rows.size(), 0, table.heads.length - 1);
			sheet.setAutoFilter(rangeAddress);
		}
		PrintUtil.println("\nFinish caching all data to POI!");
		PrintUtil
				.println(
						"=============================================================",
						PrintUtil.SEPERATOR_SLEEP_TIME);
		PrintUtil.println("3. Start saving file...");
		File file = new File(fileName + ".xlsx");
		if (file.exists()) {
			file.delete();
		}
		FileOutputStream outputStream = new FileOutputStream(file);
		workbook.write(outputStream);
		outputStream.close();
		workbook.close();
		PrintUtil.println("Finish saving file!");
		PrintUtil.println("Output: " + file.getAbsolutePath());
		PrintUtil.println("Job done!");
		PrintUtil
				.println(
						"=============================================================",
						PrintUtil.SEPERATOR_SLEEP_TIME);
	}

	private void printBack(int backCount) {
		for (int i = 0; i < backCount; i++)
			System.out.print("\b");
	}
}
