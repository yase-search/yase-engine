package fr.imie.yase.admin.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.imie.yase.admin.dto.AdminPage;
import fr.imie.yase.database.DBConnector;
import fr.imie.yase.database.dao.PageDAO;
import fr.imie.yase.database.dao.PageKeywordsDAO;

public class AdminPageDAO extends PageDAO {

    private static final String SELECT_ALL_PAGES_TO_WEBSITE = "SELECT COUNT(*) FROM pages WHERE id_website = ? ;";

    /**
     * Permet de retourner le nombre de pages associée à un website
     * @param idWebsite Integer
     * @return List<AdminPage>
     * @throws SQLException
     */
    public int findAllPagesToWebSite(Integer idWebsite) throws SQLException {
        Integer numberPage = 0;
        PreparedStatement preparedStatement = preparedStatementAllPagesToWebsite(idWebsite);
        ResultSet result = preparedStatement.executeQuery();
        
        if (result.next()) {
            numberPage = result.getInt(1);
        }
        return numberPage;
    }
    
    /** 
     * Permet de préparer l'objet preparedStatement pour une requête findAllPagesToWebsite
     * @param idWebsite Integer
     * @return PreparedStatement PreparedStatement
     * @throws SQLException SQLException
     */
    public PreparedStatement preparedStatementAllPagesToWebsite(Integer idWebsite) throws SQLException {
        Connection connection = DBConnector.getInstance();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PAGES_TO_WEBSITE);
        preparedStatement.setInt(1, idWebsite);
        return preparedStatement;
    }
	
}
