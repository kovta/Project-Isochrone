package com.kota.stratagem.weblayer.servlet.access;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.access.LoginAttribute;

public class LoginServlet extends HttpServlet implements LoginAttribute {

	private static final long serialVersionUID = -7675065664601612546L;

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(ATTR_USERNAME, "");
		request.setAttribute(ATTR_ERROR, "");
		final RequestDispatcher view = request.getRequestDispatcher(Page.HOME.getJspName());
		view.forward(request, response);
	}
}
