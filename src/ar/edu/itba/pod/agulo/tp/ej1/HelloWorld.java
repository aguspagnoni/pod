package ar.edu.itba.pod.agulo.tp.ej1;

public class HelloWorld {
	
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new PrintThread(i).start();
		}
	}

}
