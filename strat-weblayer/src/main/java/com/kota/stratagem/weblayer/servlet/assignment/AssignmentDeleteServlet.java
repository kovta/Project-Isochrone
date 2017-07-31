package com.kota.stratagem.weblayer.servlet.assignment;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.AppUserAssignmentProtocol;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.assignment.AssignmentAttribute;
import com.kota.stratagem.weblayer.common.assignment.AssignmentParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/AssignmentDelete")
public class AssignmentDeleteServlet extends AbstractRefinerServlet implements AssignmentParameter, AssignmentAttribute {

	private static final long serialVersionUID = -2010345699510377465L;

	private static final Logger LOGGER = Logger.getLogger(AssignmentDeleteServlet.class);

	private static final String GET_REQUEST_QUERY_APPENDER = "?id=";

	@EJB
	AppUserAssignmentProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		String origin = "";
		if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			try {
				if (request.getParameter(OBJECTIVE) != "") {
					if ((request.getParameter(OBJECTIVE) != null) && this.isNumeric(request.getParameter(OBJECTIVE))) {
						final Long objective_id = Long.parseLong(request.getParameter(OBJECTIVE));
						origin = Page.OBJECTIVE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + objective_id;
						LOGGER.info("Remove Assignment (id: " + id + ", objective: " + objective_id + ")");
						this.protocol.removeObjectiveAssignment(Long.parseLong(id));
					} else {
						response.sendRedirect(Page.ERROR.getUrl());
					}
				} else if (request.getParameter(PROJECT) != "") {
					if ((request.getParameter(PROJECT) != null) && this.isNumeric(request.getParameter(PROJECT))) {
						final Long project_id = Long.parseLong(request.getParameter(PROJECT));
						origin = Page.PROJECT_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + project_id;
						LOGGER.info("Remove Assignment (id: " + id + ", project: " + project_id + ")");
						// this.protocol.removeObjectiveAssignment(Long.parseLong(id));
					} else {
						response.sendRedirect(Page.ERROR.getUrl());
					}
				} else if (request.getParameter(SUBMODULE) != "") {
					if ((request.getParameter(SUBMODULE) != null) && this.isNumeric(request.getParameter(SUBMODULE))) {
						final Long submodule_id = Long.parseLong(request.getParameter(SUBMODULE));
						origin = Page.SUBMODULE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + submodule_id;
						LOGGER.info("Remove Assignment (id: " + id + ", submodule: " + submodule_id + ")");
						// this.protocol.removeObjectiveAssignment(Long.parseLong(id));
					} else {
						response.sendRedirect(Page.ERROR.getUrl());
					}
				} else if (request.getParameter(TASK) != "") {
					if ((request.getParameter(TASK) != null) && this.isNumeric(request.getParameter(TASK))) {
						final Long task_id = Long.parseLong(request.getParameter(TASK));
						origin = Page.TASK_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + task_id;
						LOGGER.info("Remove Assignment (id: " + id + ", task: " + task_id + ")");
						// this.protocol.removeObjectiveAssignment(Long.parseLong(id));
					} else {
						response.sendRedirect(Page.ERROR.getUrl());
					}
				}
				request.getSession().setAttribute(ATTR_SUCCESS, "Assignment removed successfully!");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
		}
		response.sendRedirect(origin);
	}

}
