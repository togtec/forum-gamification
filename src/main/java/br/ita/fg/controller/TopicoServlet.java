package br.ita.fg.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ita.fg.dao.ForumDAO;
import br.ita.fg.dao.TopicoDAO;
import br.ita.fg.dao.UsuarioDAO;
import br.ita.fg.model.Forum;
import br.ita.fg.model.Topico;
import br.ita.fg.model.Usuario;

@WebServlet(urlPatterns = {"/topicoListByForum", "/novoTopico", "/inserirTopico", "/editarTopico", "/atualizarTopico"})
public class TopicoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		String action = request.getServletPath();
		
		switch (action) {		
			case "/topicoListByForum": exibirTopicoListByForum(request, response); break;
			case "/novoTopico": exibirTopicoCadastrarForm(request, response); break;
			case "/inserirTopico": inserirTopico(request, response); break;
			case "/editarTopico": exibirTopicoEditarForm(request, response); break;
			case "/atualizarTopico": atualizarTopico(request, response); break;
		}
	}

	private void exibirTopicoListByForum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Topico> topicoList = TopicoDAO.getTopicoListByForum(Integer.parseInt(request.getParameter("forumId")));
			request.setAttribute("topicoList", topicoList);
			request.getRequestDispatcher("topicoListByForum.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	}
	
	private void exibirTopicoCadastrarForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean usuarioLogado = (Boolean) request.getSession().getAttribute("usuarioLogado");
		if (usuarioLogado == null) {
			request.setAttribute("erroUsuarioDeslogado", true);
			request.getRequestDispatcher("loginForm.jsp").forward(request, response);	
		} else {
			request.getRequestDispatcher("topicoCadastrarForm.jsp").forward(request, response);
		}
	}
	
	private void inserirTopico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (formularioIncompleto(request)) {
				request.setAttribute("erroDePreenchimento", true);
				request.getRequestDispatcher("topicoCadastrarForm.jsp").forward(request, response);
			} else {
				Forum forum = ForumDAO.getForumById(Integer.parseInt(request.getParameter("forumId")));
				Usuario usuario = UsuarioDAO.getUsuarioByLogin(request.getSession().getAttribute("loginUsuario").toString());
				String topicoNome = request.getParameter("topicoNome");
				String topicoConteudo = request.getParameter("topicoConteudo");
				Topico topico = new Topico(null, forum, usuario, null, topicoNome, topicoConteudo); //id, forum, usuario, data, topicoNome, topicoConteudo

				TopicoDAO.inserirTopico(topico);
				UsuarioDAO.adicionarPontos(topico.getUsuarioLogin(), 10);
				request.getRequestDispatcher("topicoListByForum").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	}
	
	private void exibirTopicoEditarForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("topicoId"));
			Topico topico = TopicoDAO.getTopicoById(id);
			request.setAttribute("topico", topico);
			request.getRequestDispatcher("topicoEditarForm.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);			
		}
	}
	
	private void atualizarTopico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if (formularioIncompleto(request)) {
				request.setAttribute("erroDePreenchimento", true);
				request.getRequestDispatcher("topicoEditarForm.jsp").forward(request, response);;
			} else {
				Topico topico = TopicoDAO.getTopicoById(Integer.parseInt(request.getParameter("topicoId")));
				topico.setTopicoNome(request.getParameter("topicoNome"));
				topico.setTopicoConteudo(request.getParameter("topicoConteudo"));
				TopicoDAO.atualizarTopico(topico);
				request.getRequestDispatcher("topicoListByForum").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("erro.jsp").forward(request, response);			
		}
	}
	
	private boolean formularioIncompleto(HttpServletRequest request) {
		boolean resultado = false;
		
		String topicoNome = request.getParameter("topicoNome");
		if(topicoNome==null || topicoNome.equals("")) {
			request.setAttribute("errorMsgTopicoNome","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}
		
		String topicoConteudo = request.getParameter("topicoConteudo");
		if(topicoConteudo==null || topicoConteudo.equals("")) {
			request.setAttribute("errorMsgTopicoConteudo","Campo de Preenchimento Obrigatório!");
			resultado = true;
		}		
		return resultado;
	}
}