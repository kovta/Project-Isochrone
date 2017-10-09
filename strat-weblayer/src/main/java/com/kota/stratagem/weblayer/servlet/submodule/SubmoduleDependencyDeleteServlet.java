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
import com.kota.stratagem.weblayer.common.submodule.SubmoduleAttribute;
import com.kota.stratagem.weblayer.common.submodule.SubmoduleParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/SubmoduleDependencyDelete")
public class SubmoduleDependencyDeleteServlet extends AbstractRefinerServlet implements SubmoduleParameter, SubmoduleAttribute {

	private static final long serialVersionUID = 9117224863670785070L;

	private static final Logger LOGGER = Logger.getLogger(SubmoduleDependencyDeleteServlet.class);

	@Inject
	SubmoduleProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String dependency = request.getParameter(DEPENDENCY), dependant = request.getParameter(DEPENDANT);
		String origin = "";
		if (!this.notEmpty(dependency) || !this.notEmpty(dependant) || !this.isNumeric(dependency) || !this.isNumeric(dependant)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			try {
				if (this.isNumeric(request.getParameter(SUBMODULE))) {
					final Long task_id = Long.parseLong(request.getParameter(SUBMODULE));
					origin = Page.SUBMODULE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + task_id;
					LOGGER.info("Remove Submodule dependency (dependency: " + dependency + ", dependant: " + task_id + ")");
					this.protocol.removeSubmoduleDependency(Long.parseLong(dependency), Long.parseLong(dependant));
				} else {
					response.sendRedirect(Page.ERROR.getUrl());
				}
				request.getSession().setAttribute(ATTR_SUCCESS, "Dependency removed successfully!");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
		}
		response.sendRedirect(origin);
	}
}
