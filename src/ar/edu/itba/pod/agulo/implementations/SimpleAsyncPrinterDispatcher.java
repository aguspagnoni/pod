package ar.edu.itba.pod.agulo.implementations;

import java.util.LinkedList;
import java.util.Queue;

import ar.edu.itba.pod.agulo.printer.Document;
import ar.edu.itba.pod.agulo.printer.Printer;
import ar.edu.itba.pod.agulo.printer.PrinterDispatcher;

public class SimpleAsyncPrinterDispatcher implements PrinterDispatcher {
	
	private Printer printer = new Printer();
	private Queue<Document> queue = new LinkedList<Document>();
	private Runnable consumeDocument = new Runnable() {
		
		@Override
		public void run() {
			printer.print(queue.poll());
		}
	};
	

	@Override
	public void printDocument(Document document) {
		queue.offer(document);
		consume();
	}

	@Override
	public void finishPrintRequests() {
		while(!queue.isEmpty())
			consume();
	}
	
	private void consume() {
		new Thread(consumeDocument).start();
	}

}
