package fr.imie.yase.dto;

public class PageKeywords {
	private Page page;
	private Keywords keyword;
	private Integer strengh;
	
	public PageKeywords(Page page, Keywords keyword, Integer strengh){
		this.page = page;
		this.keyword = keyword;
		this.strengh = strengh;
	}
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public Keywords getKeyword() {
		return keyword;
	}
	public void setKeyword(Keywords keyword) {
		this.keyword = keyword;
	}
	public Integer getStrengh() {
		return strengh;
	}
	public void setStrengh(Integer strengh) {
		this.strengh = strengh;
	}
}
