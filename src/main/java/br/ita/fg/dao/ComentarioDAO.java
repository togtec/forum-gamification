package br.ita.fg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import br.ita.fg.model.Comentario;
import br.ita.fg.model.Topico;
import br.ita.fg.model.Usuario;

public class ComentarioDAO {
	public static void inserirComentario(Comentario c) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "INSERT INTO comentario (id_topico, login, data_hora, comentario) VALUES (?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, c.getTopicoId());
			ps.setString(2, c.getUsuarioLogin());
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setString(4, c.getComentario());
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível inserir comentário no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}
	}
	
	public static Comentario getComentarioById(int id) throws Exception {
		Comentario comentario = null;
		
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "SELECT id_topico, login, data_hora, comentario FROM comentario WHERE id_comentario = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Topico topico = TopicoDAO.getTopicoById(rs.getInt("id_topico"));
				Usuario usuario = UsuarioDAO.getUsuarioByLogin(rs.getString("login"));
				Date dataHora = new Date(rs.getTimestamp("data_hora").getTime());
				String c = rs.getString("comentario");
				comentario = new Comentario(id, topico, usuario, dataHora, c);
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível recuperar comentário do banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}		
		return comentario;
	}
	
	public static List<Comentario> getComentarioListByTopico(int topicoId) throws Exception {
		List<Comentario> comentarioList = new ArrayList<Comentario>();
		
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "SELECT id_comentario, id_topico, login, data_hora, comentario FROM comentario WHERE id_topico = ? ORDER BY data_hora ASC";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, topicoId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int comentarioId = rs.getInt("id_comentario");
				Topico topico = TopicoDAO.getTopicoById(rs.getInt("id_topico"));
				Usuario usuario = UsuarioDAO.getUsuarioByLogin(rs.getString("login"));
				Date dataHora = new Date(rs.getTimestamp("data_hora").getTime());
				String c = rs.getString("comentario");
				comentarioList.add(new Comentario(comentarioId, topico, usuario, dataHora, c));
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível recuperar lista de comentários do banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}
		return comentarioList;
	}
	
	public static void atualizarComentario(Comentario c) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "UPDATE comentario SET comentario = ? WHERE id_comentario = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getComentario());
			ps.setInt(2, c.getComentarioId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível atualizar comentário no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);			
		}
	}
}