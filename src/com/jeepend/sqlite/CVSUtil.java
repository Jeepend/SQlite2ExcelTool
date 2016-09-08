package com.jeepend.sqlite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;


public class CVSUtil {
	public void generateCVS(ArrayList<Table> tableList) throws Exception {
		int count = 0;
		for (int i = 0; i < tableList.size(); i++) {
			Table table = tableList.get(i);
			File file = new File(table.tableName + ".csv");
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writeFile(writer, table.heads);
			// add rows
			for (int r = 0; r < table.rows.size(); r++) {
				Row row = table.rows.get(r);
				writeFile(writer, row.cells);
				System.out.println("save row:" + count);
				count++;
			}
		}
	}

	private void writeFile(FileWriter writer, String[] array) throws Exception{
		StringBuilder builder = new StringBuilder();
		// add title
		for (int j = 0; j < array.length; j++) {
			builder.append("\"" + array[j] + "\"").append(',');
		}
		builder.deleteCharAt(builder.length() - 1).append("\n");
		writer.write(builder.toString());
		writer.flush();
	}
}
