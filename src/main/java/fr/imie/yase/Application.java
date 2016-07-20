package fr.imie.yase;

import fr.imie.yase.crawler.CrawlerDBManager;
import fr.imie.yase.crawler.MainCrawler;

/**
 * Permet de lancer l'application.
 * @author Erwan - Ludovic - Arnaud
 *
 */
public class Application {
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Start application YASE.");

		// Instanciate before crawler starts
        new CrawlerDBManager();
		new MainCrawler();
	}
}