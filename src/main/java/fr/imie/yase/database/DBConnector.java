package fr.imie.yase.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Supamiu
 *
 * Classe qui gère les connections à la base de données
 */
public class DBConnector {
	private static final String URL = "jdbc:postgresql://" + System.getenv("PG_URL") + ":" + System.getenv("PG_PORT") + "/" + System.getenv("PG_DB");
	
	private static final String LOGIN = System.getenv("PG_USER");
	
	private static final String PASSWORD = System.getenv("PG_PASSWORD");

	private static Connection _instance;
	
	/**
	 * Singleton
	 * 
	 * @return {@link java.sql.Connection}
	 * @throws ClassNotFoundException 
	 */
	public static Connection getInstance() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			if(_instance == null || _instance.isClosed()){
				_instance = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			}
		} catch (SQLException e) {
			System.err.println("ERREUR DE CONNECTION POSTGRESQL");
			e.printStackTrace();
		}
		return _instance;
	}
}
