package ar.edu.itba.pod.agulo.util;

import ar.edu.itba.pod.agulo.implementations.EnhancedPoolPrinterDispatcher;
import ar.edu.itba.pod.agulo.printer.Document;
import ar.edu.itba.pod.agulo.printer.Printer;
import ar.edu.itba.pod.agulo.printer.PrinterDispatcher;

public class Test {
	
	public static int TRIALS = 10;
	public static int REQUESTS = 50;
	
	public static PrinterDispatcher dispatcher;
	
	
	public static void main(String[] args) {
		int poolSize = 5;
		long interval = 5;
		Logger.log("Limite de: " + poolSize + " impresoras.");
		dispatcher = new EnhancedPoolPrinterDispatcher(poolSize);
		try {
			printDocumentsUsing(0);
			addPrinters(interval);
			removePrinters(interval);
			addRemovePrinters(interval);
		} catch (TimeOutException e) {
			Logger.log("lanz— TimeOutException.");
		}
		System.out.println("fin");
	}
	
	private static void addPrinters(long interval) {
		for (int i = 0; i < 10; i++) {
			((EnhancedPoolPrinterDispatcher) dispatcher).addPrinter(new Printer());
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void removePrinters(long interval) {
		for (int i = 0; i < 10; i++) {
			((EnhancedPoolPrinterDispatcher) dispatcher).removePrinter();
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void addRemovePrinters(long interval) {
		for (int i = 0; i < 10; i++) {
			if (i%2 == 0)
				((EnhancedPoolPrinterDispatcher) dispatcher).addPrinter(new Printer());
			else
				((EnhancedPoolPrinterDispatcher) dispatcher).removePrinter();
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void printDocumentsUsing(long millis) {
		long totalSpent = 0;
		
		for (int i = 0; i < TRIALS; i++) {
			long ini = System.currentTimeMillis();
			for (int j = 0; j < REQUESTS; j++) {
				dispatcher.printDocument(new Document(1, true, "a document"));
				if (millis > 0)
					try {
						Thread.sleep(millis);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			long end = System.currentTimeMillis();
			totalSpent += end - ini;
		}
		Logger.log(dispatcher.toString() + " " + totalSpent/TRIALS);
		
	}
}
