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

public class PageDAO implements DAO<Page>{
	
	private static String INSERT_TABLE = String.join("",
		"INSERT INTO pages",
		"(id_website, link, content, title, description, crawl_date, size, load_time, locale)",
		"values (?,?,?,?,?,?,?,?,?)"
	);
	private static final String ATT_ID = "id";

	public Page get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Page> find(Map<String, Object> params) throws SQLException {
		List<Keywords> keywords = (List<Keywords>) params.get("keywords");
		List<Page> ret = new ArrayList<Page>();
		
		if(keywords.size() <= 0){
			return ret;
		}
		
		Connection con = DBConnector.getInstance();
		
		String preparedArgs = "";
		
		for(int i = 0, j = keywords.size(); i < j; i++){
			preparedArgs += "?,";
		}
		
		String req = String.join("",
			"SELECT p.id, p.description, p.title, p.link, p.content from pages p ",
			"INNER JOIN pages_words pw ON pw.idpage = p.id ",
			"INNER JOIN words w ON w.id = pw.idword ",
			"WHERE w.text IN (",
			preparedArgs.substring(0, preparedArgs.length() - 1),
			");"
		);
				
		PreparedStatement statement = con.prepareStatement(req);
		
		for(int i = 0, j = keywords.size(); i < j; i++){
			statement.setString(i + 1, keywords.get(i).getValue());
		}
		
		ResultSet res = statement.executeQuery();
		
		while(res.next()){
			System.out.println(res.getString("url"));
			Page p = new Page();
			p.setId(res.getInt("id"));
			p.setTitle(res.getString("title"));
			p.setDescription(res.getString("description"));
			p.setContent(res.getString("content"));
			p.setUrl(res.getString("link"));
			
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
		statement.setString(6, entity.getCrawl_date());
		statement.setInt(7, entity.getSize());
		statement.setInt(8, entity.getLoad_time());
		statement.setString(9,  entity.getLocale());
		
		statement.execute();
		
		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			entity.setId((int) generatedKeys.getLong(ATT_ID));
		}
		
		return entity;
	}
	
	public Page findByURL(Page page) throws Exception{
		Connection con = DBConnector.getInstance();
		String sql = "SELECT * FROM pages WHERE url = ?";
		
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setString(1,  page.getUrl());
		ResultSet res = statement.executeQuery();
		
		if(res.next()){
			page.setId(res.getInt("id"));
		} else{
			// TODO
			throw new Exception("No results");
		}
		return page;
	}
}
