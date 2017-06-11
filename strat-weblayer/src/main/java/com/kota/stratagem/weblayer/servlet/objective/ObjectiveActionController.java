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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.objective.ObjectiveAttribute;
import com.kota.stratagem.weblayer.common.objective.ObjectiveParameter;

@WebServlet("/ObjectiveAction")
public class ObjectiveActionController extends HttpServlet implements ObjectiveParameter, ObjectiveAttribute {

	private static final long serialVersionUID = -6240725013473292997L;

	private static final Logger LOGGER = Logger.getLogger(ObjectiveActionController.class);

	private static final String TRUE_VALUE = "1";
	private static final String NEW_OBJECTIVE_ID_FLAG = "-1";

	@EJB
	private ObjectiveProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		LOGGER.info("Get Objective by id (" + id + ")");
		if ((id == null) || "".equals(id)) {
			response.sendRedirect(Page.OBJECTIVE_LIST.getUrl());
		} else {
			final boolean editFlag = TRUE_VALUE.equals(request.getParameter(EDIT_FLAG));
			ObjectiveRepresentor objective = null;
			boolean isNew = false;
			if (NEW_OBJECTIVE_ID_FLAG.equals(id)) {
				objective = new ObjectiveRepresentor("", "", 0, ObjectiveStatusRepresentor.PLANNED, new Date(), false, null, null, null, null);
				isNew = true;
			} else {
				try {
					objective = this.protocol.getObjective(Long.parseLong(id));
				} catch (final ServiceException e) {
					LOGGER.error(e, e);
				}
			}
			this.forward(request, response, objective, editFlag, isNew, false);
		}
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final ObjectiveRepresentor objective, final boolean editFlag,
			final boolean isNew, final boolean finishFlag) throws ServletException, IOException {
		request.setAttribute(ATTR_OBJECTIVE, objective);
		if (finishFlag) {
			response.sendRedirect(Page.OBJECTIVE_LIST.getUrl());
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
				LOGGER.info(deadlineTemp + ", input: " + request.getParameter(DEADLINE));
			} catch (final ParseException e) {
				LOGGER.info("Failed attempt to modify Objective : (" + name + ") because of unusable date format");
				request.getSession().setAttribute(ATTR_ERROR, "Incorrect date format");
				final ObjectiveRepresentor objective = new ObjectiveRepresentor(name, description, priority, status, null, false, null, null, null, null);
				this.forward(request, response, objective, false, false, true);
			}
			final Date deadline = deadlineTemp;
			if ((name == null) || "".equals(name)) {
				LOGGER.info("Failed attempt to modify Objective : (" + name + ")");
				request.getSession().setAttribute(ATTR_ERROR, "Objective name required");
				// new attributes must be requested
				final ObjectiveRepresentor objective = new ObjectiveRepresentor(name, description, priority, status, deadline, false, null, null, null, null);
				this.forward(request, response, objective, false, false, true);
			} else {
				ObjectiveRepresentor objective = null;
				try {
					LOGGER.info(id == null ? "Create Objective : (" + name + ")" : "Update Objective : (" + id + ")");
					// new attributes must be requested
					// operator must be resolved
					objective = this.protocol.saveObjective(id, name, description, priority, status, deadline, false, request.getUserPrincipal().getName(),
							null, null, null, null);
					request.getSession().setAttribute(ATTR_SUCCESS, id == null ? "Objective created succesfully!" : "Objective updated successfully!");
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
					request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
				}
				this.forward(request, response, objective, false, false, true);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
