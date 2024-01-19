package br.ita.fg.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ita.fg.dao.ForumDAO;
import br.ita.fg.dao.UsuarioDAO;
import br.ita.fg.model.Forum;
import br.ita.fg.model.Usuario;

@WebServlet(urlPatterns = {"/forumList", "/inserirForum", "/editarForum", "/atualizarForum"})
public class ForumServlet extends HttpServlet {
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
			case "/forumList": exibirIndex(request, response); break;
			case "/inserirForum": inserirForum(request, response); break;
			case "/editarForum": exibirForumEditarForm(request, response); break;
			case "/atualizarForum": atualizarForum(request, response); break;
		}
	}
	
	private void exibirIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Forum> forumList = ForumDAO.getForumList();
			request.setAttribute("forumList", forumList);
			request.getRequestDispatcher("index.jsp").forward(request, response);			
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	}
	
	private void inserirForum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {		
			if (formularioIncompleto(request)) {
				request.setAttribute("erroDePreenchimento", true);
				request.getRequestDispatcher("forumCadastrarForm.jsp").forward(request, response);			
			} else {
				Usuario usuario = UsuarioDAO.getUsuarioByLogin(request.getSession().getAttribute("loginUsuario").toString());
				String forumNome = request.getParameter("forumNome");
				String forumDescricao = request.getParameter("forumDescricao");
				Forum novoForum = new Forum(null, usuario, null, forumNome, forumDescricao); //id, usuario, dataDeInicio, forumNome, forumDescricao
				ForumDAO.inserirForum(novoForum);
				request.getRequestDispatcher("forumList").forward(request, response);
			}	
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);			
		}
	}
	
	private void exibirForumEditarForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("forumId"));
			Forum forum = ForumDAO.getForumById(id);
			request.setAttribute("forum", forum);
			request.getRequestDispatcher("forumEditarForm.jsp").forward(request, response);			
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);			
		}
	}
	
	private void atualizarForum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {		
			if (formularioIncompleto(request)) {
				request.setAttribute("erroDePreenchimento", true);
				request.getRequestDispatcher("forumEditarForm.jsp").forward(request, response);	
			} else {
				Forum forum = ForumDAO.getForumById(Integer.parseInt(request.getParameter("forumId")));
				forum.setForumNome(request.getParameter("forumNome"));
				forum.setForumDescricao(request.getParameter("forumDescricao"));
				ForumDAO.atualizarForum(forum);
				request.getRequestDispatcher("forumList").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);			
		}	
	}
	
	private boolean formularioIncompleto(HttpServletRequest request) {
		boolean resultado = false;
		
		String nomeForum = request.getParameter("forumNome");
		if(nomeForum==null || nomeForum.equals("")) {
			request.setAttribute("errorMsgForumNome","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}
		
		String descricao = request.getParameter("forumDescricao");
		if(descricao==null || descricao.equals("")) {
			request.setAttribute("errorMsgForumDescricao","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}		
		return resultado;
	}
}
