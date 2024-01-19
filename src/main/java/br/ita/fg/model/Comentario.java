package br.ita.fg.model;

import java.sql.Date;
import java.text.DateFormat;

public class Comentario {
	private static DateFormat _df;
	private Integer _id;
	private Topico _topico;
	private Usuario _usuario;
	private Date _dataHora;
	private String _comentario;
	
	static {
		_df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
	}
	
	public Comentario(Integer id, Topico topico, Usuario usuario, Date dataHora, String comentario) {
		_id = id;
		_topico = topico;
		_usuario = usuario;
		_dataHora = dataHora;
		_comentario = comentario;
	}
	
	public Integer getComentarioId() {
		return _id;
	}
	
	public String getComentario() {
		return _comentario;
	}
	
	public Integer getForumId() {
		if (_topico == null) return null; //evita NullPointerException 
		return _topico.getForumId();
	}

	public String getForumNome() {
		if (_topico == null) return null; //evita NullPointerException
		return _topico.getForumNome();
	}
	
	public Integer getTopicoId() {
		if (_topico == null) return null; //evita NullPointerException
		return _topico.getTopicoId();
	}
	
	public String getTopicoNome() {
		if (_topico == null) return null; //evita NullPointerException
		return _topico.getTopicoNome();
	}
	
	public String getUsuarioLogin() {
		if (_usuario == null) return null; //evita NullPointerException
		return _usuario.getLogin();
	}
	
	public String getUsuarioNome() {
		if (_usuario == null) return null; //evita NullPointerException
		return _usuario.getNome();
	}
	
	public String getDataHora() {
		if (_dataHora == null) return null; //evita NullPointerException
 		return _df.format(_dataHora);
	}
	
	public void setComentario(String comentario) {
		_comentario = comentario;
	}
}