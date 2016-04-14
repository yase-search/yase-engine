package fr.imie.yase.dto;

import java.util.List;

/**
 * DTO SearchResult
 * @author Erwan
 *
 */
public class Page {
	
	private String title;
	
	private List<Keywords> keywords;
		
	private String description;
	
	private String url;
	
	private Integer id;
		
	/**
	 * Construct
	 * @param title String
	 * @param text String 
	 * @param description String
	 */
	public Page(Integer id, String title, List<Keywords> keywords, String description, String url) {
		this.id = id;
		this.title = title;
		this.keywords = keywords;
		this.description = description;
		this.url = url;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the text
	 */
	public List<Keywords> getKeywords() {
		return keywords;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(List<Keywords> keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param description the description to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
}
