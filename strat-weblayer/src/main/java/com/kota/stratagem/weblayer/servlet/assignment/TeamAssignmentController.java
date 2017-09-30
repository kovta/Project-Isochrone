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

@WebServlet("/TeamAssignment")
public class TeamAssignmentController extends AbstractRefinerServlet implements AssignmentParameter, AssignmentAttribute {

	private static final long serialVersionUID = 4734343452060450720L;

	private static final Logger LOGGER = Logger.getLogger(TeamAssignmentController.class);

	@Inject
	private TeamAssignmentProtocol protocol;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			final Long[] assignedTeams = new Long[request.getParameterValues(ASSIGNMENTS).length];
			String origin = Page.ERROR.getUrl();
			try {
				for (int i = 0; i < request.getParameterValues(ASSIGNMENTS).length; i++) {
					assignedTeams[i] = Long.parseLong(request.getParameterValues(ASSIGNMENTS)[i]);
				}
				if (this.notEmpty(request.getParameter(OBJECTIVE))) {
					origin = this.saveAssignments(request, assignedTeams, OBJECTIVE, Page.OBJECTIVE_VIEW.getUrl());
				} else if (this.notEmpty(request.getParameter(PROJECT))) {
					origin = this.saveAssignments(request, assignedTeams, PROJECT, Page.PROJECT_VIEW.getUrl());
				} else if (this.notEmpty(request.getParameter(SUBMODULE))) {
					origin = this.saveAssignments(request, assignedTeams, SUBMODULE, Page.SUBMODULE_VIEW.getUrl());
				} else if (this.notEmpty(request.getParameter(TASK))) {
					origin = this.saveAssignments(request, assignedTeams, TASK, Page.TASK_VIEW.getUrl());
				}
				request.getSession().setAttribute(ATTR_SUCCESS,
						((assignedTeams != null) && (assignedTeams.length != 0))
								? assignedTeams.length != 1 ? assignedTeams.length + " Assignments added successfully!" : "1 Assignment added succesfully!"
								: "No selections were made");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
			response.sendRedirect(origin);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private String saveAssignments(HttpServletRequest request, Long[] teams, String requestParameter, String url) throws AdaptorException {
		final Long subject_id = Long.parseLong(request.getParameter(requestParameter));
		if ((teams != null) && (teams.length != 0)) {
			LOGGER.info("Create Assignments (" + teams.length + " team(s), " + requestParameter + ": " + subject_id + ")");
			switch (requestParameter) {
				case OBJECTIVE:
					this.protocol.saveObjectiveAssignments(teams, subject_id);
					break;
				case PROJECT:
					this.protocol.saveProjectAssignments(teams, subject_id);
					break;
				case SUBMODULE:
					this.protocol.saveSubmoduleAssignments(teams, subject_id);
					break;
				case TASK:
					this.protocol.saveTaskAssignments(teams, subject_id);
					break;
			}
		}
		return url + GET_REQUEST_QUERY_APPENDER + subject_id;
	}

}
