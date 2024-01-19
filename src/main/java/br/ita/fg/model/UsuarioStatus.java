package br.ita.fg.model;

public enum UsuarioStatus {
	ACTIVE("Ativo"),
	BLOCKED("Bloqueado");
	
	private String _status;
	
	private UsuarioStatus(String status) {
		_status = status;
	}
	
	public String getDescStatus() {
		return _status;
	}
	
}
