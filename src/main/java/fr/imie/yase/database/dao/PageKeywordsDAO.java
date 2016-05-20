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
import fr.imie.yase.dto.PageKeywords;

public class PageKeywordsDAO implements DAO<PageKeywords> {
	
	// La technique à Erwan pour qu'il soit content et qu'il sourit à ses collègues et à la vie en général
	private static final String SELECT_TABLE = "SELECT * FROM pages_words where idpage = ? AND idword = ?;";
	private static final String INSERT_TABLE = "INSERT INTO pages_words (idpage,idword,strength) VALUES (?,?,?);";
	private static final String DELETE_TABLE = "";

	private static final String ATT_IDPAGE = "idpage";
	private static final String ATT_IDKEYWORD = "idword";
	private static final String ATT_STRENGTH = "strength";

	public PageKeywords get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PageKeywords> find(Map<String, Object> params) throws SQLException {
		List<PageKeywords> listPageKeywords = new ArrayList<PageKeywords>();
		for (Object pageKeywords : (List<Object>)params.get("pageKeywords")) {
			PreparedStatement preparedStatement = preparedStatementTwoIds((PageKeywords) pageKeywords);
			ResultSet result = preparedStatement.executeQuery();
			
			// Si la requete est différente de null, on ajoute le PageKeywords à la liste.
			if (!result.wasNull()) {
				while (result.next()) {              
				    PageKeywords objectKeywords = new PageKeywords(result.getInt(ATT_IDPAGE), result.getInt(ATT_IDKEYWORD), result.getInt(ATT_STRENGTH));
				    listPageKeywords.add(objectKeywords);
				}
			}
		}
		return listPageKeywords;
	}
	
	public boolean delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public PageKeywords update(PageKeywords entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public PageKeywords create(PageKeywords entity) throws SQLException {
		Connection  connection = DBConnector.getInstance();
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TABLE, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, entity.getIdPage());
		preparedStatement.setInt(2, entity.getIdKeyword());
		preparedStatement.setInt(3, entity.getStrengh());

		preparedStatement.execute();
		return entity;
	}
	
	/** 
	 * Permet de préparer l'objet preparedStatement pour une requête find
	 * @param pageKeywords PageKeywords
	 * @return PreparedStatement PreparedStatement
	 * @throws SQLException SQLException
	 */
	public PreparedStatement preparedStatementTwoIds(PageKeywords pageKeywords) throws SQLException {
		Connection connection = DBConnector.getInstance();
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TABLE);
		preparedStatement.setInt(1, (int) pageKeywords.getIdPage());
		preparedStatement.setInt(2, (int) pageKeywords.getIdKeyword());
		return preparedStatement;
	}
	
}
