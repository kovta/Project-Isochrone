package com.kota.stratagem.weblayer.servlet.assignment;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kota.stratagem.ejbservice.protocol.AppUserAssignmentProtocol;
import com.kota.stratagem.weblayer.common.assignment.AssignmentAttribute;
import com.kota.stratagem.weblayer.common.assignment.AssignmentParameter;

@WebServlet("/AssignmentDelete")
public class AssignmentDeleteServlet extends HttpServlet implements AssignmentParameter, AssignmentAttribute {

	private static final long serialVersionUID = -2010345699510377465L;

	private static final Logger LOGGER = Logger.getLogger(AssignmentDeleteServlet.class);

	private static final String GET_REQUEST_QUERY_APPENDER = "?id=";

	@EJB
	AppUserAssignmentProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
