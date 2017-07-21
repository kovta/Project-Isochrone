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

@WebServlet("/TaskDelete")
public class TaskDeleteServlet extends AbstractRefinerServlet implements TaskParameter, TaskAttribute {

	private static final long serialVersionUID = 4000909135744296357L;

	private static final Logger LOGGER = Logger.getLogger(TaskDeleteServlet.class);

	@EJB
	private TaskProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		LOGGER.info("Delete Task by id (" + id + ")");
		try {
			if ((id == null) || "".equals(id) || this.isNumeric(id)) {
				response.sendRedirect(Page.ERROR.getUrl());
			} else {
				this.protocol.removeTask(Long.parseLong(id));
				request.getSession().setAttribute(ATTR_SUCCESS, "Task deleted successfully!");
			}
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
		}
		response.sendRedirect(request.getHeader("referer"));
	}

}