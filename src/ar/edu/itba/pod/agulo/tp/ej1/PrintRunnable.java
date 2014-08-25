package ar.edu.itba.pod.agulo.tp.ej1;

import ar.edu.itba.pod.agulo.util.Logger;

public class PrintRunnable implements Runnable {
	
	private int id;
	
	PrintRunnable(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		try {
			Thread.sleep((long)(Math.random() * 4000));
		} catch (InterruptedException e) {
		}
		Logger.log("Thread: " + id);
	}
	
}
