package fr.imie.yase.dto;

public class PageKeywords {
	private int id;
	private int idPage;
	private int idKeyword;
	private Integer strengh;
	
	public PageKeywords(int page, int keyword, Integer strengh){
		this.idPage = page;
		this.idKeyword = keyword;
		this.strengh = strengh;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPage() {
		return idPage;
	}
	public void setIdPage(int page) {
		this.idPage = page;
	}
	public int getIdKeyword() {
		return idKeyword;
	}
	public void setIdKeyword(int keyword) {
		this.idKeyword = keyword;
	}
	public Integer getStrengh() {
		return strengh;
	}
	public void setStrengh(Integer strengh) {
		this.strengh = strengh;
	}
}
