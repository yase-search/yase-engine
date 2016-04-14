package fr.imie.yase.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.imie.yase.database.DBConnector;
import fr.imie.yase.dto.Keywords;
import fr.imie.yase.dto.Page;

public class PageDAO implements DAO<Page>{

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
			Page p = new Page(res.getInt("id"),
				res.getString("title"),
				null,
				res.getString("description"),
				res.getString("link"),
				res.getString("content")
			);
			
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

	public Page create(Page entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
