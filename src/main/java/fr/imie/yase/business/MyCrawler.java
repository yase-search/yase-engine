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

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import fr.imie.yase.database.dao.DAO;
import fr.imie.yase.database.dao.PageDAO;
import fr.imie.yase.database.dao.WebSiteDAO;
import fr.imie.yase.dto.WebSite;

public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp3|zip|gz))$");

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
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("URL: " + url);

         if (page.getParseData() instanceof HtmlParseData) {
        	 WebSite website;
			try {
				website = createWebSite(page);
				createPage(page, website);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
    }
     
//    public void insertPage()
     
    /**
     * Permet d'ajouter une page crawler en base de données.
     * @param htmlParseData HtmlParseData
     * @throws SQLException 
     * @throws Exception 
     */
    public fr.imie.yase.dto.Page createPage(Page page, WebSite website) throws SQLException {
         HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	     String html = htmlParseData.getHtml();
    	 fr.imie.yase.dto.Page entity = new fr.imie.yase.dto.Page();
    	 entity.setTitle(htmlParseData.getTitle());
    	 entity.setContent(html);
    	 entity.setCrawl_date(getDate());
    	 entity.setDescription(getDescription(htmlParseData));
    	 entity.setLoad_time(1);
    	 entity.setLocale(page.getLanguage());
    	 entity.setSize(html.length());
    	 entity.setUrl(page.getWebURL().getURL());
    	 entity.setWebsite(website);
    	 
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
    	WebSite website = new WebSite(null, domain);
    	WebSiteDAO daoWebSite = new WebSiteDAO();
    	Map<String, Object> mapWebSite = new HashMap<String, Object>();
    	List<WebSite> listWebSite = new ArrayList<WebSite>();
    	listWebSite.add(website);
        mapWebSite.put("website", listWebSite);
   	 	List<WebSite> results = daoWebSite.find(mapWebSite);
   	 	WebSite websiteEntity = new WebSite(null, domain);
   	 	if (results.size() > 0) {
   	 		websiteEntity = results.get(0);
   	 	} else {
   	 		websiteEntity = daoWebSite.create(website);
   	 	}
    	return websiteEntity;
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
