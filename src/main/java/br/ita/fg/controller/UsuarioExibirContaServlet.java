package br.ita.fg.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ita.fg.dao.UsuarioDAO;
import br.ita.fg.model.Usuario;

@WebServlet("/exibirUsuarioConta")
public class UsuarioExibirContaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
	
		try {
			Usuario usuario = UsuarioDAO.getUsuarioByLogin(request.getSession().getAttribute("loginUsuario").toString());
			request.setAttribute("usuario", usuario);
			request.getRequestDispatcher("usuarioConta.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	}		
}
