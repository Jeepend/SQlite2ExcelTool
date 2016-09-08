package com.jeepend.sqlite;

import java.util.Arrays;

public class Row {
	String[] cells;

	@Override
	public String toString() {
		return "Row [cells=" + Arrays.toString(cells) + "]";
	}
	
}
