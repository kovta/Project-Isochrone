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

@WebServlet("/TaskDependant")
public class TaskDependantController extends AbstractRefinerServlet implements TaskParameter, TaskAttribute {

	private static final long serialVersionUID = -9137812644965783833L;

	private static final Logger LOGGER = Logger.getLogger(TaskDependantController.class);

	@Inject
	TaskProtocol protocol;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			final String[] dependants = request.getParameterValues(DEPENDANTS);
			try {
				if ((dependants != null) && (dependants.length != 0)) {
					LOGGER.info("Save Task Dependants (" + dependants.length + " dependants, task: " + request.getParameter(TASK) + ")");
					final Long[] dependantIdentifiers = new Long[dependants.length];
					for (int i = 0; i < dependants.length; i++) {
						dependantIdentifiers[i] = Long.parseLong(dependants[i]);
					}
					this.protocol.saveTaskDependants(Long.parseLong(request.getParameter(TASK)), dependantIdentifiers);
				}
				request.getSession().setAttribute(ATTR_SUCCESS,
						((dependants != null) && (dependants.length != 0))
								? dependants.length != 1 ? dependants.length + " Dependants added succesfully!" : "1 Dependant added succesfully!"
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
