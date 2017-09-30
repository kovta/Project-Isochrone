package com.kota.stratagem.weblayer.servlet.appuser;

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
import com.kota.stratagem.ejbserviceclient.domain.AppUserRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.RoleRepresentor;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.appuser.AppUserAttribute;
import com.kota.stratagem.weblayer.common.appuser.AppUserParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/UserRole")
public class AppUserRoleServlet extends AbstractRefinerServlet implements AppUserParameter, AppUserAttribute {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(AppUserRoleServlet.class);

	@EJB
	AppUserProtocol protocol;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Long id = null;
			if (this.notEmpty(request.getParameter(ID))) {
				id = Long.parseLong(request.getParameter(ID));
			}
			final String returnPoint = (Page.USER_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + id);
			final String role = request.getParameter(ROLE);
			AppUserRepresentor user = null;
			try {
				LOGGER.info("Update AppUser: (" + id + ", role: " + role + ")");
				final AppUserRepresentor userTemp = this.protocol.getAppUser(id);
				user = this.protocol.saveAppUser(id, userTemp.getName(), null, userTemp.getEmail(), RoleRepresentor.valueOf(request.getParameter(ROLE)),
						request.getUserPrincipal().getName());
				request.getSession().setAttribute(ATTR_USER_SUCCESS, "Account updated successfully!");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_USER_ERROR, "Operation failed");
			}
			this.forward(request, response, user, false, returnPoint, false);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final AppUserRepresentor user, final boolean editFlag,
			String returnPoint, boolean errorFlag) throws ServletException, IOException {
		request.setAttribute(ATTR_APPUSER, user);
		if (!errorFlag) {
			try {
				request.setAttribute(ATTR_OPERATOR_ACCOUNT, this.protocol.isOperatorAccount(user));
				request.setAttribute(ATTR_SUBORDINATE_USER, this.protocol.isSubordinateUser(user));
				request.setAttribute(ATTR_SUBORDINATE_ROLES, this.protocol.getOperator().getRole().getSubordinateRoles());
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				errorFlag = true;
			}
		}
		if (errorFlag) {
			final RequestDispatcher view = request.getRequestDispatcher(Page.ERROR.getJspName());
			view.forward(request, response);
		} else if (returnPoint != null) {
			response.sendRedirect(returnPoint);
		} else {
			final RequestDispatcher view = request.getRequestDispatcher(editFlag ? Page.USER_EDIT.getJspName() : Page.USER_VIEW.getJspName());
			view.forward(request, response);
		}
	}

}
