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

@WebServlet("/Assignment")
public class AssignmentController extends HttpServlet implements AssignmentParameter, AssignmentAttribute {

	private static final long serialVersionUID = 4129567458088082377L;

	private static final Logger LOGGER = Logger.getLogger(AssignmentController.class);

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
					LOGGER.info("Create Assignments (" + assignedUsers.length + " users, objective: " + objective_id + ")");
					this.protocol.saveObjectiveAssignments(assignedUsers, objective_id);
					origin = Page.OBJECTIVE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + objective_id;
				} else if (request.getParameter(PROJECT) != "") {
					final Long project_id = Long.parseLong(request.getParameter(PROJECT));
					LOGGER.info("Create Assignments (" + assignedUsers.length + " users, project: " + project_id + ")");
					// this.protocol.saveObjectiveAssignments(assignedUsers, project_id);
					origin = Page.PROJECT_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + project_id;
				} else if (request.getParameter(SUBMODULE) != "") {
					final Long submodule_id = Long.parseLong(request.getParameter(SUBMODULE));
					LOGGER.info("Create Assignments (" + assignedUsers.length + " users, submodule: " + submodule_id + ")");
					// this.protocol.saveObjectiveAssignments(assignedUsers, submodule_id);
					origin = Page.SUBMODULE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + submodule_id;
				} else if (request.getParameter(TASK) != "") {
					final Long task_id = Long.parseLong(request.getParameter(TASK));
					LOGGER.info("Create Assignments (" + assignedUsers.length + " users, task: " + task_id + ")");
					// this.protocol.saveObjectiveAssignments(assignedUsers, task_id);
					origin = Page.TASK_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + task_id;
				}
				request.getSession().setAttribute(ATTR_SUCCESS, assignedUsers.length + "Assignments added succesfully!");
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
