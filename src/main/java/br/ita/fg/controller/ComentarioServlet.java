package br.ita.fg.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ita.fg.dao.ComentarioDAO;
import br.ita.fg.dao.TopicoDAO;
import br.ita.fg.dao.UsuarioDAO;
import br.ita.fg.model.Comentario;
import br.ita.fg.model.Topico;
import br.ita.fg.model.Usuario;

@WebServlet(urlPatterns = {"/comentarioListByTopico", "/inserirComentario", "/editarComentario", "/atualizarComentario"})
public class ComentarioServlet extends HttpServlet {
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
			case "/comentarioListByTopico": exibirComentarioListByTopico(request, response); break;
			case "/inserirComentario": inserirComentario(request, response); break;
			case "/editarComentario": exibirComentarioEditarForm(request, response); break;
			case "/atualizarComentario": atualizarComentario(request, response); break;
		}
	}
	
	private void exibirComentarioListByTopico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Topico topico = TopicoDAO.getTopicoById(Integer.parseInt(request.getParameter("topicoId")));
			List<Comentario> comentarioList = ComentarioDAO.getComentarioListByTopico(Integer.parseInt(request.getParameter("topicoId")));
			request.setAttribute("topico", topico);
			request.setAttribute("comentarioList", comentarioList);
			request.getRequestDispatcher("comentarioListByTopico.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	}
	
	private void inserirComentario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		try {
			Boolean usuarioLogado = (Boolean) request.getSession().getAttribute("usuarioLogado");
			if (usuarioLogado == null) {
				request.setAttribute("erroUsuarioDeslogado", true);
				request.getRequestDispatcher("loginForm.jsp").forward(request, response);
			} else if (request.getParameter("comentario").equals("")) {
				request.setAttribute("errorMsgComentario", "Campo de Preenchimento Obrigatório!");
				request.getRequestDispatcher("comentarioListByTopico").forward(request, response);
			} else {
				Topico topico = TopicoDAO.getTopicoById(Integer.parseInt(request.getParameter("topicoId")));
				Usuario usuario = UsuarioDAO.getUsuarioByLogin(request.getSession().getAttribute("loginUsuario").toString());
				String comentario = request.getParameter("comentario");
				Comentario c = new Comentario(null, topico, usuario, null, comentario); //id, topico, usuario, dataHora, comentario
				
				ComentarioDAO.inserirComentario(c);
				UsuarioDAO.adicionarPontos(c.getUsuarioLogin(), 3);
				request.getRequestDispatcher("comentarioListByTopico").forward(request, response);
			}						
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	}
	
	private void exibirComentarioEditarForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("comentarioId"));
			Comentario comentario = ComentarioDAO.getComentarioById(id);
			request.setAttribute("comentario", comentario);
			request.getRequestDispatcher("comentarioEditarForm.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);			
		}
	}
	
	private void atualizarComentario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (request.getParameter("comentario").equals("")) {
				request.setAttribute("erroDePreenchimento", true);
				request.setAttribute("errorMsgComentario", "Campo de Preenchimento Obrigatório!");
				request.getRequestDispatcher("comentarioEditarForm.jsp").forward(request, response);
			} else {
				Comentario c = ComentarioDAO.getComentarioById(Integer.parseInt(request.getParameter("comentarioId")));
				c.setComentario(request.getParameter("comentario"));
				ComentarioDAO.atualizarComentario(c);
				request.getRequestDispatcher("comentarioListByTopico").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);			
		}
	}
}
