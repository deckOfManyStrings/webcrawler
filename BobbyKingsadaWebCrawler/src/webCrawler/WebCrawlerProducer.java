/**
 * Bobby Kingsada
 * 12/1/2017
 * WebCralwerProducer.java
 * This program gets accepts URLS and downloads html.
 */
package webCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import org.jsoup.Jsoup;

/**
 * 
 * @author Bobby Kingsada
 * @version 1.0
 */
public class WebCrawlerProducer extends Thread {
	private volatile static boolean done = false;
	
	/**
	 * run method will start when i call the thread
	 */
	@Override
	public void run() {
		while (!done) {
			try {
				String site = SharedLinkQueue.getNextLink();
				
				org.jsoup.nodes.Document page = Jsoup.connect(site).get();
				SharedPageQueue.addPage(page);

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		System.out.println("Thread Stopped");
	}
	/**
	 * stop the threads
	 */
	public static void stopThread() {
		done = true;
	}
}
