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
import com.kota.stratagem.weblayer.servlet.objective.ObjectiveDeleteServlet;

@WebServlet("/TeamDelete")
public class TeamDeleteServlet extends AbstractRefinerServlet implements TeamParameter, TeamAttribute {

	private static final long serialVersionUID = 6540675610004501966L;

	private static final Logger LOGGER = Logger.getLogger(ObjectiveDeleteServlet.class);

	@EJB
	private TeamProtocol protocol;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter(ID);
		LOGGER.info("Delete Team by id (" + id + ")");
		try {
			if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
				response.sendRedirect(Page.ERROR.getUrl());
			} else {
				this.protocol.removeTeam(Long.parseLong(id));
				request.getSession().setAttribute(ATTR_SUCCESS, "Team deleted successfully!");
				response.sendRedirect(Page.TEAM_LIST.getUrl());
			}
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
		}
	}

}
