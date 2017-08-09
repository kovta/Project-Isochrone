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
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.appuser.AppUserAttribute;
import com.kota.stratagem.weblayer.common.appuser.AppUserParameter;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;
import com.kota.stratagem.weblayer.servlet.objective.ObjectiveActionController;

@WebServlet("/User")
public class AppUserActionController extends AbstractRefinerServlet implements AppUserParameter, AppUserAttribute {

	private static final long serialVersionUID = -8728723426537602991L;

	private static final Logger LOGGER = Logger.getLogger(ObjectiveActionController.class);

	@EJB
	private AppUserProtocol appUserProtocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get AppUser by id (" + request.getParameter(ID) + ")");
		final String id = request.getParameter(ID);
		if ((id == null) || "".equals(id) || !this.isNumeric(id)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			final boolean editFlag = TRUE_VALUE.equals(request.getParameter(EDIT_FLAG));
			AppUserRepresentor user = null;
			boolean errorFlag = false;
			try {
				user = this.appUserProtocol.getAppUser(Long.parseLong(id));
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				errorFlag = true;
			}
			this.forward(request, response, user, editFlag, null, errorFlag);
		}
	}

	private void forward(final HttpServletRequest request, final HttpServletResponse response, final AppUserRepresentor user, final boolean editFlag,
			String returnPoint, boolean errorFlag) throws ServletException, IOException {
		request.setAttribute(ATTR_APPUSER, user);
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