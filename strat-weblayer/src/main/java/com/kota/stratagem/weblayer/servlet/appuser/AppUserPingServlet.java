package com.kota.stratagem.weblayer.servlet.appuser;

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

import com.kota.stratagem.ejbservice.protocol.AppUserProtocol;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;

@WebServlet("/AppUserPing")
public class AppUserPingServlet extends HttpServlet {

	private static final long serialVersionUID = -7127244186756736915L;

	private static final Logger LOGGER = Logger.getLogger(AppUserPingServlet.class);

	@EJB
	private AppUserProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get AppUsers");
		response.setCharacterEncoding("UTF-8");
		final PrintWriter out = response.getWriter();
		try {
			final List<AppUserRepresentor> users = this.protocol.getAllAppUsers();
			for (final AppUserRepresentor representor : users) {
				out.println(representor.toString());
			}
		} catch (final Exception e) {
			LOGGER.error(e, e);
			out.println(e.getLocalizedMessage());
		}
		out.close();
	}

}
