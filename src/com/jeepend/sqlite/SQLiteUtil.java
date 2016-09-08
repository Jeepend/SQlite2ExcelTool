package com.jeepend.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLiteUtil {
	ArrayList<Table> tableList = new ArrayList<Table>();
	int totalRows = 0;
	public void loadDB(File file) throws Exception {
		if (!file.exists()) {
			PrintUtil.println("DB file not exist!");
			return;
		}
		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
		
		Statement statement = connection.createStatement();
		ResultSet set = statement.executeQuery("select name from sqlite_master where type='table'");
		PrintUtil.println("=============================================================", PrintUtil.SEPERATOR_SLEEP_TIME);
		PrintUtil.println("SQLite2Excel Tool 1.0");
		PrintUtil.println("It will take 3 steps to create an excel.");
		PrintUtil.println("=============================================================", PrintUtil.SEPERATOR_SLEEP_TIME);
		
		PrintUtil.println("1. Start to analyse table...");
		while(set.next()) {
			String tableName = set.getString(1);
			if (!tableName.equals("android_metadata")) {
				getTable(connection.createStatement(), tableName);
				System.out.print("\b");
			}
		}
		PrintUtil.println();
		PrintUtil.println("Finish analyse table!");
		PrintUtil.println("=============================================================", PrintUtil.SEPERATOR_SLEEP_TIME);
	}

	private void getTable(Statement statement, String tableName) throws Exception {
		System.out.print("->");
		Table table = new Table();
		table.tableName = tableName;
		ResultSet set = statement.executeQuery("select * from " + tableName);
		ResultSetMetaData metaData = set.getMetaData();
		int headCount = metaData.getColumnCount();
		table.heads = new String[headCount];
		for (int i = 0; i < headCount; i ++) {
			table.heads[i] = metaData.getColumnName(i + 1);
		}
		while(set.next()) {
			Row row = new Row();
			row.cells = new String[headCount];
			for (int i = 0; i < headCount; i ++) {
				row.cells[i] = set.getString(table.heads[i]);
				String headNameString = table.heads[i].toLowerCase();
				if (headNameString.contains("time")) {
					row.cells[i] = convertToFormatedString(row.cells[i]);
				}
			}
			table.rows.add(row);
		}
		tableList.add(table);
		totalRows += table.rows.size();
	}

	private String convertToFormatedString(String timeString) {
		try {
			long timeValue = Long.parseLong(timeString);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(new Date(timeValue * 1000));
		} catch (Exception e) {
			// TODO: handle exception
			return timeString;
		}
		
	}
}
