package com.kota.stratagem.weblayer.servlet.objective;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.objective.ObjectiveListAttribute;
import com.kota.stratagem.weblayer.servlet.project.ProjectListController;

@WebServlet("/ObjectiveList")
public class ObjectiveListController extends HttpServlet implements ObjectiveListAttribute {

	private static final long serialVersionUID = -7211465510306969487L;

	private static final Logger LOGGER = Logger.getLogger(ProjectListController.class);

	@EJB
	private ObjectiveProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get All Objectives");
		try {
			final List<ObjectiveRepresentor> objectives = this.protocol.getAllObjectives();
			Collections.sort(objectives, new Comparator<ObjectiveRepresentor>() {
				@Override
				public int compare(ObjectiveRepresentor obj_a, ObjectiveRepresentor obj_b) {
					return obj_a.getPriority() - obj_b.getPriority();
				}
			});
			request.setAttribute(ATTR_OBJECTIVES, objectives);
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
		}
		final RequestDispatcher view = request.getRequestDispatcher(Page.OBJECTIVE_LIST.getJspName());
		view.forward(request, response);
	}

}
