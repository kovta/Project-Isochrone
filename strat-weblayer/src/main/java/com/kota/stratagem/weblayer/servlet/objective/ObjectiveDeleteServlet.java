package com.kota.stratagem.weblayer.servlet.objective;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocol;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.objective.ObjectiveAttribute;
import com.kota.stratagem.weblayer.common.objective.ObjectiveParameter;
import com.kota.stratagem.weblayer.servlet.project.ProjectDeleteServlet;

@WebServlet("/ObjectiveDelete")
public class ObjectiveDeleteServlet extends HttpServlet implements ObjectiveParameter, ObjectiveAttribute {

	private static final long serialVersionUID = 1002816239391001731L;

	private static final Logger LOGGER = Logger.getLogger(ProjectDeleteServlet.class);

	@EJB
	private ObjectiveProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		LOGGER.info("Delete Objective by id (" + id + ")");
		try {
			this.protocol.removeObjective(Long.parseLong(id));
			request.getSession().setAttribute(ATTR_SUCCESS, "Objective deleted successfully!");
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
		}
		response.sendRedirect(Page.OBJECTIVE_LIST.getUrl());
	}

}
