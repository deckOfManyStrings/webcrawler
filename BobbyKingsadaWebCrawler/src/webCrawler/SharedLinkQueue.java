/**
 * Bobby Kingsada
 * 12/1/2017
 * WebCralwerConsoleProgram.java
 * This program holds the link queue.
 */

package webCrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @author kings
 * @version 1.0
 */
public class SharedLinkQueue {
	private static final int LINK_LIST_MAX_SIZE = 50000;
	static Queue<String> linkList = new LinkedList<>();
	static HashSet<String> hashSet = new HashSet<>();

	/**
	 * Addlink() will allow you to add the link to the link queue
	 * 
	 * @param link,
	 *            takes in the link to add to the link queue
	 * @throws InterruptedException
	 */
	public static void addLink(String link) throws InterruptedException {
		// make sure this is thread safe
		synchronized (linkList) {
			// what if my queue is full?
			while (linkList.size() == LINK_LIST_MAX_SIZE) {
				// wait on the queue
				linkList.wait();
			}
			
			linkList.add(link);
			hashSet.add(link);

			// wake up threads waiting on an empty queue
			linkList.notifyAll();
		}
	}

	/**
	 * allows you to get the next link to turn it into html
	 * 
	 * @return word, word is the next page in the queue
	 * @throws InterruptedException
	 */
	public static String getNextLink() throws InterruptedException {
		synchronized (linkList) {
			while (linkList.isEmpty()) {
				linkList.wait();
			}

			// return the next word in the queue
			String word = linkList.poll();

			// wake up threads waiting on a full queue
			linkList.notifyAll();

			return word;
		}
	}

	/**
	 * this is a getter for hashSet variable
	 * 
	 * @return hashSet, used to test if the link has been added to the queue yet
	 */
	public HashSet<String> getLinksFound() {
		return hashSet;
	}

}
