package br.ita.fg.model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

class TestaComentario {

	@Test
	void testaInstanciarComentario() {
		Comentario comentario = new Comentario(null, null, null, null, null);
		assertTrue(comentario instanceof Comentario);
	}
	
	@Test
	void testaMetodosGet() { //métodos get não devem lançar NullPointerException
		Comentario comentario = new Comentario(null, null, null, null, null);
		assertEquals(null, comentario.getComentarioId());
		assertEquals(null, comentario.getComentario());
		assertEquals(null, comentario.getForumId());
		assertEquals(null, comentario.getForumNome());
		assertEquals(null, comentario.getTopicoId());
		assertEquals(null, comentario.getTopicoNome());
		assertEquals(null, comentario.getUsuarioLogin());
		assertEquals(null, comentario.getUsuarioNome());
		assertEquals(null, comentario.getDataHora());
	}
	
	@Test
	void testaGetDataHora() {		
		Comentario coment = new Comentario(null, null, null, new Date(System.currentTimeMillis()), null); //id, topico, usuario, dataHora, comentario)
		System.out.println(coment.getDataHora());
	}
}
