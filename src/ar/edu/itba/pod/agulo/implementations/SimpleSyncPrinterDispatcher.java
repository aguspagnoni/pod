package ar.edu.itba.pod.agulo.implementations;

import ar.edu.itba.pod.agulo.printer.Document;
import ar.edu.itba.pod.agulo.printer.Printer;
import ar.edu.itba.pod.agulo.printer.PrinterDispatcher;

public class SimpleSyncPrinterDispatcher implements PrinterDispatcher {
	
	private Printer printer = new Printer();

	@Override
	public void printDocument(Document document) {
		printer.print(document);
	}

	@Override
	public void finishPrintRequests() {
	}

}
