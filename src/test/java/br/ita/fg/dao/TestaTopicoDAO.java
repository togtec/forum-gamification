package br.ita.fg.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ita.fg.model.Forum;
import br.ita.fg.model.Topico;
import br.ita.fg.model.Usuario;
import br.ita.fg.util.AuxiliaTest;

class TestaTopicoDAO {

	@BeforeEach
	void setUp() throws Exception {
		AuxiliaTest.popularBanco("inicio.xml");	
	}

	@Test
	void inserirTopico() throws Exception {
		Forum forum = ForumDAO.getForumById(1);
		Usuario usuario = UsuarioDAO.getUsuarioByLogin("braraujo");
		String topicoNome = "Tempo de Duração da Partida";
		String topicoConteudo = "Eu gostaria de medir o tempo de duração de cada partida. Alguém sabe como posso fazer isso?";
		Topico topico = new Topico(null, forum, usuario, null, topicoNome, topicoConteudo); //id, forum, criador, data, topicoNome, topicoConteudo
		
		TopicoDAO.inserirTopico(topico);
		
		ITable expectedTable = AuxiliaTest.expectedTable("verificaInserir.xml", "topico");
		ITable currentTable = AuxiliaTest.currentTable("topico"); 
		
		currentTable = AuxiliaTest.sincronizarColunas(currentTable, expectedTable);
		
		assertEquals(6, currentTable.getRowCount());		
		Assertion.assertEquals(expectedTable, currentTable);
	}

	@Test
	void recuperarTopicoDoBancoDeDados() throws Exception {
		Topico topico = TopicoDAO.getTopicoById(2);
		assertEquals(2, topico.getTopicoId());
		assertEquals(1, topico.getForumId());
		assertEquals("Jogo Das Palavras Embaralhadas", topico.getForumNome());
		assertEquals("Paulo Cintra", topico.getUsuarioNome());
		assertEquals("paulo.cintra", topico.getUsuarioLogin());
		assertEquals("02/05/2022", topico.getData());
		assertEquals("Implementações de Embaralhador", topico.getTopicoNome());
		assertEquals("Alguém tem uma boa ideia para implementar a interface embaralhador?", topico.getTopicoConteudo());
	}
	
	@Test
	void recuperarTopicoInexistenteNoBancoDeDados() throws Exception {
		assertNull(TopicoDAO.getTopicoById(8));
	}
	
	@Test
	void recuperarListaDeTopicosPorForum() throws Exception {
		//recupera a lista de tópicos do fórum Dune 2000
		List<Topico> topicoList = TopicoDAO.getTopicoListByForum(2);
		assertEquals(3, topicoList.size());
		assertEquals("Teclas de Atalho", topicoList.get(0).getTopicoNome());
		assertEquals("Ordem das construções", topicoList.get(1).getTopicoNome());
		assertEquals("Harkonnen 07 Hard", topicoList.get(2).getTopicoNome());
	}
	
	@Test
	void recuperarListaDeTopicosPorUsuario() throws Exception {
		//recupera a lista de tópicos do usuário sabcoral
		List<Topico> topicoList = TopicoDAO.getTopicoListByUsuario("sabcoral");
		assertEquals(1, topicoList.size());
		assertEquals("Harkonnen 07 Hard", topicoList.get(0).getTopicoNome());
	}
	
	@Test
	void editarTopicoNomeEhTopicoConteudo() throws Exception {
		Topico topico = TopicoDAO.getTopicoById(3);
		topico.setTopicoNome("Editado Teclas de Atalho");
		topico.setTopicoConteudo("Editado Segue abaixo a lista de teclas de atalho do Dune 2000 descobertas por mim até agora.");
		TopicoDAO.atualizarTopico(topico);
		
		ITable expectedTable = AuxiliaTest.expectedTable("verificaAtualizar.xml", "topico");
		ITable currentTable = AuxiliaTest.currentTable("topico");
		
		currentTable = AuxiliaTest.sincronizarColunas(currentTable, expectedTable);
		
		assertEquals(5, currentTable.getRowCount());
		Assertion.assertEquals(expectedTable, currentTable);
	}
}
