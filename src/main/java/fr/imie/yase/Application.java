package fr.imie.yase;

import fr.imie.yase.crawler.CrawlerDBManager;
import fr.imie.yase.crawler.MainCrawler;
import fr.imie.yase.database.dao.KeywordsDAO;
import fr.imie.yase.dto.Keywords;

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
        try {
            System.out.println("Start application YASE in thread.");
            new MainCrawler();
        } catch(Exception e){
            e.printStackTrace();
        }
	}
}