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

	private String input;

	private List<Keywords> keywords = new ArrayList<Keywords>();

	private List<Page> pages = new ArrayList<Page>();

	private PageDAO pageDAO = new PageDAO();

	private KeywordsDAO keywordsDAO = new KeywordsDAO();

	/**
	 * Construct
	 * 
	 * @param input
	 *            String
	 * @throws SQLException
	 */
	public Search(String input) throws SQLException {
		this.input = input;
		// Pour couper la chaine d'entrée avec les espaces ou les '
		// TODO: trouver un moyen de sortir le critère de délimitation (en
		// attribut de classe ou plus globalement)
		String delimiter = "[ ']";
		String[] splittedInput = input.split(delimiter);

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

		// On récupère notre liste de Page
		this.pages = pageDAO.findByListKeywords(this.keywords);
	}

	/**
	 * Renvoie les résultats de la recherche au client.
	 * 
	 * @return String JSON
	 */
	private String renderView() {
		// Instruction sur l'envoie du JSON.
		return "";
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

}
