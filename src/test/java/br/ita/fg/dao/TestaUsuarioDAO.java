package br.ita.fg.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ita.fg.model.Usuario;
import br.ita.fg.model.UsuarioNivel;
import br.ita.fg.model.UsuarioStatus;
import br.ita.fg.util.AuxiliaTest;


class TestaUsuarioDAO {
	private String _login = "tatipires";
	private String _email = "tati.pires@globo.com";
	private String _nome = "Tatiana Pires";
	private String _senha = "tp1221";
	private Integer _pontos = 0;
	private UsuarioStatus _usuarioStatus = UsuarioStatus.ACTIVE;
	private UsuarioNivel _usuarioNivel = UsuarioNivel.USER;
	
	@BeforeEach
	void setUp() throws Exception {
		AuxiliaTest.popularBanco("inicio.xml");
	}

	@Test
	void inserirUsuario() throws Exception {
		Usuario usuario = new Usuario(_login, _email, _nome, _senha, _pontos, _usuarioStatus, _usuarioNivel);
		UsuarioDAO.inserirUsuario(usuario);
		
		ITable expectedTable = AuxiliaTest.expectedTable("verificaInserir.xml", "usuario");
		ITable currentTable = AuxiliaTest.currentTable("usuario"); 
		
		currentTable = AuxiliaTest.sincronizarColunas(currentTable, expectedTable);
		
		assertEquals(8, currentTable.getRowCount());		
		Assertion.assertEquals(expectedTable, currentTable);
	}
	
	@Test
	void quandoInsiroUsuarioComLoginNulo_lancaExcecao() {
		Usuario usuario = new Usuario(null, _email, _nome, _senha, _pontos, _usuarioStatus, _usuarioNivel);
		assertThrows(Exception.class, () -> UsuarioDAO.inserirUsuario(usuario));
	}
	
	@Test
	void quandoInsiroUsuarioComEmailNulo_lancaExcecao() {
		Usuario usuario = new Usuario(_login, null, _nome, _senha, _pontos, _usuarioStatus, _usuarioNivel);
		assertThrows(Exception.class, () -> UsuarioDAO.inserirUsuario(usuario));
	}
	
	@Test
	void quandoInsiroUsuarioComNomeNulo_lancaExcecao() {
		Usuario usuario = new Usuario(_login, _email, null, _senha, _pontos, _usuarioStatus, _usuarioNivel);
		assertThrows(Exception.class, () -> UsuarioDAO.inserirUsuario(usuario));
	}
	
	@Test
	void quandoInsiroUsuarioComSenhaNula_lancaExcecao() {
		Usuario usuario = new Usuario(_login, _email, _nome, null, _pontos, _usuarioStatus, _usuarioNivel);
		assertThrows(Exception.class, () -> UsuarioDAO.inserirUsuario(usuario));
	}
	
	@Test
	void quandoInsiroUsuarioComPontosNulo_lancaExcecao() {
		Usuario usuario = new Usuario(_login, _email, _nome, _senha, null, _usuarioStatus, _usuarioNivel);
		assertThrows(Exception.class, () -> UsuarioDAO.inserirUsuario(usuario));
	}
	
	@Test
	void quandoInsiroUsuarioComStatusNulo_lancaExcecao() {
		Usuario usuario = new Usuario(_login, _email, _nome, _senha, _pontos, null, _usuarioNivel);
		assertThrows(Exception.class, () -> UsuarioDAO.inserirUsuario(usuario));
	}
	
	@Test
	void quandoInsiroUsuarioComNivelNulo_lancaExcecao() {
		Usuario usuario = new Usuario(_login, _email, _nome, _senha, _pontos, _usuarioStatus, null);
		assertThrows(Exception.class, () -> UsuarioDAO.inserirUsuario(usuario));
	}
		
	@Test
	void quandoInsiroUsuarioComLoginJaCadastradoNoSistema_lancaExcecao() {
		Usuario usuario = new Usuario("alelima", _email, _nome, _senha, _pontos, _usuarioStatus, _usuarioNivel);
		assertThrows(Exception.class, () -> UsuarioDAO.inserirUsuario(usuario));
	}
	
	@Test
	void recuperaUsuarioDoBancoDeDados() throws Exception {
		Usuario usuario = UsuarioDAO.getUsuarioByLogin("sabcoral");
		assertEquals("sabcoral", usuario.getLogin());
		assertEquals("sabcoral@gmail.com", usuario.getEmail());
		assertEquals("Sabrina Coral", usuario.getNome());
		assertEquals(null, usuario.getSenha());
		assertEquals(223, usuario.getPontos());
		assertEquals("ACTIVE", usuario.getStatus().toString());
		assertEquals("ADM", usuario.getNivel().toString());
	}
	
