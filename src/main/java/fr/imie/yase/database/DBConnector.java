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
	
	private static final String URL = "jdbc:postgresql://localhost:5432/yase";
	
	private static final String LOGIN = "postgres";
	
	private static final String PASSWORD = "postgres";

	private static Connection _instance;
	
	/**
	 * Singleton
	 * 
	 * @return {@link java.sql.Connection}
	 */
	public static Connection getInstance(){
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
