package com.jeepend.sqlite;

public class PrintUtil {
	public static final int SEPERATOR_SLEEP_TIME = 10;
	public static final int NORMAL_SLEEP_TIME = 20;
	public static void print(String string, int sleep) {
		int len = string.length();
		for (int i = 0; i < len; i++) {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(string.charAt(i));
		}
	}

	public static void print(String string) {
		print(string, NORMAL_SLEEP_TIME);
	}
	
	public static void println(String string, int sleep) {
		int len = string.length();
		for (int i = 0; i < len; i++) {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(string.charAt(i));
		}
		System.out.println();
	}

	public static void println(String string) {
		println(string, NORMAL_SLEEP_TIME);
	}
	public static void println(){
		System.out.println();
	}
}
