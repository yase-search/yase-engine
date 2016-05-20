package fr.imie.yase.business;



import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.util.StringUtil;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import fr.imie.yase.database.dao.DAO;
import fr.imie.yase.database.dao.KeywordsDAO;
import fr.imie.yase.database.dao.PageDAO;
import fr.imie.yase.database.dao.PageKeywordsDAO;
import fr.imie.yase.database.dao.WebSiteDAO;
import fr.imie.yase.dto.Keywords;
import fr.imie.yase.dto.PageKeywords;
import fr.imie.yase.dto.WebSite;

public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");
    
    private final static String REGEX_SPECIAL_CHAR = "[^À-Ÿà-ÿ\\w-]";

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && href.startsWith("http://www.ics.uci.edu/");
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page crawlerPage) {
         String url = crawlerPage.getWebURL().getURL();
         System.out.println("URL: " + url);
         if (crawlerPage.getParseData() instanceof HtmlParseData) {
        	 WebSite website;
			try {
				website = createWebSite(crawlerPage);
				fr.imie.yase.dto.Page page = createPage(crawlerPage, website);
				createWords(page, crawlerPage);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
    	 entity.setUrl(page.getWebURL().getURL());
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
    	Map<String, Object> mapWebSite = new HashMap<String, Object>();
    	List<WebSite> listWebSite = new ArrayList<WebSite>();
    	listWebSite.add(website);
        mapWebSite.put("website", listWebSite);
   	 	List<WebSite> results = daoWebSite.find(mapWebSite);
   	 	WebSite websiteEntity = new WebSite(null, domain, protocol);
   	 	if (results.size() > 0) {
   	 		websiteEntity = results.get(0);
   	 	} else {
   	 		websiteEntity = daoWebSite.create(website);
   	 	}
    	return websiteEntity;
    }
    
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
            		Map<String, Object> mapKeywords = new HashMap<String, Object>();
            		List<String> listKeywords = new ArrayList<String>();
            		listKeywords.add(words.getValue());
            		mapKeywords.put("keywords", listKeywords);
            		List<Keywords> results = daoKeywords.find(mapKeywords);
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
            		Map<String, Object> mapPageKeywords = new HashMap<String, Object>();
            		List<PageKeywords> listPageKeywords = new ArrayList<PageKeywords>();
            		listPageKeywords.add(pageKeywords);
            		mapPageKeywords.put("pageKeywords", listPageKeywords);
            		List<PageKeywords> listPageKeywordsEntity = pageKeywordsDao.find(mapPageKeywords);
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
}
