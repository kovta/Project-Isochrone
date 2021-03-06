package com.kota.stratagem.weblayer.servlet.access;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.access.LoginAttribute;

@WebServlet("/LoginError")
public class LoginErrorServlet extends HttpServlet implements LoginAttribute {

	private static final long serialVersionUID = 1599166100486735562L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String userName = request.getParameter("j_username");

		request.setAttribute(ATTR_USERNAME, userName);
		request.setAttribute(ATTR_ERROR, "Login failed");

		final RequestDispatcher view = request.getRequestDispatcher(Page.HOME.getJspName());
		view.forward(request, response);
	}

}
