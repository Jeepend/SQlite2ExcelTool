package com.jeepend.sqlite;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if (args.length > 0) {
			String filePath = args[0];
			File file = new File(filePath);
			if (!file.exists()) {
				System.out.println("File not exist!");
				return;
			}
			SQLiteUtil util = new SQLiteUtil();
			util.loadDB(file);
			ExcelUtil excelUtil = new ExcelUtil();
			SimpleDateFormat format = new SimpleDateFormat("_MMdd_HHmmss");
			String fileName = file.getName() + format.format(new Date());
			excelUtil.generateExcel(fileName, util.tableList, util.totalRows);
		}
	}

}
