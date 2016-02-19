package fr.imie.yase.dto;

/**
 * DTO SearchResult
 * @author Erwan
 *
 */
public class SearchResult {
	
	private String title;
	
	private String text;
	
	private String description;
	
	/**
	 * Construct
	 * @param title String
	 * @param text String 
	 * @param description String
	 */
	public SearchResult(String title, String text, String description) {
		this.title = title;
		this.text = text;
		this.description = description;
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
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	
}
