package fr.imie.yase.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Crawler {
	
	public Crawler() throws Exception{
		String crawlStorageFolder = System.getProperty("os.name").toLowerCase().indexOf("windows") > -1 ?
			"/Users/" + System.getProperty("user.name") + "/AppData/Roaming/crawler/tmp/root" :
			"/tmp/crawler/root";

	    int numberOfCrawlers = 7;

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

	    controller.addSeed("http://stackoverflow.com/");
//	    controller.addSeed("https://openclassrooms.com/");
//	    controller.addSeed("https://twitter.com/");
//	    controller.addSeed("https://www.e-monsite.com/");

	    /*
	     * Start the crawl. This is a blocking operation, meaning that your code
	     * will reach the line after this only when crawling is finished.
	     */
	    controller.start(MyCrawler.class, numberOfCrawlers);
	}
	
}
