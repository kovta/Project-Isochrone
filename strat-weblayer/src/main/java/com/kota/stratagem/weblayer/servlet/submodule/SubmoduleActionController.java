package com.kota.stratagem.weblayer.servlet.submodule;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.AppUserProtocol;
import com.kota.stratagem.ejbservice.protocol.SubmoduleProtocol;
import com.kota.stratagem.ejbservice.protocol.TeamProtocol;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.submodule.SubmoduleAttribute;
import com.kota.stratagem.weblayer.common.submodule.SubmoduleParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/Submodule")
public class SubmoduleActionController extends AbstractRefinerServlet implements SubmoduleParameter, SubmoduleAttribute {

	private static final long serialVersionUID = 4167163197164098475L;

	private static final Logger LOGGER = Logger.getLogger(SubmoduleActionController.class);

	@EJB
	private SubmoduleProtocol submoduleProtocol;

	@EJB
	private AppUserProtocol appUserProtocol;

	@EJB
	private TeamProtocol teamProtocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get Submodule by id (" + request.getParameter(ID) + ")");
		this.setUserAttributes(request);
		final String id = request.getParameter(ID);
		if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			final boolean editFlag = TRUE_VALUE.equals(request.getParameter(EDIT_FLAG));
			SubmoduleRepresentor submodule = null;
			boolean errorFlag = false;
			try {
				submodule = this.submoduleProtocol.getSubmodule(Long.parseLong(id));
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				errorFlag = true;
			}
			this.forward(request, response, submodule, editFlag, null, errorFlag);
		}
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final SubmoduleRepresentor submodule, final boolean editFlag,
			String returnPoint, boolean errorFlag) throws ServletException, IOException {
		boolean assignmentError = false;
		if (!errorFlag) {
			try {
				request.setAttribute(ATTR_ASSIGNABLE_USERS, this.appUserProtocol.getAssignableAppUserClusters(submodule));
				request.setAttribute(ATTR_ASSIGNABLE_TEAMS, this.teamProtocol.getAssignableTeams(submodule));
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				assignmentError = true;
			}
		}
		request.setAttribute(ATTR_SUBMODULE, submodule);
		if (errorFlag || assignmentError) {
			final RequestDispatcher view = request.getRequestDispatcher(Page.ERROR.getJspName());
			view.forward(request, response);
		} else if (returnPoint != null) {
			response.sendRedirect(returnPoint);
		} else {
			final RequestDispatcher view = request.getRequestDispatcher(editFlag ? Page.SUBMODULE_EDIT.getJspName() : Page.SUBMODULE_VIEW.getJspName());
			view.forward(request, response);
		}
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			Long id = null;
			Long project_id = null;
			if ((request.getParameter(ID) != "") && (request.getParameter(ID) != null)) {
				id = Long.parseLong(request.getParameter(ID));
			} else {
				project_id = Long.parseLong(request.getParameter(PARENT_PROJECT));
			}
			final String returnPoint = (id == null ? Page.PROJECT_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + project_id
					: Page.SUBMODULE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + id);
			final String name = request.getParameter(NAME);
			final String description = request.getParameter(DESCRIPTION);
			Date deadlineTemp = null;
			try {
				final DateFormat extractionFormat = new SimpleDateFormat("MM/dd/yyyy");
				if (request.getParameter(DEADLINE) != "") {
					deadlineTemp = extractionFormat.parse(request.getParameter(DEADLINE));
				}
			} catch (final ParseException e) {
				LOGGER.info("Failed attempt to modify Submodule : (" + name + ") because of unusable date format");
				request.getSession().setAttribute(ATTR_ERROR, "Incorrect date format");
				final SubmoduleRepresentor submodule = new SubmoduleRepresentor(name, description, null, null);
				this.forward(request, response, submodule, false, returnPoint + GET_REQUEST_QUERY_EDIT_PARAMETER + TRUE_VALUE, false);
			}
			final Date deadline = deadlineTemp;
			if ((name == null) || "".equals(name)) {
				LOGGER.info("Failed attempt to modify Submodule : (" + name + ")");
				request.getSession().setAttribute(ATTR_ERROR, "Submodule name required");
				final SubmoduleRepresentor submodule = new SubmoduleRepresentor(name, description, deadline, null);
				this.forward(request, response, submodule, false, returnPoint + GET_REQUEST_QUERY_EDIT_PARAMETER + TRUE_VALUE, false);
			} else {
				SubmoduleRepresentor submodule = null;
				try {
					LOGGER.info(id == null ? "Create Submodule : (" + name + ")" : "Update Submodule : (" + id + ")");
					submodule = this.submoduleProtocol.saveSubmodule(id, name, description, deadline, request.getUserPrincipal().getName(), project_id);
					request.getSession().setAttribute(ATTR_SUCCESS, id == null ? "Submodule created successfully!" : "Submodule updated successfully!");
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
					request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
				}
				this.forward(request, response, submodule, false, returnPoint, false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
