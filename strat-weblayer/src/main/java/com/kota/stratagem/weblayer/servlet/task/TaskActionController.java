package com.kota.stratagem.weblayer.servlet.task;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.AppUserProtocol;
import com.kota.stratagem.ejbservice.protocol.TaskProtocol;
import com.kota.stratagem.ejbservice.protocol.TeamProtocol;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.task.TaskAttribute;
import com.kota.stratagem.weblayer.common.task.TaskParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/Task")
public class TaskActionController extends AbstractRefinerServlet implements TaskAttribute, TaskParameter {

	private static final long serialVersionUID = 319115678364738435L;

	private static final Logger LOGGER = Logger.getLogger(TaskActionController.class);

	@Inject
	private TaskProtocol taskProtocol;

	@EJB
	private AppUserProtocol appUserProtocol;

	@EJB
	private TeamProtocol teamProtocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get Task by id (" + request.getParameter(ID) + ")");
		this.setUserAttributes(request);
		final String id = request.getParameter(ID);
		if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			final boolean editFlag = TRUE_VALUE.equals(request.getParameter(EDIT_FLAG));
			TaskRepresentor task = null;
			boolean errorFlag = false;
			try {
				task = this.taskProtocol.getTask(Long.parseLong(id));
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				errorFlag = true;
			}
			this.forward(request, response, task, editFlag, null, errorFlag);
		}
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final TaskRepresentor task, final boolean editFlag,
			String returnPoint, boolean errorFlag) throws ServletException, IOException {
		if (!errorFlag) {
			try {
				request.setAttribute(ATTR_ASSIGNABLE_USERS, this.appUserProtocol.getAssignableAppUserClusters(task));
				request.setAttribute(ATTR_ASSIGNABLE_TEAMS, this.teamProtocol.getAssignableTeams(task));
				request.setAttribute(ATTR_CONFIGURABLE_DEPENDENCIES, this.taskProtocol.getCompliantDependencyConfigurations(task));
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				errorFlag = true;
			}
		}
		request.setAttribute(ATTR_TASK, task);
		if (errorFlag) {
			final RequestDispatcher view = request.getRequestDispatcher(Page.ERROR.getJspName());
			view.forward(request, response);
		} else if (returnPoint != null) {
			response.sendRedirect(returnPoint);
		} else {
			final RequestDispatcher view = request.getRequestDispatcher(editFlag ? Page.TASK_EDIT.getJspName() : Page.TASK_VIEW.getJspName());
			view.forward(request, response);
		}
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			Long id = null, objective_id = null, project_id = null, submodule_id = null;
			String returnPoint = null;
			if (this.notEmpty(request.getParameter(ID))) {
				id = Long.parseLong(request.getParameter(ID));
				returnPoint = (Page.TASK_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + id);
			} else {
				if (this.notEmpty(request.getParameter(PARENT_OBJECTIVE))) {
					objective_id = Long.parseLong(request.getParameter(PARENT_OBJECTIVE));
					returnPoint = (Page.OBJECTIVE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + objective_id);
				} else if (this.notEmpty(request.getParameter(PARENT_PROJECT))) {
					project_id = Long.parseLong(request.getParameter(PARENT_PROJECT));
					returnPoint = (Page.PROJECT_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + project_id);
				} else if (this.notEmpty(request.getParameter(PARENT_SUBMODULE))) {
					submodule_id = Long.parseLong(request.getParameter(PARENT_SUBMODULE));
					returnPoint = (Page.SUBMODULE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + submodule_id);
				} else {
					returnPoint = Page.ERROR.getUrl();
				}
			}
			final String name = request.getParameter(NAME);
			final String description = request.getParameter(DESCRIPTION);
			int priority = 10;
			if (this.notEmpty(request.getParameter(PRIORITY))) {
				priority = Integer.parseInt(request.getParameter(PRIORITY));
			}
			final double completion = Double.parseDouble(request.getParameter(COMPLETION));
			Date deadline = null;
			try {
				final DateFormat extractionFormat = new SimpleDateFormat("MM/dd/yyyy");
				if (this.notEmpty(request.getParameter(DEADLINE))) {
					deadline = extractionFormat.parse(request.getParameter(DEADLINE));
				}
			} catch (final ParseException e) {
				LOGGER.info("Failed attempt to modify Task : (" + name + ") because of unusable date format");
				request.getSession().setAttribute(ATTR_ERROR, "Incorrect date format");
				final TaskRepresentor Task = new TaskRepresentor(name, description, priority, completion, null, null, false);
				this.forward(request, response, Task, false, returnPoint + GET_REQUEST_QUERY_EDIT_PARAMETER + TRUE_VALUE, false);
			}
			Double duration = null, pessimistic = null, realistic = null, optimistic = null;
			if (request.getParameter(DURATION_TYPE).equals("1")) {
				if (this.notEmpty(request.getParameter(PESSIMISTIC_DURATION)) && this.notEmpty(request.getParameter(REALISTIC_DURATION))
						&& this.notEmpty(request.getParameter(OPTIMISTIC_DURATION))) {
					pessimistic = Double.parseDouble(request.getParameter(PESSIMISTIC_DURATION));
					realistic = Double.parseDouble(request.getParameter(REALISTIC_DURATION));
					optimistic = Double.parseDouble(request.getParameter(OPTIMISTIC_DURATION));
				} else if (this.notEmpty(request.getParameter(PESSIMISTIC_DURATION)) || this.notEmpty(request.getParameter(REALISTIC_DURATION))
						|| this.notEmpty(request.getParameter(OPTIMISTIC_DURATION))) {
					LOGGER.info("Failed attempt to modify Task : (" + name + ")");
					request.getSession().setAttribute(ATTR_ERROR, "Partial estimations not allowed");
					final TaskRepresentor task = new TaskRepresentor(name, description, priority, completion, deadline, null, false);
					this.forward(request, response, task, false, returnPoint + GET_REQUEST_QUERY_EDIT_PARAMETER + TRUE_VALUE, false);
				}
			} else {
				if (this.notEmpty(request.getParameter(DURATION))) {
					duration = Double.parseDouble(request.getParameter(DURATION));
				}
			}
			final Boolean admittance = request.getParameter(ADMITTANCE).equals("1") ? true : false;
			if ((name == null) || "".equals(name)) {
				LOGGER.info("Failed attempt to modify Task : (" + name + ")");
				request.getSession().setAttribute(ATTR_ERROR, "Task name required");
				final TaskRepresentor task = new TaskRepresentor(name, description, priority, completion, deadline, duration, admittance);
				this.forward(request, response, task, false, returnPoint + GET_REQUEST_QUERY_EDIT_PARAMETER + TRUE_VALUE, false);
			} else {
				TaskRepresentor task = null;
				try {
					LOGGER.info(id == null ? "Create Task : (" + name + ")" : "Update Task : (" + id + ")");
					task = this.taskProtocol.saveTask(id, name, description, priority, completion, deadline, admittance, request.getUserPrincipal().getName(),
							objective_id, project_id, submodule_id, duration, pessimistic, realistic, optimistic);
					request.getSession().setAttribute(ATTR_SUCCESS, id == null ? "Task created successfully!" : "Task updated successfully!");
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
					request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
				}
				this.forward(request, response, task, false, returnPoint, false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
