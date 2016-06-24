package fr.imie.yase.crawler;

public class InstanceThreadCrawler {

	private static ThreadCrawler _instance;
	
	/**
	 * Singleton
	 * 
	 * @return ThreadCrawler
	 */
	public static ThreadCrawler getInstance() {
		if (_instance == null) {
			_instance = new ThreadCrawler();
		}
		return _instance;
	}
	
}
