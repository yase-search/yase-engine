package fr.imie.yase.admin.dto;

import fr.imie.yase.dto.WebSite;

public class AdminWebSite extends WebSite implements Comparable<AdminWebSite> {
	
    /**
     * Integer numberPage
     */
	private Integer numberPage;

	public AdminWebSite(Integer id, String url, String protocol, Integer numberPage) {
		super(id, url, protocol);
		this.numberPage = numberPage;
	}
	
	public AdminWebSite(Integer id, String url, String protocol) {
		super(id, url, protocol);
	}

    public int compareTo(AdminWebSite o) {
        return this.getDomain().compareTo(o.getDomain());
    }

    /**
     * @return the numberPage
     */
    public Integer getNumberPage() {
        return numberPage;
    }

    /**
     * @param numberPage the numberPage to set
     */
    public void setNumberPage(Integer numberPage) {
        this.numberPage = numberPage;
    }

}
