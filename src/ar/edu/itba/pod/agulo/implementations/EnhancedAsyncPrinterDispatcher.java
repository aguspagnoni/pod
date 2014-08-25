package ar.edu.itba.pod.agulo.implementations;

import java.util.LinkedList;
import java.util.Queue;

import ar.edu.itba.pod.agulo.printer.Document;
import ar.edu.itba.pod.agulo.printer.Printer;
import ar.edu.itba.pod.agulo.printer.PrinterDispatcher;
import ar.edu.itba.pod.agulo.util.TimeOutException;

public class EnhancedAsyncPrinterDispatcher implements PrinterDispatcher {
	
	private Printer printer = new Printer();
	private Queue<Document> queue = new LinkedList<Document>();
	private long timeout;
	private int limit = 5;
	private Runnable consumeDocument = new Runnable() {
		
		@Override
		public void run() {
			printer.print(queue.poll());
		}
	};
	
	public EnhancedAsyncPrinterDispatcher(long timeout) {
		this.timeout = timeout;
	}

	@Override
	public void printDocument(Document document) {
		long ini = System.currentTimeMillis();
		while( System.currentTimeMillis() - ini < timeout) {
			if (queue.size() < limit) {
				queue.offer(document);
				consume();
				return;
			}
		}
		throw new TimeOutException();
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
