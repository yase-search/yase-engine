package fr.imie.yase.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.imie.yase.database.dao.KeywordsDAO;
import fr.imie.yase.database.dao.PageDAO;
import fr.imie.yase.dto.Keywords;
import fr.imie.yase.dto.Page;

public class Search {
    
    private static final Integer INTERVAL = 10;
    
    private static final Integer ZERO = 0;

	private String input;

	private List<Keywords> keywords = new ArrayList<Keywords>();

	private List<Page> pages = new ArrayList<Page>();

	private PageDAO pageDAO = new PageDAO();

	private KeywordsDAO keywordsDAO = new KeywordsDAO();
	
	private Integer numberPages;
	
	private String interval;

	/**
	 * Construct
	 * 
	 * @param input
	 *            String
	 * @throws SQLException
	 */
	public Search(String input, Integer numberPage) throws SQLException {
		this.setInput(input);
		// Pour couper la chaine d'entrée avec les espaces ou les '
		// TODO: trouver un moyen de sortir le critère de délimitation (en
		// attribut de classe ou plus globalement)
		String delimiter = "[ ']";
		String[] splittedInput = this.getInput().toLowerCase().split(delimiter);

		// On créé une liste de mots clés
		ArrayList<String> keywords = new ArrayList<String>();
		for (String string : splittedInput) {
			// On ajoute les mots de longueur significative (pour éliminer
			// le/la/de/du etc...)
			if (string.length() > 2)
				keywords.add(string);
		}

		// On récupère notre liste d'objets Keywords
		this.keywords = keywordsDAO.findByListKeywords(keywords);

		this.numberPages = pageDAO.findNumberPages(this.keywords);
		
		// On vérifie que le numéro de pagination demandé est valide.
		Integer paging = numberPage;
		float numberResultFloat = this.numberPages;
		Integer numberPaging = (int) Math.ceil(numberResultFloat / INTERVAL);
		if (numberPage < 1) {
		    paging = 1;
		} else if (numberPage > numberPaging) {
		    paging = 1;
		}
		
		// On récupère notre liste de Page
		this.pages = pageDAO.findByListKeywords(this.keywords, paging, INTERVAL);
		
		// On génère l'intervalle des pages concernés
		initInterval(paging);
	}
	
	/**
	 * Permet de génèrer l'intervalle des pages concernées par la pagination en cour
	 * @param paging Integer
	 */
	private void initInterval(Integer paging) {
        Integer startPages = (paging * INTERVAL);
        this.interval = (startPages - INTERVAL + 1) + " - " + startPages;
        if (startPages > this.numberPages) {
            if (ZERO.equals(this.numberPages)) {
                this.interval = "";
            } else {
                this.interval = (startPages - INTERVAL + 1) + " - " + this.numberPages;
            }
        }
	}

	/**
	 * @return the pages
	 */
	public List<Page> getPages() {
		return pages;
	}

	/**
	 * @param pages
	 *            the pages to set
	 */
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * @param input
	 *            the input to set
	 */
	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * @return the keywords
	 */
	public List<Keywords> getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords
	 *            the keywords to set
	 */
	public void setKeywords(List<Keywords> keywords) {
		this.keywords = keywords;
	}

    /**
     * @return the numberPages
     */
    public Integer getNumberPages() {
        return numberPages;
    }

    /**
     * @param numberPages the numberPages to set
     */
    public void setNumberPages(Integer numberPages) {
        this.numberPages = numberPages;
    }

    /**
     * @return the interval
     */
    public String getInterval() {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(String interval) {
        this.interval = interval;
    }

}
