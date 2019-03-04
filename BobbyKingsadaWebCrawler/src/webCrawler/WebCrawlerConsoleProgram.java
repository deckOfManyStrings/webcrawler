/**
 * Bobby Kingsada
 * 12/1/2017
 * WebCralwerConsoleProgram.java
 * This program is the console for the webcrawler.
 */

package webCrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.nodes.Element;

/**
 * 
 * @author Bobby Kingsada
 * @version 1.0
 */

public class WebCrawlerConsoleProgram {
	public static final Logger LOGGER = Logger.getLogger(WebCrawlerConsoleProgram.class.toString());
	static Scanner scanner;
	static ArrayList<String> keywords = new ArrayList<String>();
	static ArrayList<WebCrawlerProducer> allProducers = new ArrayList<WebCrawlerProducer>();
	static ArrayList<WebCrawlerConsumer> allConsumers = new ArrayList<WebCrawlerConsumer>();

	static ArrayList<WebCrawlerProducer> allProducersThreads = new ArrayList<WebCrawlerProducer>();
	static ArrayList<WebCrawlerConsumer> allConsumersThreads = new ArrayList<WebCrawlerConsumer>();

	static HashMap<String, Integer> keywordList = new HashMap<>();
	static int totalProducers = 0;
	static int totalConsumers = 0;
	static int keywordCount = 0;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		foo();
		menu();
	}

	/**
	 * foo is the logger
	 */
	public static void foo() {
		try {
			LOGGER.addHandler(new FileHandler("webcrawler.log"));
			LOGGER.setUseParentHandlers(false);
		} catch (Exception e) {
			System.out.println("Error setting up log file.");
		}
	}

	private static void menu() {
		scanner = new Scanner(System.in);
		int menu;
		System.out.println("1. Add seed url \n" + "2. Add consumer \n" + "3. add producer \n"
				+ "4. add keyword search \n" + "5. Print stats \n" + "6. Exit \n" + "**************************");
		menu = scanner.nextInt();
		scanner.nextLine();

		switch (menu) {
		case 1:
			addSeed();
		case 2:
			addConsumer();
		case 3:
			addProducer();
		case 4:
			addKeyword();
		case 5:
			printStats();
		case 6:
			end();
		}

	}

	private static void addSeed() {
		String url;

		System.out.println("Please enter in a website URL:");
		url = scanner.nextLine();
		try {
			SharedLinkQueue.addLink(url);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.log(Level.FINE, "Seed added");
		menu();
		// add to link queue

	}

	private static void addConsumer() {
		WebCrawlerConsumer consumer = new WebCrawlerConsumer();
		allConsumers.add(consumer);
		totalConsumers++;
		LOGGER.log(Level.FINE, "Consumer added");
		consumer.start();
		consumer.setName("Consumer # " + totalConsumers);
		menu();
	}

	private static void addProducer() {
		WebCrawlerProducer producer = new WebCrawlerProducer();
		allProducers.add(producer);
		totalProducers++;
		LOGGER.log(Level.FINE, "Producer added");
		producer.start();
		menu();
	}

	private static void addKeyword() {
		String currentKeyword;
		keywordCount++;
		System.out.println("Enter in a keyword to look for: ");
		currentKeyword = scanner.nextLine();
		keywordList.put(currentKeyword, keywordCount);
		System.out.println("Keyword Added \n");

		menu();

	}

	private static void printStats() {
		int keywordsFound = WebCrawlerConsumer.keywordsFound;
		int linksFound = SharedLinkQueue.hashSet.size();
		int pagesFound = SharedPageQueue.totalPages;
		int failedDownloads = WebCrawlerConsumer.getFailedDownloads();

		System.out.println("Keywords found: ");
		printKeywords();

		System.out.println(
				"\nLinks found: " + linksFound 
				+	"\nPages found: " + pagesFound 
				+ 	"\nFailed downloads: " + failedDownloads 
				+ 	"\nProducers: " + totalProducers
				+ "\nConsumers: " + totalConsumers);
		menu();
	}

	private static void printKeywords() {
		for(String key: keywordList.keySet()) {
			System.out.println(key +" found: "+ keywordList.get(key));
		}
	}

	/**
	 * end the threads
	 */
	private static void end() {
		WebCrawlerProducer.stopThread();
		WebCrawlerConsumer.stopThread();
	}

	/**
	 * 
	 * @return keywords returns the keywords to search for
	 */
	public static ArrayList<String> getKeywords() {
		return keywords;
	}
}