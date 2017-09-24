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
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.appuser.AppUserListAttribute;
import com.kota.stratagem.weblayer.common.team.TeamListAttribute;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/TeamList")
public class TeamListController extends AbstractRefinerServlet implements TeamListAttribute, AppUserListAttribute {

	private static final long serialVersionUID = -6146897196724191308L;

	private static final Logger LOGGER = Logger.getLogger(TeamListController.class);

	@EJB
	private TeamProtocol teamProtocol;

	@EJB
	private AppUserProtocol appUserProtocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get All Teams");
		try {
			this.setUserAttributes(request);
			request.setAttribute(ATTR_TEAMS, this.teamProtocol.getAllTeams());
			request.setAttribute(ATTR_USERS, this.appUserProtocol.getAssignableAppUsers());
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
		}
		final RequestDispatcher view = request.getRequestDispatcher(Page.TEAM_LIST.getJspName());
		view.forward(request, response);
	}

}