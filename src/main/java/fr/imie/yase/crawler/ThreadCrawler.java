package fr.imie.yase.crawler;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import fr.imie.yase.database.dao.KeywordsDAO;
import fr.imie.yase.database.dao.PageDAO;
import fr.imie.yase.database.dao.PageKeywordsDAO;
import fr.imie.yase.database.dao.WebSiteDAO;
import fr.imie.yase.dto.Keywords;
import fr.imie.yase.dto.PageKeywords;
import fr.imie.yase.dto.WebSite;

/**
 * Cette classe permet d'insérer en base le contenu push par MyCrawler dans la liste listPage.
 * @author Erwan
 *
 */
public class ThreadCrawler extends Thread {
	
	private final static String REGEX_SPECIAL_CHAR = "[^À-Ÿà-ÿ\\w-]";
	
	private List<Page> todoListPage;
	
	private List<Page> completeListPage;
	
	private int nbrSleep = 0;
	
	private boolean start = false;
	
	/**
	 * Permet de déclencher le Thread
	 */
	public void run() {
		start = true;
		System.out.println("Le thread du Crawler demarre.");
		try {
			launchCrawl();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread.currentThread().interrupt();
		start = false;
		System.out.println("Le thread du Crawler s'arrète.");
	}
	
	/**
	 * Permet vérifier si un page est disponible pour l'insertion en base. Si oui on appelle CrawlPage()
	 * @throws InterruptedException
	 */
	private void launchCrawl() throws InterruptedException {
		Page page = getPage();
		if (page != null) {
			nbrSleep = 0;
			try {
				crawlPage(page);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Thread.sleep(1000);
			nbrSleep++;
			// Si le Thread dort depuis 30 sec, on l'arrete.
			if (nbrSleep == 30) {
				return;
			}
		}
		launchCrawl();
	}
	
	/**
	 * Permet de lancer le crawl de la page passé en paramètre
	 * @param crawlerPage Page
	 * @throws InterruptedException 
	 */
	private void crawlPage(Page crawlerPage) throws InterruptedException {
		WebSite website;
		try {
			website = createWebSite(crawlerPage);
			fr.imie.yase.dto.Page page = createPage(crawlerPage, website);
			// On ajoute on base la liste des mots
			createWords(page, crawlerPage);
			
			HtmlParseData htmlParseData = (HtmlParseData) crawlerPage.getParseData();
			System.out.println("Ajout du site à la pool : " + htmlParseData.getTitle());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
     * Permet d'ajouter une page crawler en base de données.
     * @param htmlParseData HtmlParseData
     * @throws SQLException 
     * @throws Exception 
     */
    public fr.imie.yase.dto.Page createPage(Page page, WebSite website) throws SQLException {
    	 // Get data page
         HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	     String html = htmlParseData.getHtml();
	     // Create entity page
    	 fr.imie.yase.dto.Page entity = new fr.imie.yase.dto.Page();
    	 entity.setTitle(htmlParseData.getTitle());
    	 if (htmlParseData.getTitle().length() > 100) {
    		 entity.setTitle(htmlParseData.getTitle().substring(0, 97) + "...");
    	 }
    	 entity.setContent(html);
    	 entity.setCrawl_date(getDate());
    	 entity.setDescription(getDescription(htmlParseData));
    	 entity.setLoad_time(1);
    	 entity.setLocale(page.getLanguage());
    	 entity.setSize(html.length());
    	 entity.setUrl(page.getWebURL().getPath());
    	 entity.setWebsite(website);
    	 
    	 // Insert page
    	 PageDAO daoPage = new PageDAO();
    	 // On vérifie si la page existe déjà en base
    	 fr.imie.yase.dto.Page result = daoPage.findByURL(entity);
    	 if (result.getId() == null) {
    		 result = daoPage.create(entity);
    	 }
    	 return entity;
    }
    
    /**
     * Permet de générer une entity WebSite
     * @param page Page
     * @return website WebSite
     * @throws SQLException 
     */
    public WebSite createWebSite(Page page) throws SQLException {
    	String domain = page.getWebURL().getDomain();
    	String url = page.getWebURL().getURL();
    	String protocol = url.substring(0, url.indexOf("://"));
    	WebSite website = new WebSite(null, domain, protocol);
    	WebSiteDAO daoWebSite = new WebSiteDAO();
   	 	List<WebSite> results = daoWebSite.find(website);
   	 	WebSite websiteEntity = new WebSite(null, domain, protocol);
   	 	if (results.size() > 0) {
   	 		websiteEntity = results.get(0);
   	 	} else {
   	 		websiteEntity = daoWebSite.create(website);
   	 	}
    	return websiteEntity;
    }
    
    /**
     * Permet d'insérer un mot en base ou simplement de le récupérer.
     * @param page
     * @param crawlerPage
     * @throws SQLException
     */
    private void createWords(fr.imie.yase.dto.Page page, Page crawlerPage) throws SQLException {
    	// Split content page
    	HtmlParseData htmlParseData = (HtmlParseData) crawlerPage.getParseData();
    	String text = htmlParseData.getText();
    	if (!text.isEmpty()) {
    		String[] tabWords = text.replaceAll(REGEX_SPECIAL_CHAR, " ").split(" ");
        	
        	// Insert words
        	KeywordsDAO daoKeywords = new KeywordsDAO();
        	for (int i=0;i<tabWords.length;i++) {
        		if (!tabWords[i].isEmpty()) {
        			// Insertion ou lecture en base
            		Keywords words = new Keywords(tabWords[i], true, null);
            		Keywords keywordsEntity = new Keywords(tabWords[i], true, null);
            		List<Keywords> results = daoKeywords.find(words);
            		if (results.size() > 0) {
            			keywordsEntity = results.get(0);
            			System.out.println("Mot " + keywordsEntity.getValue() + " trouvé");
               	 	} else {
               	 		keywordsEntity = daoKeywords.create(words);
               	 		System.out.println("Création du mot " + keywordsEntity.getValue());
               	 	}
            		
            		// Ajout de relation entre le site et le mot
            		PageKeywords pageKeywords = new PageKeywords(page.getId(), keywordsEntity.getId(), 1);
            		PageKeywordsDAO pageKeywordsDao = new PageKeywordsDAO();
            		List<PageKeywords> listPageKeywordsEntity = pageKeywordsDao.find(pageKeywords);
            		// Si la relation entre une page et un mot n'existe pas, on la crèe.
            		if (listPageKeywordsEntity.size() == 0) {
            			pageKeywordsDao.create(pageKeywords);
            			System.out.println("Association du mot " + keywordsEntity.getValue() + " à la page " + crawlerPage.getWebURL().getURL());
            		} else {
            			System.out.println("Le mot " + keywordsEntity.getValue() + " est déjà associé à la page " + crawlerPage.getWebURL().getURL());
            		}
        		}
        	}
        	System.out.println("Fin du crawling de la page " + crawlerPage.getWebURL().getURL());
    	}
	}
    
    /**
     * Permet de récupérer la date du jour au format yyyy-MM-dd'T'HH:mmZ
     * @return date
     */
    public String getDate() {
    	TimeZone tz = TimeZone.getTimeZone("UTC");
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
    	df.setTimeZone(tz);
    	return df.format(new Date());
    }
    
    /**
     * Permet de récupérer la description d'un site
     * @param htmlParseData
     * @return description String
     */
    public String getDescription(HtmlParseData htmlParseData) {
    	String description = "";
    	String data = htmlParseData.getMetaTags().get("description");
    	if (data != null) {
    		description = data;
    		if (data.length() > 255) {
    			description = data.substring(0, 250) + "...";
    		}
    	}
    	return description;
    }
    
    /**
     * Permet d'ajouter la page à la liste
     * @param page
     */
    public void addPage(Page page) {
    	if (todoListPage == null) {
    		todoListPage = new ArrayList<Page>();
    	}
    	todoListPage.add(page);
    }
    
    /**
     * Permet de récupérer la prochaine page à insérer en bdd.
     * @return
     */
    private Page getPage() {
    	Page page = null;
    	if (todoListPage != null && todoListPage.size() > 0) {
    		page = todoListPage.remove(0);
    		if (completeListPage == null) {
    			completeListPage = new ArrayList<Page>();
    		}
    		completeListPage.add(page);
    	}
    	return page;
    }

	/**
	 * @return the start
	 */
	public boolean isStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(boolean start) {
		this.start = start;
	}
}
