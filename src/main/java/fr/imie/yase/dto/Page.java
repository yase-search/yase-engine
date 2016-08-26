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
	private String content;
	private String crawl_date;
	private Integer size;
	private Integer load_time;
	private String locale;
	private WebSite website;
	private String faviconUrl;

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
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return this.content;
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
	 * @return the crawl_date
	 */
	public String getCrawl_date() {
		return crawl_date;
	}

	/**
	 * @param crawl_date the crawl_date to set
	 */
	public void setCrawl_date(String crawl_date) {
		this.crawl_date = crawl_date;
	}

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the load_time
	 */
	public Integer getLoad_time() {
		return load_time;
	}

	/**
	 * @param load_time the load_time to set
	 */
	public void setLoad_time(Integer load_time) {
		this.load_time = load_time;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(List<Keywords> keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return the website
	 */
	public WebSite getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(WebSite website) {
		this.website = website;
	}

	public String getFaviconUrl() {
		return faviconUrl;
	}

	public void setFaviconUrl(String faviconUrl) {
		this.faviconUrl = faviconUrl;
	}
}
