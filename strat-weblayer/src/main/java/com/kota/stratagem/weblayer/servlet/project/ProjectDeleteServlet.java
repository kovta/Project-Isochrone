package com.kota.stratagem.weblayer.servlet.project;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ProjectProtocol;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.project.ProjectParameter;

@WebServlet("/ProjectDelete")
public class ProjectDeleteServlet extends HttpServlet implements ProjectParameter {

	private static final long serialVersionUID = -1744019896283008330L;

	private static final Logger LOGGER = Logger.getLogger(ProjectDeleteServlet.class);

	@EJB
	private ProjectProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		LOGGER.info("Delete Project by id (" + id + ")");
		try {
			this.protocol.removeProject(Long.parseLong(id));
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
		}
		response.sendRedirect(Page.PROJECT_LIST.getUrl());
	}
	
}
