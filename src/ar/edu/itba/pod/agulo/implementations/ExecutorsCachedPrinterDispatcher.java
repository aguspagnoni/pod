package ar.edu.itba.pod.agulo.implementations;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ar.edu.itba.pod.agulo.printer.Document;
import ar.edu.itba.pod.agulo.printer.Printer;
import ar.edu.itba.pod.agulo.printer.PrinterDispatcher;
import ar.edu.itba.pod.agulo.util.TimeOutException;

public class ExecutorsCachedPrinterDispatcher implements PrinterDispatcher {

	private Queue<Document> queue = new LinkedList<Document>();
	private int limit = 5;
	private long timeout = 10;
	private ExecutorService executorService;
	private Runnable consumeDocument = new Runnable() {
		
		@Override
		public void run() {
			new Printer().print(queue.poll());
		}
	};
	
	
	
	public ExecutorsCachedPrinterDispatcher(int poolSize) {
		executorService = Executors.newCachedThreadPool();
	}
	

	@Override
	public void printDocument(Document document) throws TimeOutException {
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
		while(!queue.isEmpty()) {
			consume();
		}
	}
	
	private void consume() {
		executorService.execute(consumeDocument);
	}
}