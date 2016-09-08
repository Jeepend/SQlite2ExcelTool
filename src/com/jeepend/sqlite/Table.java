package com.jeepend.sqlite;

import java.util.ArrayList;
import java.util.Arrays;

public class Table {
	String tableName;
	String[] heads;
	ArrayList<Row> rows = new ArrayList<Row>();
	@Override
	public String toString() {
		return "Table [tableName=" + tableName + ", heads="
				+ Arrays.toString(heads) + ", rows=" + rows
				+ "]";
	}
	
	
}
