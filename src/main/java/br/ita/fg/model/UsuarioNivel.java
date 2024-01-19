package br.ita.fg.model;

public enum UsuarioNivel {
	USER("Usuário"),
	ADM("Administrador");
	
	private String _nivel;
	
	private UsuarioNivel(String nivel) {
		_nivel = nivel;
	}

	public String getDescNivel() {
		return _nivel;
	}
	
}
