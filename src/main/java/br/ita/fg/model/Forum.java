package br.ita.fg.model;

import java.sql.Date;
import java.text.DateFormat;

public class Forum {
	private static DateFormat _df;	
	private Integer _id;
	private Usuario _usuario;
	private Date _dataDeInicio;
	private String _forumNome;
	private String _forumDescricao;
	
	static {
		_df = DateFormat.getDateInstance(DateFormat.MEDIUM);
	}
	
	public Forum(Integer id, Usuario usuario, Date dataDeInicio, String forumNome, String forumDescricao) {
		_id = id;
		_usuario = usuario;
		_dataDeInicio = dataDeInicio;
		_forumNome = forumNome;
		_forumDescricao = forumDescricao;
	}

	public Integer getId() {
		return _id;
	}

	public String getUsuarioLogin() {
		if (_usuario == null) return null; //evita NullPointerException
		return _usuario.getLogin();
	}
	
	public String getUsuarioNome() {
		if (_usuario == null) return null; //evita NullPointerException
		return _usuario.getNome();
	}
	
	public String getDataDeInicio() {
		if (_dataDeInicio == null) return null; //evita NullPointerException
		return _df.format(_dataDeInicio);
	}

	public String getForumNome() {
		return _forumNome;
	}

	public void setForumNome(String forumNome) {
		_forumNome = forumNome;
	}

	public String getForumDescricao() {
		return _forumDescricao;
	}

	public void setForumDescricao(String forumDescricao) {
		_forumDescricao = forumDescricao;
	}
}
