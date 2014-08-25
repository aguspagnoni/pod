package ar.edu.itba.pod.agulo.implementations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ar.edu.itba.pod.agulo.printer.Document;
import ar.edu.itba.pod.agulo.printer.Printer;
import ar.edu.itba.pod.agulo.printer.PrinterDispatcher;
import ar.edu.itba.pod.agulo.util.TimeOutException;

public class EnhancedPoolPrinterDispatcher implements PrinterDispatcher {
	
	private List<Printer> free = new ArrayList<Printer>();
	private Queue<Document> queue = new LinkedList<Document>();
	private int poolSize;
	private int limit = 5;
	private long timeout = 10;
	private Runnable consumeDocument = new Runnable() {
		
		@Override
		public void run() {
			if (!free.isEmpty()) {
				Printer printer = free.remove(0);
				printer.print(queue.poll());
				free.add(printer);
			}
		}
	};
	
	
	
	public EnhancedPoolPrinterDispatcher(int poolSize) {
		this.poolSize = poolSize;
		for (int i = 0; i < poolSize; i++)
			free.add(new Printer());
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
		new Thread(consumeDocument).start();
	}
	
	public void addPrinter(Printer printer) {
		free.add(printer);
	}
	
	public void removePrinter() {
		if (poolSize == 0)
			return;
		while(free.isEmpty())
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		try {
			free.remove(0);
			poolSize--;
		} catch (IndexOutOfBoundsException e) {
			removePrinter();
		}
	}

}
