package com.kota.stratagem.weblayer.servlet.task;

import java.io.IOException;

import javax.ejb.EJB;
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

@WebServlet("/TaskDependency")
public class TaskDependencyController extends AbstractRefinerServlet implements TaskParameter, TaskAttribute {

	private static final long serialVersionUID = -9137812644965783833L;

	private static final Logger LOGGER = Logger.getLogger(TaskDependencyController.class);

	@EJB
	TaskProtocol protocol;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			final String[] dependencies = request.getParameterValues(DEPENDENCIES);
			try {
				if ((dependencies != null) && (dependencies.length != 0)) {
					LOGGER.info("Save Task Dependecies (" + dependencies.length + " dependecies, task: " + request.getParameter(TASK) + ")");
					final Long[] dependencyIdentifiers = new Long[dependencies.length];
					for (int i = 0; i < dependencies.length; i++) {
						dependencyIdentifiers[i] = Long.parseLong(dependencies[i]);
					}
					this.protocol.saveTaskDependencies(Long.parseLong(request.getParameter(TASK)), dependencyIdentifiers);
				}
				request.getSession().setAttribute(ATTR_SUCCESS,
						((dependencies != null) && (dependencies.length != 0))
								? dependencies.length != 1 ? dependencies.length + " Dependencies added succesfully!" : "1 Dependency added succesfully!"
								: "No selections were made");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
			response.sendRedirect(Page.TASK_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + request.getParameter(TASK));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
