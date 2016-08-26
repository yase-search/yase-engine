package fr.imie.yase.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class MainCrawler {
	
	public MainCrawler(String[] domains) throws Exception{
		String crawlStorageFolder = System.getProperty("os.name").toLowerCase().contains("windows") ?
			"/Users/" + System.getProperty("user.name") + "/AppData/Roaming/crawler/tmp/root" :
			"/tmp/crawler/root";

		int numberOfCrawlers = 2;

		System.out.println("Starting " + Integer.toString(numberOfCrawlers) + " crawlers");
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */

		for(String domain: domains){
			controller.addSeed(domain);
		}

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(PageCrawler.class, numberOfCrawlers);
	}
}
