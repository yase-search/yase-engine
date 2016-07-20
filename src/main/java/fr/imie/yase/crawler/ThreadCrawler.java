package fr.imie.yase.crawler;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import fr.imie.yase.database.dao.KeywordsDAO;
import fr.imie.yase.database.dao.PageDAO;
import fr.imie.yase.database.dao.PageKeywordsDAO;
import fr.imie.yase.database.dao.WebSiteDAO;
import fr.imie.yase.dto.Keywords;
import fr.imie.yase.dto.PageKeywords;
import fr.imie.yase.dto.WebSite;
import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Cette classe permet d'insérer en base le contenu push par MyCrawler dans la liste listPage.
 * @author Erwan
 *
 */
public class ThreadCrawler extends Thread {

	private static final int VERYHIGH = 10;
	private static final int HIGH = 7;
	private static final int MEDIUM = 5;
	private static final int LOW = 3;
	private static final int VERYLOW = 0;

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
			} catch (IOException e2) {
				e2.printStackTrace();
			} catch (URISyntaxException e3) {
                e3.printStackTrace();
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
	 * @throws IOException Erreur lors de la récupération de la page par le parser html
	 */
	private void crawlPage(Page crawlerPage) throws InterruptedException, IOException, URISyntaxException {
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
     * @param page
     * @param website
     * @throws SQLException
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
    private void createWords(fr.imie.yase.dto.Page page, Page crawlerPage) throws SQLException, IOException, URISyntaxException {

		HashMap<String, Integer> tabWords = new HashMap<>();
		/**
		 * Parsing du html de la page depuis l'url
		 */
		Document doc = Jsoup.connect(page.getUrl()).get();
		/**
		 * Traitement des principaux tags html 4/5 confondus (suivant précos w3c)
 		 */
        // Elements de headers
        Elements headerNodes = doc.select("header, div#header, div.header");
        for(Element elem: headerNodes) {
            for(Element node: elem.children()) {
                /**
                 * Pour chaque node, traitement du texte comme précédemment et ajout d'une valeur de pertinence
                 */
                if(!node.text().isEmpty()) {
                    int note = VERYHIGH;
                    System.out.println(String.format("Word: %s, Tag: %s, value: %d",node.text(),node.tagName(),note));
                    updateWordsMap(tabWords, node.text(), note);
                }
            }
        }
		// Liens de barre(s) de navigation
		Elements navLinks = doc.select("div#nav li a,div.nav li a,nav li a");
		for(Element elem: navLinks) {
			for(Element node: elem.children()) {
				/**
				 * Pour chaque node, traitement du texte comme précédemment et ajout d'une valeur de pertinence
				 */
				if(!node.text().isEmpty()) {
					int note = HIGH;
					System.out.println(String.format("Word: %s, Tag: %s, value: %d",node.text(),node.tagName(),note));
					updateWordsMap(tabWords, node.text(), note);
				}
			}
		}
        // Liens des sidebars
        Elements sidebarLinks = doc.select("div[id^sidebar] li a, " +
                "div[class^sidebar] li a," +
                " aside li a," +
                " section[id^=sidebar] li a," +
                " section[class^=sidebar] li a");
        for(Element elem: sidebarLinks) {
            for(Element node: elem.children()) {
                /**
                 * Pour chaque node, traitement du texte comme précédemment et ajout d'une valeur de pertinence
                 */
                if(!node.text().isEmpty()) {
                    int note = HIGH;
                    System.out.println(String.format("Word: %s, Tag: %s, value: %d",node.text(),node.tagName(),note));
                    updateWordsMap(tabWords, node.text(), note);
                }
            }
        }
        // Éléments du main
        Elements mainElements = doc.select("main," +
                "section#main, section.main," +
                "div#main, div.main");
        for(Element elem: mainElements) {
            for(Element node: elem.children()) {
                /**
                 * Pour chaque node, traitement du texte comme précédemment et ajout d'une valeur de pertinence
                 */
                if(!node.text().isEmpty() || node.tagName() == "img") {
                    String word = node.text();
                    int note = VERYLOW;
                    switch(node.tagName()) {
                        case "h1":
                            note = HIGH;
                            break;
                        case "h2":
                            note = MEDIUM;
                            break;
                        case "li":
                            note = LOW;
                            break;
                        case "a":
                            note = LOW;
                            break;
                        case "img":
                            note = MEDIUM;
                            word = node.attr("alt") != ""?node.attr("alt"):FilenameUtils.getBaseName((new File((new URL(node.attr("src"))).toURI())).getPath());
                        default: break;

                    }

                    System.out.println(String.format("Word: %s, Tag: %s, value: %d",node.text(),node.tagName(),note));
                    updateWordsMap(tabWords, node.text(), note);
                }
            }
        }
        // Elements du corps
        // TODO: faire la même chose pour les autres éléments (main/footer/article/section/img etc...)
        // + changer dans certains node enfants la note suivant le tag parent(h1>h2>...>p etc.)
        // ex: Elements node = doc.select("div[id^sidebar], div[class^sidebar], aside, section[id^=sidebar], section[class^=sidebar]");

        /**
         * sur la map finalement obtenue, effectuer le traitement précédent (légèrement modifié car HashMap<> != String[]
         */
        KeywordsDAO daoKeywords = new KeywordsDAO();
        Iterator it = tabWords.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String word = (String) entry.getKey();
            Integer note = (Integer) entry.getValue();
            // Insertion ou lecture en base
            Keywords words = new Keywords(word, true, null);
            Keywords keywordsEntity = new Keywords(word, true, null);
            List<Keywords> results = daoKeywords.find(words);
            if (results.size() > 0) {
                keywordsEntity = results.get(0);
                System.out.println("Mot " + keywordsEntity.getValue() + " trouvé");
            } else {
                keywordsEntity = daoKeywords.create(words);
                System.out.println("Création du mot " + keywordsEntity.getValue());
            }

            // Ajout de relation entre le site et le mot
            PageKeywords pageKeywords = new PageKeywords(page.getId(), keywordsEntity.getId(), note);
            PageKeywordsDAO pageKeywordsDao = new PageKeywordsDAO();
            List<PageKeywords> listPageKeywordsEntity = pageKeywordsDao.find(pageKeywords);
            // Si la relation entre une page et un mot n'existe pas, on la créé.
            if (listPageKeywordsEntity.size() == 0) {
                pageKeywordsDao.create(pageKeywords);
                System.out.println(String.format("Association du mot %s à la page %s avec une pertinence de %d", keywordsEntity.getValue(), crawlerPage.getWebURL().getURL(), pageKeywords.getStrengh().intValue()));
            } else {
                System.out.println("Le mot " + keywordsEntity.getValue() + " est déjà associé à la page " + crawlerPage.getWebURL().getURL());
            }
        }
        System.out.println("Fin du crawling de la page " + crawlerPage.getWebURL().getURL());
	}

    /**
     * Mets à jour la map de mots donnée à partir d'un chaine de mots et d'une valeur donnés
     * @param map le tableau de mots à mettre à jour
     * @param chain la chaine de mots
     * @param note la note (pertinence) à rajouter aux mots
     */
    public void updateWordsMap(HashMap<String, Integer> map, String chain, int note) {
        String[] words = chain.replaceAll(REGEX_SPECIAL_CHAR, " ").split(" ");
        for(int i =0; i < words.length && i < 20; i++) {
            String currentWord = words[i].toLowerCase();
            // TODO: gérer ici le rejet des mots non significatifs (ex: liaisons...):
            // http://www.mycampus-live.com/telechargements/4-B2-argumentation-Tableaux-mots-de-liaison-et-modalisateurs.pdf
            if (map.containsKey(currentWord)) {
                int previousNote = map.get(currentWord);
                map.replace(currentWord,previousNote+note);
            } else {
                map.put(currentWord, note);
            }
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
