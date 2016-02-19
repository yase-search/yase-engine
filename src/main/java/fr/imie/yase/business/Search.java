package fr.imie.yase.business;

import java.util.ArrayList;
import java.util.List;

import fr.imie.yase.dto.Keywords;

public class Search {

	private String input;
	
	private List<Keywords> keywords = new ArrayList<Keywords>();
	
	/**
	 * Construct 
	 * @param input String
	 */
	public Search(String input) {
		this.input = input;
	}
	
	/**
	 * Renvoie les r�sultats de la recherche au client.
	 * @return String JSON
	 */
	private String renderView() {
		// Instruction sur l'envoie du JSON.
		return "";
	}

	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * @param input the input to set
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
	 * @param keywords the keywords to set
	 */
	public void setKeywords(List<Keywords> keywords) {
		this.keywords = keywords;
	}
	
	
	
}
