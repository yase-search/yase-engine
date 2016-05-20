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
			if(_instance == null || _instance.isClosed()){
//				System.out.println(System.getenv("PG_URL"));
//				System.out.println(System.getenv("PG_USER"));
//				System.out.println(System.getenv("PG_PASSWORD"));
//				System.out.println(URL);
//				System.out.println(LOGIN);
//				System.out.println(PASSWORD);
				_instance = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			}
		} catch (SQLException e) {
			System.err.println("ERREUR DE CONNECTION POSTGRESQL");
			e.printStackTrace();
		}
		return _instance;
	}
}
