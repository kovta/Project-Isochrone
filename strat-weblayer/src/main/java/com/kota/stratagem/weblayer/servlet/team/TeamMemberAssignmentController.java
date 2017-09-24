package com.kota.stratagem.weblayer.servlet.team;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.TeamProtocol;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.team.TeamAttribute;
import com.kota.stratagem.weblayer.common.team.TeamParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;
import com.kota.stratagem.weblayer.servlet.assignment.AppUserAssignmentController;

@WebServlet("/TeamMembershipAssignment")
public class TeamMemberAssignmentController extends AbstractRefinerServlet implements TeamParameter, TeamAttribute {

	private static final long serialVersionUID = -585996129296390908L;

	private static final Logger LOGGER = Logger.getLogger(AppUserAssignmentController.class);

	@EJB
	TeamProtocol protocol;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			final String[] memberships = request.getParameterValues(MEMBERS);
			String origin = "";
			try {
				final Long id = Long.parseLong(request.getParameter(ID));
				origin = Page.TEAM_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + id;
				if ((memberships != null) && (memberships.length != 0)) {
					LOGGER.info("Create Team Memberships (" + memberships.length + " memberships, Team: " + id + ")");
					this.protocol.saveTeamMemberships(id, memberships);
				}
				request.getSession().setAttribute(ATTR_SUCCESS,
						((memberships != null) && (memberships.length != 0))
								? memberships.length != 1 ? memberships.length + " Memberships added succesfully!" : "1 Membership added succesfully!"
								: "No selections were made");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
			response.sendRedirect(origin);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
