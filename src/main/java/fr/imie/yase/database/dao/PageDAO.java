package fr.imie.yase.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.imie.yase.database.DBConnector;
import fr.imie.yase.dto.Keywords;
import fr.imie.yase.dto.Page;
import fr.imie.yase.dto.WebSite;

public class PageDAO implements DAO<Page>{
	
	private static String INSERT_TABLE = String.join("",
		"INSERT INTO pages",
		"(id_website, url, content, title, description, crawl_date, size, load_time, locale, favicon)",
		"values (?,?,?,?,?,NOW(),?,?,?,?)"
	);
	private static final String ATT_ID = "id";

	public Page get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Page> find(Object param) throws SQLException {
		Keywords keywords = (Keywords) param;
		List<Page> ret = new ArrayList<Page>();
		
		if(keywords == null){
			return ret;
		}
		
		Connection con = DBConnector.getInstance();
		
		String req = String.join("",
			"SELECT DISTINCT(p.id), p.description, p.title, p.url, p.content, p.favicon ws.id AS idWebsite, ws.domain, ws.protocol from pages p ",
			"INNER JOIN pages_words pw ON pw.idpage = p.id ",
			"INNER JOIN words w ON w.id = pw.idword ",
			"INNER JOIN websites ws ON ws.id = p.id_website ",
			"WHERE w.text IN (?);"
		);
				
		PreparedStatement statement = con.prepareStatement(req);
		statement.setString(1, keywords.getValue());
		
		ResultSet res = statement.executeQuery();
		
		while(res.next()){
			System.out.println(res.getString("url"));
			Page p = new Page();
			p.setId(res.getInt("id"));
			p.setTitle(res.getString("title"));
			p.setDescription(res.getString("description"));
			p.setContent(res.getString("content"));
			p.setUrl(res.getString("url"));
			p.setWebsite(new WebSite(res.getInt("idWebsite"), res.getString("domain"), res.getString("protocol")));
			p.setFaviconUrl(res.getString("favicon"));

			ret.add(p);
		}
		
		return ret;
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Page update(Page entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Page create(Page entity) throws SQLException {
		Connection con = DBConnector.getInstance();
		
		PreparedStatement statement = con.prepareStatement(INSERT_TABLE, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, entity.getWebsite().getId());
		statement.setString(2, entity.getUrl());
		statement.setString(3, entity.getContent());
		statement.setString(4, entity.getTitle());
		statement.setString(5, entity.getDescription());
//	TODO	statement.setString(6, entity.getCrawl_date());
		statement.setInt(6, entity.getSize());
		statement.setInt(7, entity.getLoad_time());
		statement.setString(8,  entity.getLocale());
		statement.setString(9, entity.getFaviconUrl());
		
		statement.execute();
		
		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			entity.setId((int) generatedKeys.getLong(ATT_ID));
		}
		
		return entity;
	}
	
	public Page findByURL(Page page) throws SQLException {
		Connection con = DBConnector.getInstance();
		String sql = "SELECT * FROM pages WHERE url = ?";
		
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setString(1,  page.getUrl());
		ResultSet res = statement.executeQuery();
		
		if(res.next()){
			page.setId(res.getInt("id"));
		}
		return page;
	}
	
	public List<Page> findByListKeywords(List<Keywords> params) throws SQLException {
		List<Page> ret = new ArrayList<Page>();
		
		if(params.size() <= 0){
			return ret;
		}
		
		Connection con = DBConnector.getInstance();
		
		String preparedArgs = "";
		
		for(int i = 0, j = params.size(); i < j; i++){
			preparedArgs += "?,";
		}
		
		String req = String.join("",
			"SELECT DISTINCT(p.id), p.description, p.title, p.url, p.content, p.favicon ws.id AS idWebsite, ws.domain, ws.protocol from pages p ",
			"INNER JOIN pages_words pw ON pw.idpage = p.id ",
			"INNER JOIN words w ON w.id = pw.idword ",
			"INNER JOIN websites ws ON ws.id = p.id_website ",
			"WHERE w.text IN (",
			preparedArgs.substring(0, preparedArgs.length() - 1),
			");"
		);
				
		PreparedStatement statement = con.prepareStatement(req);
		
		for(int i = 0, j = params.size(); i < j; i++){
			statement.setString(i + 1, params.get(i).getValue());
		}
		
		ResultSet res = statement.executeQuery();
		
		while(res.next()){
			System.out.println(res.getString("url"));
			Page p = new Page();
			p.setId(res.getInt("id"));
			p.setTitle(res.getString("title"));
			p.setDescription(res.getString("description"));
			p.setContent(res.getString("content"));
			p.setUrl(res.getString("url"));
			p.setWebsite(new WebSite(res.getInt("idWebsite"), res.getString("domain"), res.getString("protocol")));
			p.setFaviconUrl(res.getString("favicon"));

			ret.add(p);
		}
		
		return ret;
	}
}
