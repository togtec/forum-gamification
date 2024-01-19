package br.ita.fg.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.ita.fg.model.Forum;
import br.ita.fg.model.Usuario;

public class ForumDAO {
	public static void inserirForum(Forum f) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {						
			String sql = "INSERT INTO forum (login, data_de_inicio, nome, descricao) VALUES (?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, f.getUsuarioLogin());
			ps.setDate(2, new Date(System.currentTimeMillis()));
			ps.setString(3, f.getForumNome());
			ps.setString(4, f.getForumDescricao());			
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível inserir fórum no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}
	}
	
	public static Forum getForumById(int id) throws Exception {
		Forum forum = null;
		
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "SELECT login, data_de_inicio, nome, descricao FROM forum WHERE id_forum = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				Usuario usuario = UsuarioDAO.getUsuarioByLogin(rs.getString("login"));
				Date dataDeInicio = rs.getDate("data_de_inicio"); 
				String forumNome = rs.getString("nome");
				String forumDescricao = rs.getString("descricao");
				forum = new Forum(id, usuario, dataDeInicio, forumNome, forumDescricao);
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível recuperar fórum do banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}
		return forum;
	}
		
	public static List<Forum> getForumList() throws Exception {
		List<Forum> forumList = new ArrayList<Forum>();
		
		try(Connection conn = DBConnector.getConnection()) {
			String sql = "SELECT id_forum, login, data_de_inicio, nome, descricao FROM forum ORDER BY data_de_inicio ASC";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id_forum");
				Usuario usuario = UsuarioDAO.getUsuarioByLogin(rs.getString("login"));
				Date dataDeInicio = rs.getDate("data_de_inicio");
				String forumNome = rs.getString("nome");
				String forumDescricao = rs.getString("descricao");
				forumList.add(new Forum(id, usuario, dataDeInicio, forumNome, forumDescricao));
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível recuperar lista de fóruns do banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}
		return forumList;
	}	
	
	public static void atualizarForum(Forum forum) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {			
			String sql = "UPDATE forum SET nome = ?, descricao = ? WHERE id_forum = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, forum.getForumNome());
			ps.setString(2, forum.getForumDescricao());
			ps.setInt(3, forum.getId());						
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível atualizar fórum no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);			
		}
	}	
}
