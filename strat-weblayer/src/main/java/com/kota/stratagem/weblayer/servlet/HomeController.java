package com.kota.stratagem.weblayer.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kota.stratagem.weblayer.common.Page;

@WebServlet("/Home")
public class HomeController extends AbstractRefinerServlet {

	private static final long serialVersionUID = 3218457465214789896L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getUserPrincipal() != null) {
			this.setUserAttributes(request);
		}
		final RequestDispatcher view = request.getRequestDispatcher(Page.HOME.getJspName());
		view.forward(request, response);
	}

}
