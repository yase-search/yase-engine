package fr.imie.yase.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.imie.yase.database.DBConnector;
import fr.imie.yase.dto.Keywords;

public class KeywordsDAO implements DAO<Keywords> {

    private static final String SELECT_TABLE = "SELECT id, text FROM words where text = ? ;";
    private static final String SELECT_TABLE_ALL = "SELECT id, text FROM words;";
    private static final String INSERT_TABLE = "INSERT INTO words (text) VALUES (?);";
    private static final String DELETE_TABLE = "DELETE FROM words WHERE id = ? ;";
    private static final String ATT_TEXT = "text";
    private static final String ATT_ID = "id";

    private static Map<String, Integer> keywordsCache = new HashMap<>();

    public Keywords get(int id) {
        return null;
    }

    public List<Keywords> find(Object param) throws SQLException {
        List<Keywords> listKeywords = new ArrayList<Keywords>();
        Keywords keywords = (Keywords) param;
        Keywords result = this.keywordExists(keywords.getValue());
        if(result != null){
            listKeywords.add(result);
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

            // Add the new word to the cache list
            synchronized (this.keywordsCache){
               this.keywordsCache.put(entity.getValue(), entity.getId());
            }
        }
        return entity;
    }

    /**
     * Permet de rechercher par liste de Keywords
     * @param List<String> params
     * @return List<Keywords> listKeywords
     * @throws SQLException SQLException
     */
    public List<Keywords> findByListKeywords(List<String> params) throws SQLException {
        List<Keywords> listKeywords = new ArrayList<Keywords>();
        for (Object keywords : params) {
            Keywords result = this.keywordExists((String)keywords);
            if(result != null) {
                listKeywords.add(result);
            }
        }
        return listKeywords;
    }

    /**
     * Permet de préparer l'objet preparedStatement pour une requete find
     * @param keywords String
     * @return PreparedStatement PreparedStatement
     * @throws SQLException SQLException
     */
    public PreparedStatement preparedStatementOneWords(String keywords) throws SQLException {
        Connection connection = DBConnector.getInstance();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TABLE);
        preparedStatement.setString(1, keywords);
        return preparedStatement;
    }

    public static Map<String, Integer> findAllKeywords() throws SQLException{
        Map<String, Integer> words = new HashMap<String, Integer>();

        Connection connection = DBConnector.getInstance();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TABLE_ALL);
        ResultSet result = preparedStatement.executeQuery();

        if (!result.wasNull()) {
            while (result.next()) {
                words.put(result.getString(ATT_TEXT), result.getInt(ATT_ID));
            }
        }

        return words;
    }

    public static void populateWords(){
        try {
            KeywordsDAO.keywordsCache = KeywordsDAO.findAllKeywords();
            System.out.println("Number of words in database: " + KeywordsDAO.keywordsCache.size());
        } catch (SQLException e){
            System.out.println("Couldn't get all words from database");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Keywords keywordExists(String keyword){
        Integer keywordId;
        synchronized (KeywordsDAO.keywordsCache) {
            keywordId = KeywordsDAO.keywordsCache.get(keyword);
        }

        if (keywordId == null) {
            return this.createKeyword(keyword);
        } else {
            return new Keywords(keyword, false, keywordId);
        }
    }

    private Keywords createKeyword(String keyword){
        KeywordsDAO keywordsDAO = new KeywordsDAO();
        Keywords entity = new Keywords();
        entity.setValue(keyword);

        try {
            Keywords created = keywordsDAO.create(entity);

            if(created.getId() != null) {
                synchronized (KeywordsDAO.keywordsCache) {
                    KeywordsDAO.keywordsCache.put(created.getValue(), created.getId());
                }
            }

            return created;
        } catch (SQLException e){
            // If insertion fails, return the entity which has no ID
            return entity;
        }
    }
}