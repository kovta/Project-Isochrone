package com.kota.stratagem.weblayer.servlet.appuser;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.AppUserProtocol;
import com.kota.stratagem.weblayer.common.Page;
import com.kota.stratagem.weblayer.common.appuser.AppUserParameter;
import com.kota.stratagem.weblayer.common.assignment.AssignmentAttribute;
import com.kota.stratagem.weblayer.servlet.AbstractRefinerServlet;

@WebServlet("/UserImage")
public class AppUserImageServlet extends AbstractRefinerServlet implements AppUserParameter, AssignmentAttribute {

	private static final long serialVersionUID = -2010345699510377465L;

	private static final Logger LOGGER = Logger.getLogger(AppUserImageServlet.class);

	@EJB
	AppUserProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String image = request.getParameter(IMAGE), id = request.getParameter(ID);
		String origin = "";
		if (!this.notEmpty(image) || !this.isNumeric(image) || !this.notEmpty(id) || !this.isNumeric(id)) {
			response.sendRedirect(Page.ERROR.getUrl());
		} else {
			try {
				origin = Page.USER_VIEW.getUrl() + GET_REQUEST_QUERY_APPENDER + id;
				this.protocol.saveImageSelector(Integer.parseInt(image));
				request.getSession().setAttribute(ATTR_SUCCESS, "Profile updated successfully!");
			} catch (final AdaptorException e) {
				LOGGER.error(e, e);
				request.getSession().setAttribute(ATTR_ERROR, "Operation failed");
			}
		}
		response.sendRedirect(origin);
	}

}
