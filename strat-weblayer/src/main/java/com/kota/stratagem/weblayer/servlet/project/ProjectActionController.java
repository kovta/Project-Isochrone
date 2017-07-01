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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ProjectProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.project.ProjectAttribute;
import com.kota.stratagem.weblayer.common.project.ProjectParameter;

@WebServlet("/ProjectAction")
public class ProjectActionController extends HttpServlet implements ProjectParameter, ProjectAttribute {

	private static final long serialVersionUID = -8825015852540069920L;

	private static final Logger LOGGER = Logger.getLogger(ProjectActionController.class);

	private static final String TRUE_VALUE = "1";
	private static final String NEW_PROJECT_ID_FLAG = "-1";

	@EJB
	private ProjectProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		LOGGER.info("Get Project by id (" + id + ")");
		if ((id == null) || "".equals(id)) {
			response.sendRedirect(Page.PROJECT_LIST.getUrl());
		} else {
			final boolean editFlag = TRUE_VALUE.equals(request.getParameter(EDIT_FLAG));
			ProjectRepresentor project = null;
			boolean isNew = false;
			if (NEW_PROJECT_ID_FLAG.equals(id)) {
				project = new ProjectRepresentor(null, "", "", ProjectStatusRepresentor.PROPOSED, null, false, null, null, null, null, null);
				isNew = true;
			} else {
				try {
					project = this.protocol.getProject(Long.parseLong(id));
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
				}
			}
			this.forward(request, response, project, editFlag, isNew, false);
		}
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final ProjectRepresentor project, final boolean editFlag,
			boolean isNew, boolean finishFlag) throws ServletException, IOException {
		request.setAttribute(ATTR_PROJECT, project);
		request.setAttribute(ATTR_ISNEW, isNew);
		if (finishFlag) {
			response.sendRedirect(Page.PROJECT_LIST.getUrl());
		} else {
			final RequestDispatcher view = request.getRequestDispatcher(editFlag ? Page.PROJECT_EDIT.getJspName() : Page.PROJECT_VIEW.getJspName());
			view.forward(request, response);
		}
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			Long id = null;
			if ((request.getParameter(ID) != "") && (request.getParameter(ID) != null)) {
				id = Long.parseLong(request.getParameter(ID));
			}
			final String name = request.getParameter(NAME);
			final String description = request.getParameter(DESCRIPTION);
			final ProjectStatusRepresentor status = ProjectStatusRepresentor.valueOf(request.getParameter(STATUS));
			Date deadlineTemp = null;
			try {
				final DateFormat extractionFormat = new SimpleDateFormat("MM/dd/yyyy");
				if (request.getParameter(DEADLINE) != "") {
					deadlineTemp = extractionFormat.parse(request.getParameter(DEADLINE));
				}
				LOGGER.info(deadlineTemp + ", input: " + request.getParameter(DEADLINE));
			} catch (final ParseException e) {
				LOGGER.info("Failed attempt to modify Project : (" + name + ") because of unusable date format");
				request.getSession().setAttribute(ATTR_ERROR, "Incorrect date format");
				final ProjectRepresentor project = new ProjectRepresentor(name, description, status, null, false, null, null, null, null, null);
				this.forward(request, response, project, false, false, true);
			}
			final Date deadline = deadlineTemp;
			final Boolean confidentiality = request.getParameter(CONFIDENTIALITY).equals("1") ? true : false;
			LOGGER.info("Extracted confidentiality: " + request.getParameter(CONFIDENTIALITY));
			final Long objective_id = Long.parseLong(request.getParameter(PARENT_OBJECTIVE));
			if ((name == null) || "".equals(name)) {
				LOGGER.info("Failed attempt to modify Project : (" + name + ")");
				request.getSession().setAttribute(ATTR_ERROR, "Objective name required");
				// new attributes must be requested
				final ProjectRepresentor project = new ProjectRepresentor(name, description, status, deadline, confidentiality, null, null, null, null, null);
				this.forward(request, response, project, false, false, true);
			} else {
				ProjectRepresentor project = null;
				try {
					LOGGER.info(id == null ? "Create Project : (" + name + ")" : "Update Project : (" + id + ")");
					project = this.protocol.saveProject(id, name, description, status, deadline, confidentiality, request.getUserPrincipal().getName(), null,
							null, null, null, objective_id);
					request.getSession().setAttribute(ATTR_SUCCESS, id == null ? "Project created succesfully!" : "Project updated successfully!");
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
					request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
				}
				this.forward(request, response, project, false, false, true);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		/*
		 * Long id = null; if ((request.getParameter(ID) != "") && (request.getParameter(ID) != null)) { id =
		 * Long.parseLong(request.getParameter(ID)); } final String name = request.getParameter(NAME); final String
		 * description = request.getParameter(DESCRIPTION); final ProjectStatusRepresentor status =
		 * ProjectStatusRepresentor.valueOf(request.getParameter(STATUS)); final Boolean visible =
		 * Boolean.valueOf(request.getParameter(VISIBLE)); if ((name == null) || "".equals(name)) {
		 * LOGGER.info("Failed attempt to modify project : (" + name + ")"); final ProjectRepresentor project = new
		 * ProjectRepresentor(id, name, description, status, null, visible, null, null, null, null, null);
		 * this.forward(request, response, project, true, true, false); } else { ProjectRepresentor project = null; try
		 * { LOGGER.info(id == null ? "Create project : (" + name + ")" : "Update project : (" + id + ")"); project =
		 * this.protocol.saveProject(id, name, description, status, null, visible, null, null, null, null, null, null);
		 * } catch (final AdaptorException e) { LOGGER.error(e, e); } this.forward(request, response, project, false,
		 * false, true); } } catch (final Exception e) { e.printStackTrace(); }
		 */
	}

}
