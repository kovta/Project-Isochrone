package com.kota.stratagem.weblayer.servlet.submodule;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.SubmoduleProtocol;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.project.ProjectAttribute;
import com.kota.stratagem.weblayer.common.project.ProjectParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/SubmoduleDelete")
public class SubmoduleDeleteServlet extends AbstractRefinerServlet implements ProjectParameter, ProjectAttribute {

	private static final long serialVersionUID = -1744019896283008330L;

	private static final Logger LOGGER = Logger.getLogger(SubmoduleDeleteServlet.class);

	@Inject
	private SubmoduleProtocol protocol;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		String returnPoint = "";
		LOGGER.info("Delete Submodule by id (" + id + ")");
		try {
			if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
				response.sendRedirect(Page.ERROR.getUrl());
			} else {
				returnPoint = Page.PROJECT_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + this.protocol.getSubmodule(Long.parseLong(id)).getProject().getId();
				this.protocol.removeSubmodule(Long.parseLong(id));
				request.getSession().setAttribute(ATTR_SUCCESS, "Submodule deleted successfully!");
				response.sendRedirect(returnPoint);
			}
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
		}
	}

}