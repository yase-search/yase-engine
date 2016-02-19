package fr.imie.yase;

import fr.imie.yase.business.Crawler;

/**
 * Permet de lancer l'application.
 * @author Erwan - Ludovic - Arnaud
 *
 */
public class Application {
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Start application YASE.");
		
		new Crawler();
		
	}
}