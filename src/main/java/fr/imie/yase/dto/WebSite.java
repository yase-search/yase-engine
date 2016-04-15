package fr.imie.yase.dto;

public class WebSite {

	private Integer id;
	private String domain;
	
	public WebSite(Integer id, String url) {
		this.id = id;
		this.domain = url;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the url
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param url the url to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
}
