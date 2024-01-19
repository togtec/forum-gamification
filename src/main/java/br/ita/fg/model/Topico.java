package br.ita.fg.model;

import java.sql.Date;
import java.text.DateFormat;

public class Topico {
	private static DateFormat _df;
	private Integer _id;
	private Forum _forum;
	private Usuario _usuario;
	private Date _data;
	private String _topicoNome;
	private String _topicoConteudo;
	
	static {
		_df = DateFormat.getDateInstance(DateFormat.MEDIUM);
	}

	public Topico(Integer id, Forum forum, Usuario usuario, Date data, String topicoNome, String topicoConteudo) {
		_id = id;
		_forum = forum;
		_usuario = usuario;
		_data = data;
		_topicoNome = topicoNome;
		_topicoConteudo = topicoConteudo;
	}

	public Integer getTopicoId() {
		return _id;
	}
	
	public Integer getForumId() {
		if (_forum == null) return null; //evita NullPointerException
		return _forum.getId();
	}

	public String getForumNome() {
		if (_forum == null) return null; //evita NullPointerException
		return _forum.getForumNome();
	}

	public String getUsuarioNome() {
		if (_usuario == null) return null; //evita NullPointerException 
		return _usuario.getNome();
	}
	
	public String getUsuarioLogin() {
		if (_usuario == null) return null; //evita NullPointerException 
		return _usuario.getLogin();
	}

	public String getData() {
		if (_data == null) return null; //evita NullPointerException
		return _df.format(_data);
	}

	public String getTopicoNome() {
		return _topicoNome;
	}

	public String getTopicoConteudo() {
		return _topicoConteudo;
	}
	
	public void setTopicoNome(String topicoNome) {
		_topicoNome = topicoNome;
	}
	
	public void setTopicoConteudo(String topicoConteudo) {
		_topicoConteudo = topicoConteudo;
	}
}
