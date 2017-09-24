package com.kota.stratagem.weblayer.servlet.team;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.AppUserProtocol;
import com.kota.stratagem.ejbservice.protocol.TeamProtocol;
import com.kota.stratagem.ejbserviceclient.domain.TeamRepresentor;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.appuser.AppUserListAttribute;
import com.kota.stratagem.weblayer.common.team.TeamAttribute;
import com.kota.stratagem.weblayer.common.team.TeamParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;
import com.kota.stratagem.weblayer.servlet.objective.ObjectiveActionController;

@WebServlet("/Team")
public class TeamActionController extends AbstractRefinerServlet implements TeamParameter, TeamAttribute, AppUserListAttribute {

	private static final long serialVersionUID = 6203104872062284777L;

	private static final Logger LOGGER = Logger.getLogger(ObjectiveActionController.class);

	@EJB
	private TeamProtocol teamProtocol;

	@EJB
	private AppUserProtocol appUserProtocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get Team by id (" + request.getParameter(ID) + ")");
		this.setUserAttributes(request);
		final String id = request.getParameter(ID);
		if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			final boolean editFlag = TRUE_VALUE.equals(request.getParameter(EDIT_FLAG));
			TeamRepresentor team = null;
			boolean errorFlag = false;
			try {
				team = this.teamProtocol.getTeam(Long.parseLong(id));
			} catch (final NumberFormatException | AdaptorException e) {
				LOGGER.error(e, e);
				errorFlag = true;
			}
			this.forward(request, response, team, editFlag, null, errorFlag);
		}
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final TeamRepresentor team, final boolean editFlag,
			String returnPoint, boolean errorFlag) throws ServletException, IOException {
		boolean assignmentError = false;
		if (!errorFlag) {
			try {
				request.setAttribute(ATTR_ASSIGNABLE_USERS, this.appUserProtocol.getAssignableAppUserClusters(team));
				request.setAttribute(ATTR_USERS, this.appUserProtocol.getAssignableAppUsers());
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				assignmentError = true;
			}
		}
		request.setAttribute(ATTR_TEAM, team);
		if (errorFlag || assignmentError) {
			final RequestDispatcher view = request.getRequestDispatcher(Page.ERROR.getJspName());
			view.forward(request, response);
		} else if (returnPoint != null) {
			response.sendRedirect(returnPoint);
		} else {
			final RequestDispatcher view = request.getRequestDispatcher(editFlag ? Page.TEAM_EDIT.getJspName() : Page.TEAM_VIEW.getJspName());
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
			final String returnPoint = (id == null ? Page.TEAM_LIST.getUrl() : Page.TEAM_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + id);
			final String name = request.getParameter(NAME);
			final String leader = request.getParameter(LEADER);
			if ((name == null) || "".equals(name)) {
				LOGGER.info("Failed attempt to modify Team : (" + name + ")");
				request.getSession().setAttribute(ATTR_ERROR, "Team name required");
				final TeamRepresentor team = new TeamRepresentor(name, null);
				this.forward(request, response, team, false, returnPoint + GET_REQUEST_QUERY_EDIT_PARAMETER + TRUE_VALUE, false);
			} else {
				TeamRepresentor team = null;
				try {
					LOGGER.info(id == null ? "Create Team : (" + name + ")" : "Update Team : (" + id + ")");
					team = this.teamProtocol.saveTeam(id, name, leader);
					request.getSession().setAttribute(ATTR_SUCCESS, id == null ? "Team created successfully!" : "Team updated successfully!");
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
					request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
				}
				this.forward(request, response, team, false, returnPoint, false);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
