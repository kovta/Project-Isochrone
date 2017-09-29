package com.kota.stratagem.weblayer.servlet.assignment;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.TeamAssignmentProtocol;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.assignment.AssignmentAttribute;
import com.kota.stratagem.weblayer.common.assignment.AssignmentParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/TeamAssignmentDelete")
public class TeamAssignmentDeleteServlet extends AbstractRefinerServlet implements AssignmentParameter, AssignmentAttribute {

	private static final long serialVersionUID = -5778619091334827766L;

	private static final Logger LOGGER = Logger.getLogger(TeamAssignmentDeleteServlet.class);

	@Inject
	private TeamAssignmentProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		String origin = "";
		if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			try {
				if ((request.getParameter(OBJECTIVE) != "") && (request.getParameter(OBJECTIVE) != null)) {
					origin = this.removeAssignment(request, response, id, OBJECTIVE, Page.OBJECTIVE_VIEW.getUrl());
				} else if ((request.getParameter(PROJECT) != "") && (request.getParameter(PROJECT) != null)) {
					origin = this.removeAssignment(request, response, id, PROJECT, Page.PROJECT_VIEW.getUrl());
				} else if ((request.getParameter(SUBMODULE) != "") && (request.getParameter(SUBMODULE) != null)) {
					origin = this.removeAssignment(request, response, id, SUBMODULE, Page.SUBMODULE_VIEW.getUrl());
				} else if ((request.getParameter(TASK) != "") && (request.getParameter(TASK) != null)) {
					origin = this.removeAssignment(request, response, id, TASK, Page.TASK_VIEW.getUrl());
				}
				request.getSession().setAttribute(ATTR_SUCCESS, "Assignment removed successfully!");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
		}
		response.sendRedirect(origin);
	}

	private String removeAssignment(HttpServletRequest request, HttpServletResponse response, String id, String requestParameter, String url)
			throws NumberFormatException, AdaptorException {
		if (this.isNumeric(request.getParameter(requestParameter))) {
			final Long subject_id = Long.parseLong(request.getParameter(requestParameter));
			LOGGER.info("Remove Assignment (id: " + id + ", " + requestParameter + " : " + subject_id + ")");
			switch (requestParameter) {
				case OBJECTIVE:
					this.protocol.removeObjectiveAssignment(Long.parseLong(id));
					break;
				case PROJECT:
					this.protocol.removeProjectAssignment(Long.parseLong(id));
					break;
				case SUBMODULE:
					this.protocol.removeSubmoduleAssignment(Long.parseLong(id));
					break;
				case TASK:
					this.protocol.removeTaskAssignment(Long.parseLong(id));
					break;
			}
			return url + GET_REQUEST_QUERY_APPENDER + subject_id;
		} else {
			return Page.ERROR.getUrl();
		}
	}

}
