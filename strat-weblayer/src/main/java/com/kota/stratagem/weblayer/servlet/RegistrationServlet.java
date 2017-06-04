package com.kota.stratagem.weblayer.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.AppUserProtocol;
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.RoleRepresentor;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.RegistrationAttribute;
import com.kota.stratagem.weblayer.common.RegistrationParameter;

@WebServlet("/Register")
public class RegistrationServlet extends HttpServlet implements RegistrationParameter, RegistrationAttribute {

	private static final long serialVersionUID = -4363980684441406896L;

	private static final Logger LOGGER = Logger.getLogger(RegistrationServlet.class);

	@EJB
	private AppUserProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final AppUserRepresentor user = new AppUserRepresentor("", "", "", RoleRepresentor.PRISTINE_USER, null, null);
		this.forward(request, response, user, false);
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final AppUserRepresentor user, final boolean finishFlag)
			throws ServletException, IOException {
		request.setAttribute(ATTR_REG_USER, user);
		final RequestDispatcher view = request.getRequestDispatcher(Page.HOME.getJspName());
		view.forward(request, response);
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		try {
			final String username = request.getParameter(USERNAME);
			final String email = request.getParameter(EMAIL);
			final String password = request.getParameter(PASSWORD);
			final String password_confirmation = request.getParameter(PASSWORD_CONFIRMATION);
			LOGGER.info("Registration attempt");
			if (((username == null) || "".equals(username)) || ((password == null) || "".equals(password)) || !(password.equals(password_confirmation))) {
				LOGGER.info("Registration failed, -- " + username + ", " + password + ", " + password_confirmation);
				if ((username == null) || "".equals(username)) {
					request.setAttribute(ATTR_REG_ERROR, "Username required");
				} else if ((password == null) || "".equals(password)) {
					request.setAttribute(ATTR_REG_ERROR, "Password required");
				} else if (!(password.equals(password_confirmation))) {
					request.setAttribute(ATTR_REG_ERROR, "Unmatching passwords");
				}
				final AppUserRepresentor user = new AppUserRepresentor(username, "", email, RoleRepresentor.PRISTINE_USER, null, null);
				this.forward(request, response, user, false);
			} else {
				AppUserRepresentor user = null;
				try {
					LOGGER.info("Registration successful for user: " + username);
					user = this.protocol.saveAppUser(null, username, password, email, RoleRepresentor.PRISTINE_USER, null, null, null, null, null, null, null,
							null);
					request.setAttribute(ATTR_REG_SUCCESS, "Registration successful!");
				} catch (final AdaptorException e) {
					LOGGER.error(e, e);
					request.setAttribute(ATTR_REG_ERROR, "Different username required");
				}
				this.forward(request, response, user, true);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
