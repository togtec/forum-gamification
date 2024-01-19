package br.ita.fg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.ita.fg.model.Usuario;
import br.ita.fg.model.UsuarioNivel;
import br.ita.fg.model.UsuarioStatus;


public class UsuarioDAO {	
	public static void inserirUsuario(Usuario u) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {			
			String sql = "INSERT INTO usuario (login, email, nome, senha, pontos, status, nivel) VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getLogin());
			ps.setString(2, u.getEmail());
			ps.setString(3, u.getNome());
			ps.setString(4, u.getSenha());
			ps.setInt(5, u.getPontos());
			ps.setString(6, u.getStatus().toString());
			ps.setString(7, u.getNivel().toString());			
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível inserir usuário no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);			
		}
	}
		
	public static Usuario getUsuarioByLogin(String login) throws Exception {
		Usuario usuario = null;
		
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "SELECT email, nome, pontos, status, nivel FROM usuario WHERE login = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				String email = rs.getString("email");
				String nome = rs.getString("nome");				
				Integer pontos = rs.getInt("pontos");
				UsuarioStatus status = UsuarioStatus.valueOf(rs.getString("status"));
				UsuarioNivel nivel = UsuarioNivel.valueOf(rs.getString("nivel"));
				usuario = new Usuario(login, email, nome, null, pontos, status, nivel); //null = senha
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível recuperar usuário do banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}
		return usuario;
	}
	
	public static Usuario autenticarUsuario(String login, String senha) throws Exception {
		Usuario usuario = null;		
		
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "SELECT nome, status, nivel FROM usuario WHERE login = ? AND senha = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, login);
			ps.setString(2, senha);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				String nome = rs.getString("nome");				
				UsuarioStatus status = UsuarioStatus.valueOf(rs.getString("status"));
				UsuarioNivel nivel = UsuarioNivel.valueOf(rs.getString("nivel"));
				usuario = new Usuario(login, null, nome, null, null, status, nivel); //login, email, nome, senha, pontos, status, nivel 
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível autenticar usuário no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);
		}		
		return usuario;
	}	

	public static void atualizarUsuarioEmail(String email, String login) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "UPDATE usuario SET email = ? WHERE login = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, login);						
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível atualizar o e-mail do usuário no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);			
		}
	}
	
	public static void atualizarUsuarioNome(String nome, String login) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "UPDATE usuario SET nome = ? WHERE login = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, login);						
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível atualizar o nome do usuário no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);	
		}
	}
	
	public static void atualizarUsuarioSenha(String senha, String login) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "UPDATE usuario SET senha = ? WHERE login = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, senha);
			ps.setString(2, login);						
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível atualizar a senha do usuário no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);	
		}
	}
	
	public static void adicionarPontos(String login, int pontos) throws Exception {
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "UPDATE usuario SET pontos = pontos + ? WHERE login = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, pontos);
			ps.setString(2, login);
			ps.executeUpdate();
			
		} catch (Exception e) {
			String strError = "Não foi possível adicionar pontos ao usuário no banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);	
		}
	}
	
	public static List<Usuario> getUsuarioRanking() throws Exception {
		List<Usuario> usuarioRanking = new ArrayList<Usuario>();
		
		try (Connection conn = DBConnector.getConnection()) {
			String sql = "SELECT login, nome, pontos FROM usuario ORDER BY pontos DESC, nome ASC";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String login = rs.getString("login");
				String nome = rs.getString("nome");
				Integer pontos = rs.getInt("pontos");
				usuarioRanking.add(new Usuario(login, null, nome, null, pontos, null, null)); //login, email, nome, senha, pontos, status, nivel
			}
			
		} catch (Exception e) {
			String strError = "Não foi possível recuperar o ranking de usuários do banco de dados!";
			System.out.println(strError + " " + e.getMessage());
			throw new Exception(strError);	
		}
		return usuarioRanking;
	}
	
}
