package ar.edu.itba.pod.agulo.tp.ej3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SystemPC {
	
	private Queue<String> strings = new LinkedList<String>();
	private Runnable producer = new Runnable() {
		@Override
		public void run() {
			char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < 20; i++) {
			    char c = chars[random.nextInt(chars.length)];
			    sb.append(c);
			}
			strings.offer(sb.toString());
		}
	};
	private Runnable consumer = new Runnable() {
		@Override
		public void run() {
			String s = strings.poll();
			if (s == null)
				return;
			int count = 0;
			s = s.toLowerCase();
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
					count++;
			}
			System.out.println(count);
		}
	};
	
	
}
