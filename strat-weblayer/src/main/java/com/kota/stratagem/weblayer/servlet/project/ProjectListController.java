package com.kota.stratagem.weblayer.servlet.project;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ProjectProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectCriteria;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.weblayer.common.FormValue;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.project.ProjectListAttribute;
import com.kota.stratagem.weblayer.common.project.ProjectListParameter;

@WebServlet("/ProjectList")
public class ProjectListController extends HttpServlet implements ProjectListAttribute, ProjectListParameter, FormValue {

	private static final long serialVersionUID = -7360081024797943969L;

	private static final Logger LOGGER = Logger.getLogger(ProjectListController.class);

	@EJB
	private ProjectProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get All Projects");
		try {
			final List<ObjectiveRepresentor> objectiveProjectClusters = this.protocol.getObjectiveProjectClusters();
			request.setAttribute(ATTR_PARENT_OBJECTIVES_CLUSTERS, objectiveProjectClusters);
		} catch (final ServiceException e) {
			LOGGER.error(e, e);
		}
		this.forward(request, response, new ProjectCriteria(), FILTER_ALL_CATEGORY);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final String statusName = request.getParameter(STATUS);
		final ProjectCriteria criteria = new ProjectCriteria();
		if (!statusName.equals(FILTER_ALL_CATEGORY)) {
			criteria.setStatus(ProjectStatusRepresentor.valueOf(statusName));
		}
		this.forward(request, response, criteria, statusName);
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, ProjectCriteria criteria, String categoryValue)
			throws ServletException, IOException {
		try {
			final List<ProjectRepresentor> projects = this.protocol.getAllProjects(criteria);
			request.setAttribute(ATTR_PROJECTS, projects);
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
		}
		request.setAttribute(ATTR_STATUS, categoryValue);
		final RequestDispatcher view = request.getRequestDispatcher(Page.PROJECT_LIST.getJspName());
		view.forward(request, response);
	}
}
