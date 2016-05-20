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
	
	private static final String ATT_TEXT = "domain";
	private static final String ATT_ID = "id";
	private static final String ATT_SITE_RANK = "site_rank";
	
	private static final String SELECT_TABLE = "SELECT * FROM websites where " + ATT_TEXT + " = ? ;";
	private static final String INSERT_TABLE = "INSERT INTO websites (" + ATT_TEXT + ", " + ATT_SITE_RANK + ") VALUES (?, ?);";
	private static final String DELETE_TABLE = "DELETE FROM websites WHERE id = ? ;";

	public WebSite get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WebSite> find(Map<String, Object> params) throws SQLException {
		List<WebSite> listWebSite = new ArrayList<WebSite>();
		for (WebSite website : (List<WebSite>) params.get("website")) {
			PreparedStatement preparedStatement = preparedStatementFind(website.getDomain());
			ResultSet result = preparedStatement.executeQuery();
<<<<<<< HEAD
			// Si la requete est différents de null, on ajoute le Keywords à la liste.
			if (!result.wasNull()) {
=======
			// Si la requete est diff�rents de null, on ajoute le Keywords � la liste.
			if (result.next()) {
>>>>>>> dcc852647265a36cbd016b8c5fec8e355af4e0ce
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
		preparedStatement.setInt(2, 1);
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
