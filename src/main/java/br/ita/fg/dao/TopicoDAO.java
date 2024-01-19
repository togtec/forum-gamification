package br.ita.fg.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.ita.fg.model.Forum;
import br.ita.fg.model.Topico;
import br.ita.fg.model.Usuario;

public class TopicoDAO {
	public static void inserirTopico(Topico topico) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "INSERT INTO topico (id_forum, login, data, nome, conteudo) VALUES(?, ?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);			
			ps.setInt(1, topico.getForumId());
			ps.setString(2, topico.getUsuarioLogin());
			ps.setDate(3, new Date(System.currentTimeMillis()));
			ps.setString(4, topico.getTopicoNome());
			ps.setString(5, topico.getTopicoConteudo());
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível inserir tópico no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}
	}
	
	public static Topico getTopicoById(int id) throws Exception {
		Topico topico = null;
		
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "SELECT id_forum, login, data, nome, conteudo FROM topico WHERE id_topico = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Forum forum = ForumDAO.getForumById(rs.getInt("id_forum"));
				Usuario usuario = UsuarioDAO.getUsuarioByLogin(rs.getString("login"));
				Date data = rs.getDate("data");
				String topicoNome = rs.getString("nome");
				String topicoConteudo = rs.getString("conteudo");
				topico = new Topico(id, forum, usuario, data, topicoNome, topicoConteudo);
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível recuperar tópico do banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}		
		return topico;
	}
	
	public static List<Topico> getTopicoListByForum(int forumId) throws Exception {
		List<Topico> topicoList = new ArrayList<Topico>();
		
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "SELECT id_topico, login, data, nome, conteudo FROM topico WHERE id_forum = ? ORDER BY data ASC";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, forumId);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int topicoId = rs.getInt("id_topico");
				Forum forum = ForumDAO.getForumById(forumId);
				Usuario usuario = UsuarioDAO.getUsuarioByLogin(rs.getString("login"));
				Date data = rs.getDate("data");
				String topicoNome = rs.getString("nome");
				String topicoConteudo = rs.getString("conteudo");
				topicoList.add(new Topico(topicoId, forum, usuario, data, topicoNome, topicoConteudo));
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível recuperar lista de tópicos do banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}
		return topicoList;
	}
	
	public static List<Topico> getTopicoListByUsuario(String login) throws Exception {
		List<Topico> topicoList = new ArrayList<Topico>();
		
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "SELECT id_topico, id_forum, data, nome, conteudo FROM topico WHERE login = ? ORDER BY data ASC";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int topicoId = rs.getInt("id_topico");
				Forum forum = ForumDAO.getForumById(rs.getInt("id_forum"));
				Usuario usuario = UsuarioDAO.getUsuarioByLogin(login);
				Date data = rs.getDate("data");
				String topicoNome = rs.getString("nome");
				String topicoConteudo = rs.getString("conteudo");
				topicoList.add(new Topico(topicoId, forum, usuario, data, topicoNome, topicoConteudo));
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível recuperar lista de tópicos do banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}
		return topicoList;
	}

	public static void atualizarTopico(Topico topico) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "UPDATE topico SET nome = ?, conteudo = ? WHERE id_topico = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, topico.getTopicoNome());
			ps.setString(2, topico.getTopicoConteudo());
			ps.setInt(3, topico.getTopicoId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível atualizar tópico no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);			
		}
	}
}