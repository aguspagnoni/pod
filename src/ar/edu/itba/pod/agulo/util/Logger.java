package ar.edu.itba.pod.agulo.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	
	private static String logPath = "/Users/agustinpagnoni/Documents/Eclipse workspaces/pod ws/POD/logs";
	
	public static void log(String s) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(logPath), true);
			fw.append(s + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
