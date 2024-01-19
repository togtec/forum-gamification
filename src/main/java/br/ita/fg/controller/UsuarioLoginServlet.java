package br.ita.fg.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ita.fg.dao.UsuarioDAO;
import br.ita.fg.model.Usuario;
import br.ita.fg.model.UsuarioStatus;


@WebServlet(urlPatterns = {"/controleDeAcesso", "/administradorOnly", "/login"})
public class UsuarioLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		String action = request.getServletPath();
			
		switch (action) {
			case "/controleDeAcesso": controleDeAcesso(request, response); break;
			case "/administradorOnly": administradorOnly(request, response); break;
			case "/login": login(request, response); break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void controleDeAcesso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (usuarioLogado(request, response)) 
			logout(request, response);
		else
			entrar(request, response);
	}
	
	private void administradorOnly (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("exception", new Exception("Acesso negado! Requer nível Administrador!"));
		request.getRequestDispatcher("erro.jsp").forward(request, response);
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String login = request.getParameter("login");
    	String senha = request.getParameter("senha");
    	
    	try {
			Usuario usuario = UsuarioDAO.autenticarUsuario(login, senha);
			if (usuario == null) {
				request.setAttribute("usuarioOuSenhaInvalido", true);
				request.getRequestDispatcher("loginForm.jsp").forward(request, response);
			} else if (usuario.getStatus() == UsuarioStatus.BLOCKED) {
				request.setAttribute("exception", new Exception("Usuário Bloqueado!<br>Favor entrar em contato com o Administrador do Sistema!"));
				request.getRequestDispatcher("erro.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("usuarioLogado", true);
		    	request.getSession().setAttribute("loginUsuario", usuario.getLogin());
				request.getSession().setAttribute("nomeUsuario", usuario.getNome());
				request.getSession().setAttribute("nivelUsuario", usuario.getNivel().getDescNivel());
				request.getSession().setAttribute("statusUsuario", usuario.getStatus().getDescStatus());
				request.getRequestDispatcher("forumList").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	}
		
	private boolean usuarioLogado(HttpServletRequest request, HttpServletResponse response) {
		Boolean usuarioLogado = (Boolean) request.getSession().getAttribute("usuarioLogado");
		if (usuarioLogado == null) return false;
		return usuarioLogado;
	}
	
	private void entrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("loginForm.jsp").forward(request, response);
	}	
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("usuarioLogado", null);
    	request.getSession().setAttribute("loginUsuario", null);
		request.getSession().setAttribute("nomeUsuario", null);
		request.getSession().setAttribute("nivelUsuario", null);
		request.getSession().setAttribute("statusUsuario", null);
		request.getRequestDispatcher("fim.jsp").forward(request, response);
	}
}
