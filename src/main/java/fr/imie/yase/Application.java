package fr.imie.yase;

import fr.imie.yase.crawler.CrawlerDBManager;
import fr.imie.yase.crawler.MainCrawler;
import fr.imie.yase.database.dao.KeywordsDAO;
import fr.imie.yase.dto.Keywords;

import java.util.List;

/**
 * Permet de lancer l'application.
 * @author Erwan - Ludovic - Arnaud
 *
 */
public class Application extends Thread{
	
	public void run(){
		// Instanciate before crawler starts
        new CrawlerDBManager();
        KeywordsDAO.populateWords();

        String[] domains = new String[0];
        String domainsStr = System.getenv("CRAWL_DOMAINS");

        if(domainsStr != null){
            domains = domainsStr.split(",");
        }

        if(domains.length > 0) {
            try {
                System.out.println("Start application YASE in thread.");
                new MainCrawler(domains);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            System.out.println("Got no domains to crawl. Use CRAWL_DOMAINS separated with a comma to add some");
        }
	}
}