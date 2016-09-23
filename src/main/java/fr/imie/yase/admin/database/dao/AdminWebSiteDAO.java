package fr.imie.yase.admin.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.imie.yase.admin.dto.AdminWebSite;
import fr.imie.yase.database.DBConnector;
import fr.imie.yase.database.dao.WebSiteDAO;

public class AdminWebSiteDAO extends WebSiteDAO {
	
	private static final String SELECT_TABLE_ALL = "SELECT * FROM websites;";

	public List<AdminWebSite> findAll() throws SQLException {
		List<AdminWebSite> listWebSite = new ArrayList<AdminWebSite>();
		Connection  connection = DBConnector.getInstance();
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TABLE_ALL);
		ResultSet result = preparedStatement.executeQuery();
		
		// Si la requete est différente de null, on ajoute le PageKeywords à la liste.
		if (!result.wasNull()) {
		    AdminPageDAO pageDAO = new AdminPageDAO();
			while (result.next()) {              
				AdminWebSite objectWebsite = new AdminWebSite(result.getInt(ATT_ID), result.getString(ATT_TEXT), result.getString(ATT_PROTOCOL));
				objectWebsite.setNumberPage(pageDAO.findAllPagesToWebSite(objectWebsite.getId()));
			    listWebSite.add(objectWebsite);
			}
		}
		return listWebSite;
	}
	
}
