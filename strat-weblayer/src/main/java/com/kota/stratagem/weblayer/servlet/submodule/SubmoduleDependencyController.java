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

@WebServlet("/SubmoduleDependency")
public class SubmoduleDependencyController extends AbstractRefinerServlet implements SubmoduleParameter, SubmoduleAttribute {

	private static final long serialVersionUID = -2084453344845413494L;

	private static final Logger LOGGER = Logger.getLogger(SubmoduleDependencyController.class);

	@Inject
	SubmoduleProtocol protocol;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			final String[] dependencies = request.getParameterValues(DEPENDENCIES);
			try {
				if ((dependencies != null) && (dependencies.length != 0)) {
					LOGGER.info("Save Submodule Dependecies (" + dependencies.length + " dependecies, submodule: " + request.getParameter(SUBMODULE) + ")");
					final Long[] dependencyIdentifiers = new Long[dependencies.length];
					for (int i = 0; i < dependencies.length; i++) {
						dependencyIdentifiers[i] = Long.parseLong(dependencies[i]);
					}
					this.protocol.saveSubmoduleDependencies(Long.parseLong(request.getParameter(SUBMODULE)), dependencyIdentifiers);
				}
				request.getSession().setAttribute(ATTR_SUCCESS,
						((dependencies != null) && (dependencies.length != 0))
								? dependencies.length != 1 ? dependencies.length + " Dependencies added succesfully!" : "1 Dependency added succesfully!"
								: "No selections were made");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
			response.sendRedirect(Page.SUBMODULE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + request.getParameter(SUBMODULE));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}