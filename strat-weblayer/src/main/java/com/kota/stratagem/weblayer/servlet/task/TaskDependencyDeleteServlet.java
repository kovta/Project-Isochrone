package com.kota.stratagem.weblayer.servlet.task;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.TaskProtocol;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.task.TaskAttribute;
import com.kota.stratagem.weblayer.common.task.TaskParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/TaskDependencyDelete")
public class TaskDependencyDeleteServlet extends AbstractRefinerServlet implements TaskParameter, TaskAttribute {

	private static final long serialVersionUID = 9117224863670785070L;

	private static final Logger LOGGER = Logger.getLogger(TaskDependencyDeleteServlet.class);

	@Inject
	TaskProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String dependency = request.getParameter(DEPENDENCY), dependant = request.getParameter(DEPENDANT);
		String origin = "";
		if (!this.notEmpty(dependency) || !this.notEmpty(dependant) || !this.isNumeric(dependency) || !this.isNumeric(dependant)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			try {
				if (this.isNumeric(request.getParameter(TASK))) {
					final Long task_id = Long.parseLong(request.getParameter(TASK));
					origin = Page.TASK_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + task_id;
					LOGGER.info("Remove Task dependency (dependency: " + dependency + ", dependant: " + task_id + ")");
					this.protocol.removeTaskDependency(Long.parseLong(dependency), Long.parseLong(dependant));
				} else {
					response.sendRedirect(Page.ERROR.getUrl());
				}
				request.getSession().setAttribute(ATTR_SUCCESS, "Dependency removed successfully!");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
		}
		response.sendRedirect(origin);
	}
}