package br.ita.fg.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ita.fg.dao.UsuarioDAO;
import br.ita.fg.model.Usuario;

@WebServlet(urlPatterns = {"/autenticarUsuario", "/atualizarUsuarioSenha"})
public class UsuarioRedefinirSenhaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		String action = request.getServletPath();
		
		switch (action) {
			case "/autenticarUsuario": autenticarUsuario(request, response); break;
			case "/atualizarUsuarioSenha": atualizarUsuarioSenha(request, response); break;
		}
	}
	
	private void autenticarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (usuarioConfirmarIdentidadeFormIncompleto(request)) {
				request.setAttribute("erroDePreenchimento", true);
				request.getRequestDispatcher("usuarioConfirmarIdentidadeForm.jsp").forward(request, response);
				return;
			}  
			
			String login = request.getParameter("login");
			String senhaAtual = request.getParameter("senhaAtual");
			Usuario usuario = UsuarioDAO.autenticarUsuario(login, senhaAtual);
			if (usuario == null) {
				request.setAttribute("usuarioOuSenhaInvalido", true);
				request.getRequestDispatcher("usuarioConfirmarIdentidadeForm.jsp").forward(request, response);
			} else {
				request.setAttribute("usuarioAutenticado", true);
				request.getRequestDispatcher("usuarioRedefinirSenhaForm.jsp").forward(request, response);
			}			
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	}

	private boolean usuarioConfirmarIdentidadeFormIncompleto(HttpServletRequest request) {
		boolean resultado = false;
		
		String login = request.getParameter("login");
		if(login==null || login.equals("")) {
			request.setAttribute("errorMsgLogin","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}
		
		String senha = request.getParameter("senhaAtual");
		if(senha==null || senha.equals("")) {
			request.setAttribute("errorMsgSenhaAtual","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}		
		return resultado;
	}
	
	private void atualizarUsuarioSenha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String login = request.getParameter("login");
			String senhaAtual = request.getParameter("senhaAtual");
			Usuario usuario = UsuarioDAO.autenticarUsuario(login, senhaAtual);
			if (usuario == null) {
				request.setAttribute("usuarioAutenticado", false);
				request.getRequestDispatcher("usuarioRedefinirSenhaForm.jsp").forward(request, response);
				return;
			} else {			
				request.setAttribute("usuarioAutenticado", true);
			}
			
			if (usuarioRedefinirSenhaFormIncompleto(request)) {
				request.setAttribute("erroDePreenchimento", true);
				request.getRequestDispatcher("usuarioRedefinirSenhaForm.jsp").forward(request, response);
				return;
			}
						
			if(novaSenhaEconfirmarNovaSenhaDiferentes(request)) {			
				request.setAttribute("erroDePreenchimento", true);
				request.getRequestDispatcher("usuarioRedefinirSenhaForm.jsp").forward(request, response);
			} else {
				UsuarioDAO.atualizarUsuarioSenha(request.getParameter("novaSenha"), request.getParameter("login"));
				request.getRequestDispatcher("usuarioSenhaAtualizada.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);	
		}	
	}
	
	private boolean usuarioRedefinirSenhaFormIncompleto(HttpServletRequest request) {
		boolean resultado = false;
		
		String novaSenha = request.getParameter("novaSenha");
		if(novaSenha==null || novaSenha.equals("")) {
			request.setAttribute("errorMsgNovaSenha","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}		
		return resultado;
	}
	
	private boolean novaSenhaEconfirmarNovaSenhaDiferentes(HttpServletRequest request) {
		if( !request.getParameter("novaSenha").equals(request.getParameter("confirmarNovaSenha")) ) {
			request.setAttribute("errorMsgNovaSenha", "Nova Senha e Confirmar Nova Senha precisam ser iguais!");
			request.setAttribute("errorMsgConfirmarNovaSenha", "Nova Senha e Confirmar Nova Senha precisam ser iguais!");
			return true;
		}
		return false;
	}	
}
