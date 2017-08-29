package com.kota.stratagem.weblayer.servlet.appuser;

import java.io.IOException;
import java.util.List;

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
import com.kota.stratagem.weblayer.common.appuser.AppUserListAttribute;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/UserList")
public class AppUserListController extends AbstractRefinerServlet implements AppUserListAttribute {

	private static final long serialVersionUID = -2772686760528594706L;

	private static final Logger LOGGER = Logger.getLogger(AppUserListController.class);

	@EJB
	private AppUserProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get All AppUsers");
		try {
			this.setUserAttributes(request);
			final List<AppUserRepresentor> users = this.protocol.getAllAppUsers();
			request.setAttribute(ATTR_USERS, users);
		} catch (final AdaptorException e) {
			LOGGER.error(e, e);
		}
		final RequestDispatcher view = request.getRequestDispatcher(Page.USER_LIST.getJspName());
		view.forward(request, response);
	}

}
