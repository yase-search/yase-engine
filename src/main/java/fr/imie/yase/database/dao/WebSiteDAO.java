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
import fr.imie.yase.dto.WebSite;

public class WebSiteDAO implements DAO<WebSite> {
	
	private static final String SELECT_TABLE = "SELECT * FROM websites where url = ? ;";
	private static final String INSERT_TABLE = "INSERT INTO websites (url) VALUES (?);";
	private static final String DELETE_TABLE = "DELETE FROM websites WHERE id = ? ;";
	private static final String ATT_TEXT = "domain";
	private static final String ATT_ID = "id";

	public WebSite get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WebSite> find(Map<String, Object> params) throws SQLException {
		List<WebSite> listWebSite = new ArrayList<WebSite>();
		for (Object url : (List<Object>) params.get("website")) {
			PreparedStatement preparedStatement = preparedStatementFind((String) url);
			ResultSet result = preparedStatement.executeQuery();
			// Si la requete est différents de null, on ajoute le Keywords à la liste.
			if (!result.wasNull()) {
				WebSite objectWebSite = new WebSite(result.getInt(ATT_ID), result.getString(ATT_TEXT));
				listWebSite.add(objectWebSite);
			}
		}
		return listWebSite;
	}

	public boolean delete(int id) throws SQLException {
		Connection  connection = DBConnector.getInstance();
		PreparedStatement preparedStatement =  connection.prepareStatement(DELETE_TABLE);
		preparedStatement.setInt(1, id);
		return preparedStatement.execute();
	}

	public WebSite create(WebSite entity) throws SQLException {
		Connection  connection = DBConnector.getInstance();
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TABLE, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, entity.getDomain());
		preparedStatement.execute();
		// On récupère l'id si l'insertion en base s'est bien passé.
		ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
		if (generatedKeys.next()) {
			entity.setId((int) generatedKeys.getLong(ATT_ID));
		}
		return entity;
	}
	
	/** 
	 * Permet de préparer l'objet preparedStatement pour une requete find
	 * @param keywords String
	 * @return PreparedStatement PreparedStatement
	 * @throws SQLException SQLException
	 */
	public PreparedStatement preparedStatementFind(String url) throws SQLException {
		Connection  connection = DBConnector.getInstance();
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TABLE);
		preparedStatement.setString(1, (String) url);
		return preparedStatement;
	}

	public WebSite update(WebSite entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
