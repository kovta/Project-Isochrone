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

@WebServlet("/SubmoduleDependant")
public class SubmoduleDependantController extends AbstractRefinerServlet implements SubmoduleParameter, SubmoduleAttribute {

	private static final long serialVersionUID = -2084453344845413494L;

	private static final Logger LOGGER = Logger.getLogger(SubmoduleDependantController.class);

	@Inject
	SubmoduleProtocol protocol;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			final String[] dependants = request.getParameterValues(DEPENDANTS);
			try {
				if ((dependants != null) && (dependants.length != 0)) {
					LOGGER.info("Save Submodule Dependants (" + dependants.length + " dependants, submodule: " + request.getParameter(SUBMODULE) + ")");
					final Long[] dependencyIdentifiers = new Long[dependants.length];
					for (int i = 0; i < dependants.length; i++) {
						dependencyIdentifiers[i] = Long.parseLong(dependants[i]);
					}
					this.protocol.saveSubmoduleDependants(Long.parseLong(request.getParameter(SUBMODULE)), dependencyIdentifiers);
				}
				request.getSession().setAttribute(ATTR_SUCCESS,
						((dependants != null) && (dependants.length != 0))
								? dependants.length != 1 ? dependants.length + " Dependants added succesfully!" : "1 Dependant added succesfully!"
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