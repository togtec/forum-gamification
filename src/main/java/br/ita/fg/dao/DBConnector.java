package br.ita.fg.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.JdbcDatabaseTester;


public class DBConnector {
	private static final String _driver = "org.postgresql.Driver";
	private static final String _url = "jdbc:postgresql://localhost/forum_gamification_db?currentSchema=public";
	private static final String _usuario = "ita";
	private static final String _senha = "admin";
	
	static {	
		try {
			Class.forName(_driver);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static Connection getConnection() throws Exception {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(_url, _usuario, _senha);
		} catch (Exception e) {			
			String strError = "Não foi possível estabelecer conexão com o Banco de Dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}
		return connection;
	}
	
	public static JdbcDatabaseTester getJdbcDatabaseTester() throws Exception {
		JdbcDatabaseTester jdt = null;		
		try {
			jdt = new JdbcDatabaseTester(_driver, _url, _usuario, _senha);
		} catch (Exception e) {
			String strError = "Não foi possível instanciar JdbcDatabaseTester!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}		
		return jdt;
	}
}
