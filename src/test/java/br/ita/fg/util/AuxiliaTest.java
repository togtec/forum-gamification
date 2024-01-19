package br.ita.fg.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import br.ita.fg.dao.DBConnector;

public class AuxiliaTest {
	
	private static IDataSet loadFile(String file) {
		FlatXmlDataFileLoader loader = new FlatXmlDataFileLoader();
		return loader.load("/" + file);
	}
	
	private static void resetSequence() throws Exception {
		try (Connection conn = DBConnector.getConnection()) {
			Statement statement = conn.createStatement();
			
			JdbcDatabaseTester jdt = DBConnector.getJdbcDatabaseTester();				
			IDataSet dataSet = jdt.getConnection().createDataSet();
			String[] tableNames = dataSet.getTableNames();
		
			for (String table : tableNames) {
				String sequenceName = table + "_id_seq";
				int valor = dataSet.getTable(table).getRowCount() + 1;
		
				statement.execute("ALTER SEQUENCE IF EXISTS " + sequenceName + " RESTART WITH " + valor);
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível executar resetSequence no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);			
		}
	}
	
	public static ITable expectedTable(String file, String table) throws DataSetException {
		return loadFile(file).getTable(table);
	}
	
	public static ITable currentTable(String table) throws DataSetException, SQLException, Exception {
		JdbcDatabaseTester jdt = DBConnector.getJdbcDatabaseTester();
		return jdt.getConnection().createDataSet().getTable(table);
	}
		
	public static ITable sincronizarColunas(ITable currentTable, ITable expectedTable) throws DataSetException {
		//sincroniza para que currentTable tenha apenas as colunas presentes em expectedTable
		return DefaultColumnFilter.includedColumnsTable(currentTable, expectedTable.getTableMetaData().getColumns());
	}
	
	public static void popularBanco(String file) throws Exception {
		JdbcDatabaseTester jdt = DBConnector.getJdbcDatabaseTester();
		jdt.setDataSet(loadFile(file));
		jdt.onSetup();
		resetSequence();
	}
}
