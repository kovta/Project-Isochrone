package com.kota.stratagem.weblayer.util;

import java.util.Scanner;

public class RequestRefiner {

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
