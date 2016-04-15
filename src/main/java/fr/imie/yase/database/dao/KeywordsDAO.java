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

public class KeywordsDAO implements DAO<Keywords> {
	
	private static final String SELECT_TABLE = "SELECT * FROM words where text = ? ;";
	private static final String INSERT_TABLE = "INSERT INTO words (text) VALUES (?);";
	private static final String DELETE_TABLE = "DELETE FROM words WHERE id = ? ;";
	private static final String ATT_TEXT = "text";
	private static final String ATT_ID = "id";

	public Keywords get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Keywords> find(Map<String, Object> params) throws SQLException {
		List<Keywords> listKeywords = new ArrayList<Keywords>();
		for (Object keywords : params.entrySet()) {
			PreparedStatement preparedStatement = preparedStatementOneWords((String) keywords);
			ResultSet result = preparedStatement.executeQuery();
			// Si la requete est différents de null, on ajoute le Keywords à la liste.
			if (!result.wasNull()) {
				Keywords objectKeywords = new Keywords(result.getString(ATT_TEXT), true, result.getInt(ATT_ID));
				listKeywords.add(objectKeywords);
			}
		}
		return listKeywords;
	}

	public boolean delete(int id) throws SQLException {
		Connection  connection = DBConnector.getInstance();
		PreparedStatement preparedStatement =  connection.prepareStatement(DELETE_TABLE);
		preparedStatement.setInt(1, id);
		return preparedStatement.execute();
	}

	public Keywords update(Keywords entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Keywords create(Keywords entity) throws SQLException {
		Connection  connection = DBConnector.getInstance();
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TABLE, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, entity.getValue());
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
	public PreparedStatement preparedStatementOneWords(String keywords) throws SQLException {
		Connection  connection = DBConnector.getInstance();
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TABLE);
		preparedStatement.setString(1, (String) keywords);
		return preparedStatement;
	}

}