	@Test
	void recuperaUsuarioInexistenteNoBancoDeDados() throws Exception {
		assertNull(UsuarioDAO.getUsuarioByLogin("inexistente"));
	}
	
	@Test
	void autenticarUsuario() throws Exception {
		Usuario usuario = UsuarioDAO.autenticarUsuario("alelima", "ab1234");
		assertTrue(usuario instanceof Usuario);
		assertEquals("alelima", usuario.getLogin());
		assertNull(usuario.getEmail());
		assertEquals("Alessandra Lima", usuario.getNome());
		assertNull(usuario.getSenha());
		assertNull(usuario.getPontos());
		assertEquals("ACTIVE", usuario.getStatus().toString());
		assertEquals("USER", usuario.getNivel().toString());
	}
	
	@Test
	void quandoAutenticoUsuarioExistenteComSenhaErrada_retornaNull() throws Exception {
		assertNull(UsuarioDAO.autenticarUsuario("alelima", "ab123456"));
	}
	
	@Test
	void quandoAutenticoUsuarioInexistente_retornaNull() throws Exception {
		assertNull(UsuarioDAO.autenticarUsuario("inexistente", "123"));
	}
	
	@Test
	void editarUsuarioEmail() throws Exception {
		Usuario usuario = UsuarioDAO.getUsuarioByLogin("flaviosp");		
		assertEquals("flaviosp@bol.com.br", usuario.getEmail()); //email atual
		
		UsuarioDAO.atualizarUsuarioEmail("editadoflaviosp@bol.com.br", "flaviosp"); //novo email		
		Usuario usuarioAtualizado = UsuarioDAO.getUsuarioByLogin("flaviosp");
		assertEquals("editadoflaviosp@bol.com.br", usuarioAtualizado.getEmail());
	}
	
	@Test
	void editarUsuarioNome() throws Exception {
		Usuario usuario = UsuarioDAO.getUsuarioByLogin("flaviosp");
		assertEquals("Flavio Sampaio", usuario.getNome()); //nome atual
		
		UsuarioDAO.atualizarUsuarioNome("Editado Flavio Sampaio", "flaviosp"); //novo nome		
		Usuario usuarioAtualizado = UsuarioDAO.getUsuarioByLogin("flaviosp");
		assertEquals("Editado Flavio Sampaio", usuarioAtualizado.getNome());
	}
	
	@Test
	void editarUsuarioSenha() throws Exception {
		//testa antes de editar
		Usuario usuario = UsuarioDAO.autenticarUsuario("sabcoral", "vg3516");
		assertEquals("Sabrina Coral", usuario.getNome());
		
		//troca a senha
		UsuarioDAO.atualizarUsuarioSenha("editadovg3516", "sabcoral");
		
		//testa senha antiga
		assertNull(UsuarioDAO.autenticarUsuario("sabcoral", "vg3516"));
		//testa senha nova
		usuario = UsuarioDAO.autenticarUsuario("sabcoral", "editadovg3516");
		assertEquals("Sabrina Coral", usuario.getNome());
	}
	
	@Test
	void adicionarPontos() throws Exception {
		assertEquals(25, UsuarioDAO.getUsuarioByLogin("alelima").getPontos());
		assertEquals(0, UsuarioDAO.getUsuarioByLogin("braraujo").getPontos());
		assertEquals(1, UsuarioDAO.getUsuarioByLogin("flaviosp").getPontos());
		
		UsuarioDAO.adicionarPontos("alelima", 10);
		UsuarioDAO.adicionarPontos("braraujo", 3);
		UsuarioDAO.adicionarPontos("flaviosp", 5);
		
		assertEquals(35, UsuarioDAO.getUsuarioByLogin("alelima").getPontos());
		assertEquals(3, UsuarioDAO.getUsuarioByLogin("braraujo").getPontos());
		assertEquals(6, UsuarioDAO.getUsuarioByLogin("flaviosp").getPontos());
	}
	
	@Test
	void ranking() throws Exception {
		List<Usuario> ranking = UsuarioDAO.getUsuarioRanking();
		assertEquals(223, ranking.get(0).getPontos());
		assertEquals(135, ranking.get(1).getPontos());
		assertEquals(43, ranking.get(2).getPontos());
		assertEquals(32, ranking.get(3).getPontos());
		assertEquals(25, ranking.get(4).getPontos());
		assertEquals(1, ranking.get(5).getPontos());
		assertEquals(0, ranking.get(6).getPontos());
	}
}
