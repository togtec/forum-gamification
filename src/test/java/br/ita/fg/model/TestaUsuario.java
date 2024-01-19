package br.ita.fg.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestaUsuario {
	
	@Test
	void testaInstanciarUsuario() {
		Usuario usuario = new Usuario(null, null, null, null, null, null, null);
		assertTrue(usuario instanceof Usuario);
	}

	@Test
	void testaMetodosGet() { //métodos get não devem lançar NullPointerException
		Usuario usuario = new Usuario(null, null, null, null, null, null, null);
		assertEquals(null, usuario.getLogin());
		assertEquals(null, usuario.getEmail());
		assertEquals(null, usuario.getNome());
		assertEquals(null, usuario.getSenha());
		assertEquals(null, usuario.getPontos());
		assertEquals(null, usuario.getStatus());
		assertEquals(null, usuario.getNivel());
	}
}
