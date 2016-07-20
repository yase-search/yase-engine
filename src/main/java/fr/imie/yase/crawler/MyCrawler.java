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
        	 ThreadCrawler threadCrawler = InstanceThreadCrawler.getInstance();
        	 threadCrawler.addPage(crawlerPage);
        	 if (!threadCrawler.isStart()) {
        		 System.out.println("Lancement du thread");
        		 threadCrawler.start();
        	 }
         }
    }
}
