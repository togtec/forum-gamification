package br.ita.fg.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ita.fg.model.Forum;
import br.ita.fg.model.Usuario;
import br.ita.fg.util.AuxiliaTest;

class TestaForumDAO {

	@BeforeEach
	void setUp() throws Exception {
		AuxiliaTest.popularBanco("inicio.xml");		
	}

	@Test
	void inserirForum() throws Exception {
		Usuario usuario = UsuarioDAO.getUsuarioByLogin("sabcoral");
		String forumNome = "Snake Pass";
		String forumDescricao = "Fórum destinado a discutir temas relacionados ao Jogo Snake Pass. Snake Pass desafia o jogador a \"pensar como uma cobra\" ao encarar quebra-cabeças que só uma cobra conseguiria resolver.";
		Forum forum = new Forum(null, usuario, null, forumNome, forumDescricao); //id, criador, dataDeInicio, forumNome, forumDescricao 
		
		ForumDAO.inserirForum(forum); 
		
		ITable expectedTable = AuxiliaTest.expectedTable("verificaInserir.xml", "forum");
		ITable currentTable = AuxiliaTest.currentTable("forum"); 
		
		currentTable = AuxiliaTest.sincronizarColunas(currentTable, expectedTable);
		
		assertEquals(4, currentTable.getRowCount());		
		Assertion.assertEquals(expectedTable, currentTable);		
	}
	
	@Test
	void recuperarForumDoBancoDeDados() throws Exception {
		Forum forum = ForumDAO.getForumById(1);
		assertEquals(1, forum.getId());
		assertEquals("sabcoral", forum.getUsuarioLogin());
		assertEquals("Sabrina Coral", forum.getUsuarioNome());
		assertEquals("01/05/2022", forum.getDataDeInicio());
		assertEquals("Jogo Das Palavras Embaralhadas", forum.getForumNome());
		assertTrue(forum.getForumDescricao().contains("Fórum destinado a discutir temas relacionados ao Jogo Das Palavras Embaralhadas."));
	}
	
	@Test
	void recuperarForumInexistenteNoBancoDeDados() throws Exception {
		assertNull(ForumDAO.getForumById(4));
	}	
	
	@Test
	void recuperarForumList() throws Exception {
		List<Forum> forumList = ForumDAO.getForumList();
		assertEquals(3, forumList.size());
		assertEquals("Jogo Das Palavras Embaralhadas", forumList.get(0).getForumNome());
		assertEquals("Dune 2000", forumList.get(1).getForumNome());
		assertEquals("Doom", forumList.get(2).getForumNome());
	}
	
	@Test
	void editarForumNomeEhForumDescricao() throws Exception {
		Forum forum = ForumDAO.getForumById(2);
		forum.setForumNome("Editado Dune 2000");
		forum.setForumDescricao("Editado Fórum destinado a discutir temas relacionados ao Jogo Dune 2000. Dune 2000 é um jogo de estratégia em tempo real, desenvolvido pela Intelligent Games e lançado pela Westwood Studios em 1998 para Microsoft Windows. Mais tarde, foi portado para o PlayStation em 1999. É um remake parcial de Dune II, que é vagamente baseado no universo Dune de Frank Herbert. A história do jogo é semelhante a Dune II, e continua em Emperor: Battle for Dune.");
		ForumDAO.atualizarForum(forum);
		
		ITable expectedTable = AuxiliaTest.expectedTable("verificaAtualizar.xml", "forum");
		ITable currentTable = AuxiliaTest.currentTable("forum"); 
		
		currentTable = AuxiliaTest.sincronizarColunas(currentTable, expectedTable);
		
		assertEquals(3, currentTable.getRowCount());		
		Assertion.assertEquals(expectedTable, currentTable);
	}	
}
