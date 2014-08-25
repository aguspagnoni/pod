package ar.edu.itba.pod.agulo.implementations;

import java.util.LinkedList;
import java.util.Queue;

import ar.edu.itba.pod.agulo.printer.Document;
import ar.edu.itba.pod.agulo.printer.Printer;
import ar.edu.itba.pod.agulo.printer.PrinterDispatcher;

public class LimitedAsyncPrinterDispatcher implements PrinterDispatcher {
	
	private Printer printer = new Printer();
	private Queue<Document> queue = new LinkedList<Document>();
	private int limit;
	private Runnable consumeDocument = new Runnable() {
		
		@Override
		public void run() {
			printer.print(queue.poll());
		}
	};
	
	public LimitedAsyncPrinterDispatcher(int limit) {
		this.limit = limit;
		
	}

	@Override
	public void printDocument(Document document) {
		if (queue.size() < limit) {
			queue.offer(document);
			consume();
		} else {
			finishPrintRequests();
		}
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
