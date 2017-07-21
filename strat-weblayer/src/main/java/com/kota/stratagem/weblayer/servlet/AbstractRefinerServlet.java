package com.kota.stratagem.weblayer.servlet;

import java.util.Scanner;

import javax.servlet.http.HttpServlet;

public abstract class AbstractRefinerServlet extends HttpServlet {

	private static final long serialVersionUID = 3731026449748271832L;

	public boolean isNumeric(String parameter) {
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

}
