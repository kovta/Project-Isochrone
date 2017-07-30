package com.kota.stratagem.weblayer.servlet.objective;

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
import com.kota.stratagem.ejbservice.protocol.AppUserProtocol;
import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.objective.ObjectiveAttribute;
import com.kota.stratagem.weblayer.common.objective.ObjectiveParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/Objective")
public class ObjectiveActionController extends AbstractRefinerServlet implements ObjectiveParameter, ObjectiveAttribute {

	private static final long serialVersionUID = -6240725013473292997L;

	private static final Logger LOGGER = Logger.getLogger(ObjectiveActionController.class);

	@EJB
	private ObjectiveProtocol objectiveProtocol;

	@EJB
	private AppUserProtocol appUserProtocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get Objective by id (" + request.getParameter(ID) + ")");
		final String id = request.getParameter(ID);
		if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			final boolean editFlag = TRUE_VALUE.equals(request.getParameter(EDIT_FLAG));
			ObjectiveRepresentor objective = null;
			boolean errorFlag = false;
			try {
				objective = this.objectiveProtocol.getObjective(Long.parseLong(id));
			} catch (final ServiceException e) {
				LOGGER.error(e, e);
				errorFlag = true;
			}
			this.forward(request, response, objective, editFlag, null, errorFlag);
		}
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final ObjectiveRepresentor objective, final boolean editFlag,
			String returnPoint, boolean errorFlag) throws ServletException, IOException {
		boolean assignmentError = false;
		try {
			request.setAttribute(ATTR_ASSIGNABLE_USERS, this.appUserProtocol.getAssignableAppUserClusters());
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
			assignmentError = true;
		}
		request.setAttribute(ATTR_OBJECTIVE, objective);
		if (errorFlag || assignmentError) {
			final RequestDispatcher view = request.getRequestDispatcher(Page.ERROR.getJspName());
			view.forward(request, response);
		} else if (returnPoint != null) {
			response.sendRedirect(returnPoint);
		} else {
			final RequestDispatcher view = request.getRequestDispatcher(editFlag ? Page.OBJECTIVE_EDIT.getJspName() : Page.OBJECTIVE_VIEW.getJspName());
			view.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Long id = null;
			if ((request.getParameter(ID) != "") && (request.getParameter(ID) != null)) {
				id = Long.parseLong(request.getParameter(ID));
			}
			final String returnPoint = (id == null ? Page.OBJECTIVE_LIST.getUrl() : Page.OBJECTIVE_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + id);
			final String name = request.getParameter(NAME);
			final String description = request.getParameter(DESCRIPTION);
			int priorityTemp = 0;
			if (request.getParameter(PRIORITY) != "") {
				priorityTemp = Integer.parseInt(request.getParameter(PRIORITY));
			}
			final int priority = priorityTemp;
			final ObjectiveStatusRepresentor status = ObjectiveStatusRepresentor.valueOf(request.getParameter(STATUS));
			Date deadlineTemp = null;
			try {
				final DateFormat extractionFormat = new SimpleDateFormat("MM/dd/yyyy");
				if (request.getParameter(DEADLINE) != "") {
					deadlineTemp = extractionFormat.parse(request.getParameter(DEADLINE));
				}
			} catch (final ParseException e) {
				LOGGER.info("Failed attempt to modify Objective : (" + name + ") because of unusable date format");
				request.getSession().setAttribute(ATTR_ERROR, "Incorrect date format");
				final ObjectiveRepresentor objective = new ObjectiveRepresentor(name, description, priority, status, null, false, null, null, null, null);
				this.forward(request, response, objective, false, returnPoint + GET_REQUEST_QUERY_EDIT_PARAMETER + TRUE_VALUE, false);
			}
			final Date deadline = deadlineTemp;
			final Boolean confidentiality = request.getParameter(CONFIDENTIALITY).equals("1") ? true : false;
			if ((name == null) || "".equals(name)) {
				LOGGER.info("Failed attempt to modify Objective : (" + name + ")");
				request.getSession().setAttribute(ATTR_ERROR, "Objective name required");
				// new attributes must be requested
				final ObjectiveRepresentor objective = new ObjectiveRepresentor(name, description, priority, status, deadline, confidentiality, null, null,
						null, null);
				this.forward(request, response, objective, false, returnPoint + GET_REQUEST_QUERY_EDIT_PARAMETER + TRUE_VALUE, false);
			} else {
				ObjectiveRepresentor objective = null;
				try {
					LOGGER.info(id == null ? "Create Objective : (" + name + ")" : "Update Objective : (" + id + ")");
					objective = this.objectiveProtocol.saveObjective(id, name, description, priority, status, deadline, confidentiality,
							request.getUserPrincipal().getName());
					request.getSession().setAttribute(ATTR_SUCCESS, id == null ? "Objective created succesfully!" : "Objective updated successfully!");
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
					request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
				}
				this.forward(request, response, objective, false, returnPoint, false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}