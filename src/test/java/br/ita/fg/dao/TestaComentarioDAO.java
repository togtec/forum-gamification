package br.ita.fg.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ita.fg.model.Comentario;
import br.ita.fg.model.Topico;
import br.ita.fg.model.Usuario;
import br.ita.fg.util.AuxiliaTest;

class TestaComentarioDAO {

	@BeforeEach
	void setUp() throws Exception {
		AuxiliaTest.popularBanco("inicio.xml");
	}

	@Test
	void inserirComentario() throws Exception {
		Topico topico = TopicoDAO.getTopicoById(4); //Ordem das construções
		Usuario usuario = UsuarioDAO.getUsuarioByLogin("ibatista");
		String comentario = "Energia, barraca, refinaria, refinaria, fábrica de tanque leve, fábrica de tanque pesado.";
		Comentario c = new Comentario(null, topico, usuario, null, comentario); //id, topico, usuario, dataHora, comentario
		
		ComentarioDAO.inserirComentario(c);
		
		ITable expectedTable = AuxiliaTest.expectedTable("verificaInserir.xml", "comentario");
		ITable currentTable = AuxiliaTest.currentTable("comentario"); 
		
		currentTable = AuxiliaTest.sincronizarColunas(currentTable, expectedTable);
		
		assertEquals(12, currentTable.getRowCount());		
		Assertion.assertEquals(expectedTable, currentTable);
	}
	
	@Test
	void recuperarComentarioDoBancoDeDados() throws Exception {
		Comentario comentario = ComentarioDAO.getComentarioById(2);
		assertEquals(1, comentario.getForumId());
		assertEquals("Jogo Das Palavras Embaralhadas", comentario.getForumNome());
		assertEquals(1, comentario.getTopicoId());
		assertEquals("Implementações de MecanicaDoJogo", comentario.getTopicoNome());
		assertEquals(2, comentario.getComentarioId());		
		assertEquals("Legal! Boa ideia! Mas e se o jogador realmente não souber a palavra. Ele fica preso na palavra em que está para sempre? A partida não acaba nunca?", comentario.getComentario());
		assertEquals("ibatista", comentario.getUsuarioLogin());
		assertEquals("Igor Batista", comentario.getUsuarioNome());
		assertEquals("01/05/2022 12:50", comentario.getDataHora());
	}
	
	@Test
	void recuperarComentarioInexistenteNoBancoDeDados() throws Exception {
		assertNull(ComentarioDAO.getComentarioById(12));
	}
	
	@Test
	void recuperarComentarioList() throws Exception {
		List<Comentario> comentarioList = ComentarioDAO.getComentarioListByTopico(1);
		//recupera os comentários referentes ao tópico: Implementações de MecanicaDoJogo 
		assertTrue(comentarioList.get(0).getComentario().contains("Minha ideia é criar uma mecânica em que o jogador não tenha limite de palpites."));
		assertTrue(comentarioList.get(1).getComentario().contains("Legal! Boa ideia! Mas e se o jogador realmente não souber a palavra. Ele fica preso na palavra em que está para sempre?"));
		assertTrue(comentarioList.get(2).getComentario().contains("Para resolver seria necessário criar um limite de tempo para encerrar a partida."));
		assertTrue(comentarioList.get(3).getComentario().contains("Mas criar um objeto para contar o tempo das partidas pode ser algo complicado! Alguém sabe como fazer isso?"));
		assertTrue(comentarioList.get(4).getComentario().contains("Uma outra solução seria, por exemplo, permitir que o jogador desista da palavra atual e avance para a palavra seguinte mediante um comando."));
		assertTrue(comentarioList.get(5).getComentario().contains("Utilizando a ideia acima de criar uma mecânica baseada no número de palpites, estou pensando em criar uma mecânica em que o usuário tenha 3 palpites por palavra."));
		assertTrue(comentarioList.get(6).getComentario().contains("Legal! É possível fazer também uma mecânica baseada no número de palpites por partida."));
		
		//recupera os comentários referentes ao tópico: Harkonnen 07 Hard
		comentarioList = ComentarioDAO.getComentarioListByTopico(5);
		assertEquals("06/05/2022 09:30", comentarioList.get(0).getDataHora());
		assertEquals("07/05/2022 03:54", comentarioList.get(1).getDataHora());
		assertEquals("07/05/2022 22:45", comentarioList.get(2).getDataHora());
		assertEquals("08/05/2022 08:22", comentarioList.get(3).getDataHora());
	}
	
	@Test
	void editarComentario() throws Exception {
		Comentario comentario = ComentarioDAO.getComentarioById(2);
		comentario.setComentario("Editado Legal! Boa ideia! Mas e se o jogador realmente não souber a palavra. Ele fica preso na palavra em que está para sempre? A partida não acaba nunca?");
		ComentarioDAO.atualizarComentario(comentario);
		
		ITable expectedTable = AuxiliaTest.expectedTable("verificaAtualizar.xml", "comentario");
		ITable currentTable = AuxiliaTest.currentTable("comentario");
		
		currentTable = AuxiliaTest.sincronizarColunas(currentTable, expectedTable);
		
		assertEquals(11, currentTable.getRowCount());		
		Assertion.assertEquals(expectedTable, currentTable);
	}
}