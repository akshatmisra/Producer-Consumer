import java.util.LinkedList;
import java.util.Queue;
//import org.apache.log4j.Logger;

public class Solution {

	public static void main(String[] args) {

		Integer q = 0;

		Thread j = new JV(q);
		Thread r = new reb(q);
		j.start();
		r.start();

	}

	public synchronized static void startClient(String string) {
		System.out.println(string);
		
	}
}

class JV extends Thread {
	private  Integer sharedQueue;
	private Object Lock = new Object();

	public JV(Integer sQueue) {
		this.sharedQueue = sQueue;
	}

	public void run() {
		for (int i = 0; i < 500; i++) {
			synchronized (Lock) {
				Solution.startClient("Juang");
				if (i >=10 && i<12) {

					System.out.println("Stopping REB : " + i);
					//sharedQueue.add(i);
					sharedQueue = 1;
				}
				else
				{
					if(sharedQueue >=1)
					{
						sharedQueue =0;
					}
					Lock.notify();
				}
			}
		}

	}
}

class reb extends Thread {
	private Integer sharedQueue;
	public static int counter = 0;
	private Object Lock = new Object();

	public reb(Integer sQ) {
		this.sharedQueue = sQ;
	}

	public void run() {

		for (int j = 0; j < 300; j++) {
			synchronized (Lock) {
				Solution.startClient("REB");
				if(sharedQueue > 0)
				{
					try {
						System.out.println("REB shared Queue : "+ sharedQueue);
						Lock.wait();
						System.out.println("Notifyed by JV...");
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		}

	}
}
