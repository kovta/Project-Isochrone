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

@WebServlet("/MoveTask")
public class TaskMoveController extends AbstractRefinerServlet implements TaskAttribute, TaskParameter {

	private static final long serialVersionUID = 779036368993033604L;

	private static final Logger LOGGER = Logger.getLogger(TaskMoveController.class);

	@Inject
	private TaskProtocol protocol;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			String origin = Page.ERROR.getUrl();
			try {
				this.protocol.moveTask(Long.parseLong(request.getParameter(TASK)), Long.parseLong(request.getParameter(DESTINATION)));
				origin = (Page.TASK_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + request.getParameter(TASK));
				request.getSession().setAttribute(ATTR_SUCCESS, "Task moved successfully!");
			} catch(final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
			response.sendRedirect(origin);
		} catch(final Exception e) {
			e.printStackTrace();
		}
	}

}
