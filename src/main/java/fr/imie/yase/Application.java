package fr.imie.yase;

import fr.imie.yase.crawler.Crawler;
import fr.imie.yase.business.Search;
import fr.imie.yase.dto.Keywords;

/**
 * Permet de lancer l'application.
 * @author Erwan - Ludovic - Arnaud
 *
 */
public class Application {
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Start application YASE.");
//		
		new Crawler();
		Search search = new Search("le papa et la maman d'erwan");
		for (Keywords kw : search.getKeywords()) {
			System.out.println(kw.getValue());
		}
		
	}
}