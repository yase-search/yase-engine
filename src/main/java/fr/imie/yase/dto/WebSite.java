package fr.imie.yase.dto;

public class WebSite {

	private Integer id;
	private String domain;
	private String protocol;
	
	public WebSite(Integer id, String url, String protocol) {
		this.id = id;
		this.domain = url;
		this.protocol = protocol;
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

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
