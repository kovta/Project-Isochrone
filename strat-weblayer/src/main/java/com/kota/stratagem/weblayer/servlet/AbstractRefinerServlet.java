package com.kota.stratagem.weblayer.servlet;

import java.util.Scanner;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.protocol.AppUserProtocol;
import com.kota.stratagem.weblayer.common.appuser.AccountAttribute;

public abstract class AbstractRefinerServlet extends HttpServlet implements AccountAttribute {

	@EJB
	private AppUserProtocol appUserProtocol;

	private static final long serialVersionUID = 3731026449748271832L;

	protected static final String TRUE_VALUE = "1";
	protected static final String GET_REQUEST_QUERY_APPENDER = "?id=";
	protected static final String GET_REQUEST_QUERY_EDIT_PARAMETER = "&edit=";

	protected boolean isNumeric(String parameter) {
		boolean valid = false;
		final Scanner sc = new Scanner(parameter.trim());
		if (!sc.hasNextInt(10)) {
			valid = false;
		} else {
			sc.nextInt(10);
			valid = !sc.hasNext();
		}
		sc.close();
		return valid;
	}

	protected void setUserAttributes(HttpServletRequest request) {
		try {
			request.getSession().setAttribute(ATTR_NOTIFICATION_COUNT, this.appUserProtocol.getAppUserNewNotificationCount(request.getRemoteUser()));
		} catch (final AdaptorException e) {
			e.printStackTrace();
		}
	}

}
