package com.kota.stratagem.messageservice.assembly;

import java.text.MessageFormat;

public class MessageAssembler {

	public static String buildCreationMessage(String structureType, String name, String priority, String deadline, String confidentiality) {
		return MessageFormat.format("A new {1} {0} has been created, with priority level: {2}, and {3}.", structureType,
				confidentiality == "true" ? "private" : "", priority, deadline == "null" ? "no deadline" : "a deadline of: " + deadline);
	}

}
