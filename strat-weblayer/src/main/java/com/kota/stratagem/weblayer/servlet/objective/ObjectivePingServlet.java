package com.kota.stratagem.weblayer.servlet.objective;

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

import com.kota.stratagem.ejbservice.protocol.ObjectiveProtocol;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;

@WebServlet("/ObjectivePing")
public class ObjectivePingServlet extends HttpServlet {

	private static final long serialVersionUID = 1958561648907457635L;

	private static final Logger LOGGER = Logger.getLogger(ObjectivePingServlet.class);

	@EJB
	private ObjectiveProtocol protocol;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.info("Get objectives");
		response.setCharacterEncoding("UTF-8");
		final PrintWriter out = response.getWriter();
		try {
			final List<ObjectiveRepresentor> objectives = this.protocol.getAllObjectives();
			for (final ObjectiveRepresentor representor : objectives) {
				out.println(representor.toString());
			}
		} catch (final Exception e) {
			LOGGER.error(e, e);
			out.println(e.getLocalizedMessage());
		}
		out.close();
	}
}
