package com.kota.stratagem.weblayer.servlet.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.protocol.TaskProtocol;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;

@WebServlet("/TaskPing")
public class TaskPingServlet extends HttpServlet {

	private static final long serialVersionUID = -3067666682855692427L;

	private static final Logger LOGGER = Logger.getLogger(TaskPingServlet.class);

	@EJB
	private TaskProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get tasks");
		response.setCharacterEncoding("UTF-8");
		final PrintWriter out = response.getWriter();
		try {
			final List<TaskRepresentor> tasks = this.protocol.getAllTasks();
			for (final TaskRepresentor representor : tasks) {
				out.println(representor.toString());
			}
		} catch (final Exception e) {
			LOGGER.error(e, e);
			out.println(e.getLocalizedMessage());
		}
		out.close();
	}

}