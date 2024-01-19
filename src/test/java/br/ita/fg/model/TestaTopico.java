package br.ita.fg.model;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestaTopico {

	@Test
	void testaInstanciarTopico() {
		Topico topico = new Topico(null, null, null, null, null, null);
		assertTrue(topico instanceof Topico);
	}

	@Test
	void testaMetodosGet() { //métodos get não devem lançar NullPointerException
		Topico topico = new Topico(null, null, null, null, null, null);
		assertNull(topico.getTopicoId());
		assertNull(topico.getForumId());
		assertNull(topico.getForumNome());
		assertNull(topico.getUsuarioNome());
		assertNull(topico.getUsuarioLogin());
		assertNull(topico.getData());
		assertNull(topico.getTopicoNome());
		assertNull(topico.getTopicoConteudo());
	}
}
