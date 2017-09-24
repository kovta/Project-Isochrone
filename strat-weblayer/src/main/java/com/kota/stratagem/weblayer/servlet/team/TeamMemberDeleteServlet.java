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

@WebServlet("/TeamMembershipDelete")
public class TeamMemberDeleteServlet extends AbstractRefinerServlet implements TeamParameter, TeamAttribute {

	private static final long serialVersionUID = 8085881045090498881L;

	private static final Logger LOGGER = Logger.getLogger(TeamMemberDeleteServlet.class);

	@EJB
	TeamProtocol protocol;

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID), member = request.getParameter(MEMBER);
		String origin = "";
		if ((id == null) || "".equals(id) || !this.isNumeric(id) || !this.notEmpty(member)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			try {
				origin = Page.TEAM_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + id;
				LOGGER.info("Remove Team Membership (team: " + id + ", member: " + member + ")");
				this.protocol.removeTeamMembership(Long.parseLong(id), member);
				request.getSession().setAttribute(ATTR_SUCCESS, "Membership removed successfully!");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
		}
		response.sendRedirect(origin);
	}

}
