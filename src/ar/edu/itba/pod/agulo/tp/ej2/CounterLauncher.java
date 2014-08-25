package ar.edu.itba.pod.agulo.tp.ej2;

import java.util.concurrent.atomic.AtomicInteger;

import ar.edu.itba.pod.agulo.util.Logger;

public class CounterLauncher {
	
	public static AtomicInteger counter = new AtomicInteger(0);
	
	public static Runnable counterRunnable = new Runnable() {
		
		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				counter.incrementAndGet();
			}
		}
	};
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 4; i++) {
			new Thread(counterRunnable).start();
		}
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Logger.log("Contador: " + counter);
	}

}
