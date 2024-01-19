package br.ita.fg.model;

public class Usuario {
	private String _login;
	private String _email;
	private String _nome;
	private String _senha;
	private Integer _pontos;
	private UsuarioStatus _status;	
	private UsuarioNivel _nivel;

	
	public Usuario(String login, String email, String nome, String senha, Integer pontos, UsuarioStatus status, UsuarioNivel nivel) {
		_login = login;
		_email = email;
		_nome = nome;
		_senha = senha;
		_pontos = pontos;
		_status = status;
		_nivel = nivel;
	}

	public String getLogin() {
		return _login;
	}

	public String getEmail() {
		return _email;
	}

	public String getNome() {
		return _nome;
	}

	public String getSenha() {
		return _senha;
	}

	public Integer getPontos() {
		return _pontos;
	}

	public UsuarioStatus getStatus() {
		return _status;
	}

	public UsuarioNivel getNivel() {
		return _nivel;
	}
}
