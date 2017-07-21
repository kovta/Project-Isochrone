package com.kota.stratagem.weblayer.servlet.task;

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
import com.kota.stratagem.ejbservice.protocol.TaskProtocol;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.task.TaskAttribute;
import com.kota.stratagem.weblayer.common.task.TaskParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/Task")
public class TaskActionController extends AbstractRefinerServlet implements TaskAttribute, TaskParameter {

	private static final long serialVersionUID = 319115678364738435L;

	private static final Logger LOGGER = Logger.getLogger(TaskActionController.class);

	private static final String TRUE_VALUE = "1";
	private static final String NEW_TASK_ID_FLAG = "-1";

	@EJB
	TaskProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get Task by id (" + request.getParameter(ID) + ")");
		final String id = request.getParameter(ID);
		if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			final boolean editFlag = TRUE_VALUE.equals(request.getParameter(EDIT_FLAG));
			TaskRepresentor project = null;
			boolean isNew = false, errorFlag = false;
			if (NEW_TASK_ID_FLAG.equals(id)) {
				project = new TaskRepresentor(null, "", "", 0, 0, new Date(), null, new Date(), null, new Date());
				isNew = true;
			} else {
				try {
					project = this.protocol.getTask(Long.parseLong(id));
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
					errorFlag = true;
				}
			}
			this.forward(request, response, project, editFlag, isNew, false, errorFlag);
		}
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final TaskRepresentor task, final boolean editFlag,
			boolean isNew, boolean finishFlag, boolean errorFlag) throws ServletException, IOException {
		request.setAttribute(ATTR_TASK, task);
		request.setAttribute(ATTR_ISNEW, isNew);
		if (errorFlag) {
			final RequestDispatcher view = request.getRequestDispatcher(Page.ERROR.getJspName());
			view.forward(request, response);
		} else if (finishFlag) {
			response.sendRedirect(Page.PROJECT_LIST.getUrl());
		} else {
			final RequestDispatcher view = request.getRequestDispatcher(editFlag ? Page.TASK_EDIT.getJspName() : Page.TASK_VIEW.getJspName());
			view.forward(request, response);
		}
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			Long id = null, objective_id = null, project_id = null;
			if ((request.getParameter(ID) != "") && (request.getParameter(ID) != null)) {
				id = Long.parseLong(request.getParameter(ID));
			} else {
				if (request.getParameter(PARENT_OBJECTIVE) != "") {
					objective_id = Long.parseLong(request.getParameter(PARENT_OBJECTIVE));
				} else if (request.getParameter(PARENT_PROJECT) != "") {
					project_id = Long.parseLong(request.getParameter(PARENT_PROJECT));
				}
			}
			final String name = request.getParameter(NAME);
			final String description = request.getParameter(DESCRIPTION);
			int priorityTemp = 0;
			if (request.getParameter(PRIORITY) != "") {
				priorityTemp = Integer.parseInt(request.getParameter(PRIORITY));
			}
			final int priority = priorityTemp;
			final double completion = Double.parseDouble(request.getParameter(COMPLETION));
			Date deadlineTemp = null;
			try {
				final DateFormat extractionFormat = new SimpleDateFormat("MM/dd/yyyy");
				if (request.getParameter(DEADLINE) != "") {
					deadlineTemp = extractionFormat.parse(request.getParameter(DEADLINE));
				}
			} catch (final ParseException e) {
				LOGGER.info("Failed attempt to modify Task : (" + name + ") because of unusable date format");
				request.getSession().setAttribute(ATTR_ERROR, "Incorrect date format");
				final TaskRepresentor Task = new TaskRepresentor(name, description, priority, completion, null, null, null, null, null);
				this.forward(request, response, Task, false, false, true, false);
			}
			final Date deadline = deadlineTemp;
			if ((name == null) || "".equals(name)) {
				LOGGER.info("Failed attempt to modify Task : (" + name + ")");
				request.getSession().setAttribute(ATTR_ERROR, "Task name required");
				final TaskRepresentor Task = new TaskRepresentor(name, description, priority, completion, deadline, null, null, null, null);
				this.forward(request, response, Task, false, false, true, false);
			} else {
				TaskRepresentor task = null;
				try {
					LOGGER.info(id == null ? "Create Task : (" + name + ")" : "Update Task : (" + id + ")");
					task = this.protocol.saveTask(id, name, description, priority, completion, deadline, request.getUserPrincipal().getName(), null, null, null,
							null, null, objective_id, project_id);
					request.getSession().setAttribute(ATTR_SUCCESS, id == null ? "Task created succesfully!" : "Task updated successfully!");
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
					request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
				}
				this.forward(request, response, task, false, false, true, false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
