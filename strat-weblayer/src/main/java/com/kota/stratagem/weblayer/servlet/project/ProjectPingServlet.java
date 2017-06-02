package com.kota.stratagem.weblayer.servlet.project;

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

import com.kota.stratagem.ejbservice.protocol.ProjectProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ProjectCriteria;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;

@WebServlet("/ProjectPing")
public class ProjectPingServlet extends HttpServlet {

	private static final long serialVersionUID = -3780468253548335598L;

	private static final Logger LOGGER = Logger.getLogger(ProjectPingServlet.class);

	@EJB
	private ProjectProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get projects");
		response.setCharacterEncoding("UTF-8");
		final PrintWriter out = response.getWriter();
		try {
			final List<ProjectRepresentor> projects = this.protocol.getAllProjects(new ProjectCriteria());
			for (final ProjectRepresentor representor : projects) {
				out.println(representor.toString());
			}
			final ProjectRepresentor project = this.protocol.getProject(2L);
			out.println(project.toString());
		} catch (final Exception e) {
			LOGGER.error(e, e);
			out.println(e.getLocalizedMessage());
		}
		out.close();
	}

}