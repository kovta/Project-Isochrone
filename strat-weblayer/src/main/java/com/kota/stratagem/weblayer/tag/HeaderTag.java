package com.kota.stratagem.weblayer.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class HeaderTag extends SimpleTagSupport {

	private StringWriter sw = new StringWriter();

	public void doTag() throws JspException, IOException {
		getJspBody().invoke(sw);
		JspWriter out = getJspContext().getOut();
		out.println("<h1>" + sw.toString() + "</h1>");
	}

}
