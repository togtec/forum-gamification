package br.ita.fg.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class TestaDBConnector {

	@Test
	void testaConexaoComBancoDeDados() throws SQLException, Exception {
		try(Connection conn = DBConnector.getConnection()) {
			assertNotNull(conn);
		}
	}
	
	@Test
	void testaJdbcDatabaseTester() throws Exception {
		assertNotNull(DBConnector.getJdbcDatabaseTester());
	}
}
