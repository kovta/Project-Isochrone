package com.kota.stratagem.weblayer.servlet.project;

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
import com.kota.stratagem.ejbservice.protocol.ProjectProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ProjectStatusRepresentor;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.project.ProjectAttribute;
import com.kota.stratagem.weblayer.common.project.ProjectParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/Project")
public class ProjectActionController extends AbstractRefinerServlet implements ProjectParameter, ProjectAttribute {

	private static final long serialVersionUID = -8825015852540069920L;

	private static final Logger LOGGER = Logger.getLogger(ProjectActionController.class);

	@EJB
	private ProjectProtocol projectProtocol;

	@EJB
	private AppUserProtocol appUserProtocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get Project by id (" + request.getParameter(ID) + ")");
		this.setUserAttributes(request);
		final String id = request.getParameter(ID);
		if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			final boolean editFlag = TRUE_VALUE.equals(request.getParameter(EDIT_FLAG));
			ProjectRepresentor project = null;
			boolean errorFlag = false;
			try {
				project = this.projectProtocol.getProject(Long.parseLong(id));
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				errorFlag = true;
			}
			this.forward(request, response, project, editFlag, null, errorFlag);
		}
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final ProjectRepresentor project, final boolean editFlag,
			String returnPoint, boolean errorFlag) throws ServletException, IOException {
		boolean assignmentError = false;
		if (!errorFlag) {
			try {
				request.setAttribute(ATTR_ASSIGNABLE_USERS, this.appUserProtocol.getAssignableAppUserClusters(project));
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				assignmentError = true;
			}
		}
		request.setAttribute(ATTR_PROJECT, project);
		if (errorFlag || assignmentError) {
			final RequestDispatcher view = request.getRequestDispatcher(Page.ERROR.getJspName());
			view.forward(request, response);
		} else if (returnPoint != null) {
			response.sendRedirect(returnPoint);
		} else {
			final RequestDispatcher view = request.getRequestDispatcher(editFlag ? Page.PROJECT_EDIT.getJspName() : Page.PROJECT_VIEW.getJspName());
			view.forward(request, response);
		}
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			Long id = null, objective_id = null;
			if ((request.getParameter(ID) != "") && (request.getParameter(ID) != null)) {
				id = Long.parseLong(request.getParameter(ID));
			} else {
				objective_id = Long.parseLong(request.getParameter(PARENT_OBJECTIVE));
			}
			final String returnPoint = (id == null ? Page.OBJECTIVE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + objective_id
					: Page.PROJECT_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + id);
			final String name = request.getParameter(NAME);
			final String description = request.getParameter(DESCRIPTION);
			final ProjectStatusRepresentor status = ProjectStatusRepresentor.valueOf(request.getParameter(STATUS));
			Date deadlineTemp = null;
			try {
				final DateFormat extractionFormat = new SimpleDateFormat("MM/dd/yyyy");
				if (request.getParameter(DEADLINE) != "") {
					deadlineTemp = extractionFormat.parse(request.getParameter(DEADLINE));
				}
			} catch (final ParseException e) {
				LOGGER.info("Failed attempt to modify Project : (" + name + ") because of unusable date format");
				request.getSession().setAttribute(ATTR_ERROR, "Incorrect date format");
				final ProjectRepresentor project = new ProjectRepresentor(name, description, status, null, false, null);
				this.forward(request, response, project, false, returnPoint + GET_REQUEST_QUERY_EDIT_PARAMETER + TRUE_VALUE, false);
			}
			final Date deadline = deadlineTemp;
			final Boolean confidentiality = request.getParameter(CONFIDENTIALITY).equals("1") ? true : false;
			if ((name == null) || "".equals(name)) {
				LOGGER.info("Failed attempt to modify Project : (" + name + ")");
				request.getSession().setAttribute(ATTR_ERROR, "Project name required");
				// new attributes must be requested
				final ProjectRepresentor project = new ProjectRepresentor(name, description, status, deadline, confidentiality, null);
				this.forward(request, response, project, false, returnPoint + GET_REQUEST_QUERY_EDIT_PARAMETER + TRUE_VALUE, false);
			} else {
				ProjectRepresentor project = null;
				try {
					LOGGER.info(id == null ? "Create Project : (" + name + ")" : "Update Project : (" + id + ")");
					project = this.projectProtocol.saveProject(id, name, description, status, deadline, confidentiality, request.getUserPrincipal().getName(),
							objective_id);
					request.getSession().setAttribute(ATTR_SUCCESS, id == null ? "Project created successfully!" : "Project updated successfully!");
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
					request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
				}
				this.forward(request, response, project, false, returnPoint, false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}