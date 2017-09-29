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
					final Long objective_id = Long.parseLong(request.getParameter(OBJECTIVE));
					origin = Page.OBJECTIVE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + objective_id;
					if ((assignedTeams != null) && (assignedTeams.length != 0)) {
						LOGGER.info("Create Assignments (" + assignedTeams.length + " teams, objective: " + objective_id + ")");
						this.protocol.saveObjectiveAssignments(assignedTeams, objective_id);
					}
				} else if (this.notEmpty(request.getParameter(PROJECT))) {
					final Long project_id = Long.parseLong(request.getParameter(PROJECT));
					origin = Page.PROJECT_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + project_id;
					if ((assignedTeams != null) && (assignedTeams.length != 0)) {
						LOGGER.info("Create Assignments (" + assignedTeams.length + " teams, project: " + project_id + ")");
						this.protocol.saveProjectAssignments(assignedTeams, project_id);
					}
				} else if (this.notEmpty(request.getParameter(SUBMODULE))) {
					final Long submodule_id = Long.parseLong(request.getParameter(SUBMODULE));
					origin = Page.SUBMODULE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + submodule_id;
					if ((assignedTeams != null) && (assignedTeams.length != 0)) {
						LOGGER.info("Create Assignments (" + assignedTeams.length + " teams, submodule: " + submodule_id + ")");
						this.protocol.saveSubmoduleAssignments(assignedTeams, submodule_id);
					}
				} else if (this.notEmpty(request.getParameter(TASK))) {
					final Long task_id = Long.parseLong(request.getParameter(TASK));
					origin = Page.TASK_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + task_id;
					if ((assignedTeams != null) && (assignedTeams.length != 0)) {
						LOGGER.info("Create Assignments (" + assignedTeams.length + " teams, task: " + task_id + ")");
						this.protocol.saveTaskAssignments(assignedTeams, task_id);
					}
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

}
