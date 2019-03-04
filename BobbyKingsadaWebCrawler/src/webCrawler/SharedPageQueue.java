/**
 * Bobby Kingsada
 * 12/1/2017
 * WebCralwerConsoleProgram.java
 * This program holds the pages.
 */

package webCrawler;

import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * 
 * @author Bobby Kingsada
 * @version 1.0
 */
public class SharedPageQueue {
	private static final int PAGE_LIST_MAX_SIZE = 50000;
	static int totalPages;
	static Queue<Document> pageList = new LinkedList<>();
	
	
	public SharedPageQueue(String url) {
	}
	
	

	/**
	 * add pages to the queue
	 * 
	 * @param pageText,
	 *            will add this parameter to the queue
	 * @throws InterruptedException
	 */
	public static void addPage(Document page) throws InterruptedException {
		// make sure this is thread safe
		synchronized (pageList) {
			// what if my queue is full?
			while (pageList.size() == PAGE_LIST_MAX_SIZE) {
				// wait on the queue
				pageList.wait();

			}
			
			pageList.add(page);
			totalPages++;

			// wake up threads waiting on an empty queue
			pageList.notifyAll();
		}
	}

	/**
	 * @return word, returns the next page
	 */
	public static Document getNextPage() throws InterruptedException {
		synchronized (pageList) {
			while (pageList.isEmpty()) {
				pageList.wait();
			}

			// return the next word in the queue
			Document word = pageList.poll();

			// wake up threads waiting on a full queue
			pageList.notifyAll();

			return word;
		}
	}

	/**
	 * 
	 * @return totalPages, return the total amount of pages added to the queue
	 */
	public int getPagesDownloaded() {
		return totalPages;
	}
}
