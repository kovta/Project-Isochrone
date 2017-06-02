package com.kota.stratagem.weblayer.tag;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PriceFormatterTag extends SimpleTagSupport {

	private static final String CURRENCY_SIGN = "Ft";

	private Double value;

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException {
		final NumberFormat formatter = new DecimalFormat("#");
		this.getJspContext().getOut().write(formatter.format(this.value) + " " + CURRENCY_SIGN);
	}

}
