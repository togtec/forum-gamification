package br.ita.fg.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ita.fg.dao.UsuarioDAO;

@WebServlet("/atualizarUsuarioNome")
public class UsuarioRedefinirNomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		
		try {
			String nome = request.getParameter("nome");
			if (nome == null || nome.equals("")) {
				request.setAttribute("errorMsgNome", "Campo de Preenchimento Obrigatório!");
				request.setAttribute("erroDePreenchimento", true);
				request.getRequestDispatcher("usuarioEditarNomeForm.jsp").forward(request, response);
			} else {
				UsuarioDAO.atualizarUsuarioNome(nome, request.getSession().getAttribute("loginUsuario").toString());
				request.getRequestDispatcher("exibirUsuarioConta").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	}
}
