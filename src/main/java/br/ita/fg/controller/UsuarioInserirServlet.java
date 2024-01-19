package br.ita.fg.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ita.fg.dao.UsuarioDAO;
import br.ita.fg.model.Usuario;
import br.ita.fg.model.UsuarioNivel;
import br.ita.fg.model.UsuarioStatus;

@WebServlet("/inserirUsuario")
public class UsuarioInserirServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		
		try {		
			if (usuarioCadastrarFormIncompleto(request) || senhaEconfirmarSenhaDiferentes(request) || loginJaEstaCadastradoNoSistema(request)) {
				request.setAttribute("erroDePreenchimento", true);				
				request.getRequestDispatcher("usuarioCadastrarForm.jsp").forward(request, response);				
			} else {
				String login = request.getParameter("login");
				String email = request.getParameter("email");
				String nome = request.getParameter("nome");
				String senha = request.getParameter("senha");
				Integer pontos = 0;
				UsuarioStatus status = UsuarioStatus.ACTIVE;
				UsuarioNivel nivel = UsuarioNivel.USER;
				UsuarioDAO.inserirUsuario(new Usuario(login, email, nome, senha, pontos, status, nivel));
				request.getRequestDispatcher("usuarioCadastrado.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);			
		}		
	}

	private boolean usuarioCadastrarFormIncompleto(HttpServletRequest request) {
		boolean resultado = false;
		
		String login = request.getParameter("login");
		if(login==null || login.equals("")) {
			request.setAttribute("errorMsgLogin","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}
			
		String email = request.getParameter("email");
		if(email==null || email.equals("")) {
			request.setAttribute("errorMsgEmail","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}
				
		String nome = request.getParameter("nome");
		if(nome==null || nome.equals("")) {
			request.setAttribute("errorMsgNome","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}
				
		String senha = request.getParameter("senha");
		if(senha==null || senha.equals("")) {
			request.setAttribute("errorMsgSenha","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}	
		return resultado;
	}

	private boolean senhaEconfirmarSenhaDiferentes(HttpServletRequest request) {
		if ( !request.getParameter("senha").equals(request.getParameter("confirmarSenha")) ) {
			request.setAttribute("errorMsgSenha","Senha e Confirmar Senha precisam ser iguais!");
			request.setAttribute("errorMsgConfirmarSenha","Senha e Confirmar Senha precisam ser iguais!");
			return true;
		}			
		return false;
	}
	
	private boolean loginJaEstaCadastradoNoSistema(HttpServletRequest request) throws Exception {
		Usuario usuario = UsuarioDAO.getUsuarioByLogin(request.getParameter("login"));
		if (usuario == null) return false;
		
		request.setAttribute("errorMsgLogin", "Seu login já está cadastrado em nosso sistema!");
		return true;
	}	
}
