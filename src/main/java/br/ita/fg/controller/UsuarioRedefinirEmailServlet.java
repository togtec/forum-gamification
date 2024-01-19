package br.ita.fg.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ita.fg.dao.UsuarioDAO;

@WebServlet("/atualizarUsuarioEmail")
public class UsuarioRedefinirEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		
		try {
			String email = request.getParameter("email");
			if (email == null || email.equals("")) {
				request.setAttribute("errorMsgEmail", "Campo de Preenchimento Obrigatório!");
				request.setAttribute("erroDePreenchimento", true);
				request.getRequestDispatcher("usuarioEditarEmailForm.jsp").forward(request, response);
			} else {
				UsuarioDAO.atualizarUsuarioEmail(email, request.getSession().getAttribute("loginUsuario").toString());
				request.getRequestDispatcher("exibirUsuarioConta").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	}
}
