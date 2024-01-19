package br.ita.fg.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestaForum {
	
	@Test
	void testaInstanciarForum() {
		Forum forum = new Forum(null, null, null, null, null);
		assertTrue(forum instanceof Forum);
	}
	
	@Test
	void testaMetodosGet() { //métodos get não devem lançar NullPointerException
		Forum forum = new Forum(null, null, null, null, null);
		assertEquals(null, forum.getId());
		assertEquals(null, forum.getUsuarioLogin());
		assertEquals(null, forum.getUsuarioNome());
		assertEquals(null, forum.getDataDeInicio());
		assertEquals(null, forum.getForumNome());
		assertEquals(null, forum.getForumDescricao());
	}
}
