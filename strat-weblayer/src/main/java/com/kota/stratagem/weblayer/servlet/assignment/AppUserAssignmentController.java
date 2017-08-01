package com.kota.stratagem.weblayer.servlet.assignment;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.AppUserAssignmentProtocol;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.assignment.AssignmentAttribute;
import com.kota.stratagem.weblayer.common.assignment.AssignmentParameter;

@WebServlet("/AppUserAssignment")
public class AppUserAssignmentController extends HttpServlet implements AssignmentParameter, AssignmentAttribute {

	private static final long serialVersionUID = 4129567458088082377L;

	private static final Logger LOGGER = Logger.getLogger(AppUserAssignmentController.class);

	private static final String GET_REQUEST_QUERY_APPENDER = "?id=";

	@EJB
	AppUserAssignmentProtocol protocol;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			final String[] assignedUsers = request.getParameterValues(ASSIGNMENTS);
			String origin = "";
			try {
				if (request.getParameter(OBJECTIVE) != "") {
					final Long objective_id = Long.parseLong(request.getParameter(OBJECTIVE));
					origin = Page.OBJECTIVE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + objective_id;
					if ((assignedUsers != null) && (assignedUsers.length != 0)) {
						LOGGER.info("Create Assignments (" + assignedUsers.length + " users, objective: " + objective_id + ")");
						this.protocol.saveObjectiveAssignments(assignedUsers, objective_id);
					}
				} else if (request.getParameter(PROJECT) != "") {
					final Long project_id = Long.parseLong(request.getParameter(PROJECT));
					origin = Page.PROJECT_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + project_id;
					if ((assignedUsers != null) && (assignedUsers.length != 0)) {
						LOGGER.info("Create Assignments (" + assignedUsers.length + " users, project: " + project_id + ")");
						// this.protocol.saveObjectiveAssignments(assignedUsers, project_id);
					}
				} else if (request.getParameter(SUBMODULE) != "") {
					final Long submodule_id = Long.parseLong(request.getParameter(SUBMODULE));
					origin = Page.SUBMODULE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + submodule_id;
					if ((assignedUsers != null) && (assignedUsers.length != 0)) {
						LOGGER.info("Create Assignments (" + assignedUsers.length + " users, submodule: " + submodule_id + ")");
						// this.protocol.saveObjectiveAssignments(assignedUsers, submodule_id);
					}
				} else if (request.getParameter(TASK) != "") {
					final Long task_id = Long.parseLong(request.getParameter(TASK));
					origin = Page.TASK_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + task_id;
					if ((assignedUsers != null) && (assignedUsers.length != 0)) {
						LOGGER.info("Create Assignments (" + assignedUsers.length + " users, task: " + task_id + ")");
						// this.protocol.saveObjectiveAssignments(assignedUsers, task_id);
					}
				}
				request.getSession().setAttribute(ATTR_SUCCESS,
						((assignedUsers != null) && (assignedUsers.length != 0))
								? assignedUsers.length != 1 ? assignedUsers.length + " Assignments added succesfully!" : "1 Assignment added succesfully!"
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
