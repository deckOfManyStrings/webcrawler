/**
 * Bobby Kingsada
 * 12/1/2017
 * WebCralwerConsumer.java
 * This program consumes links.
 */
package webCrawler;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author Bobby kingsada
 * @version 1.0
 */

public class WebCrawlerConsumer extends Thread {
	static int keywordsFound;
	public static int failedDownloads = 0;
	private volatile static boolean done = false;

	/**
	 * This is the constructor for the consumer
	 */
	public WebCrawlerConsumer() {

	}

	/**
	 * the run method runs then the thread starts
	 */
	@Override
	public void run() {
		ArrayList<String> keywords;
		String[] parts;

		// continue to pull links from the queue
		while (!done) {
			try {
				Document pageText = SharedPageQueue.getNextPage();
				Elements links = pageText.select("a[href]");
				
				for (Element link : links)
				{
					String url = link.absUrl("href");
					SharedLinkQueue.addLink(url);

					keywords = (ArrayList<String>) WebCrawlerConsoleProgram.getKeywords();
					for (int i = 0; i <= keywords.size(); i++) {
						parts = url.split(url, keywords.indexOf(keywords));
					}
				}

			} catch (InterruptedException ex) {
				WebCrawlerConsoleProgram.LOGGER.log(Level.WARNING, "bad link");
				failedDownloads++;
			}
		}

	}
	
	/**
	 * 
	 * @return failedDowloads, this returns pages if the Interrupted Exception fires
	 */
	public static int getFailedDownloads() {
		return failedDownloads;
	}
	
	/**
	 * stop the threads
	 */
	public static void stopThread() {
		done = true;
	}
}