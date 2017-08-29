package com.kota.stratagem.messageservice.assembly;

import java.text.MessageFormat;

public class MessageAssembler {

	public static String buildCreationMessage(String structureType, String name, String priority, String deadline, String confidentiality) {
		return MessageFormat.format("A new{2} {0}: {1}, has been created{3}{4}.", structureType, name, "true".equals(confidentiality) ? " private" : "",
				priority != null ? ", with priority level: " + priority + ", and" : "",
				"null".equals(deadline) ? " with no deadline" : ("a deadline of: " + deadline));
	}

	public static String buildAssignmentMessage(String structureType, String name) {
		return MessageFormat.format("You have been assigned to the {0}: {1}.", structureType, name);
	}

	public static String buildDissociationMessage(String structureType, String name) {
		return MessageFormat.format("You have been unassigned from the {0}: {1}.", structureType, name);
	}

	public static String buildModificationBaseMessage(String structureType, String name) {
		return MessageFormat.format("The {0}: {1} has been modified.", structureType, name);
	}

	public static String buildModificationItemMessage(String attributeName, String origin, String result) {
		return MessageFormat.format(" The {0} has been changed from {1}, to: {2}.", "confidential".equals(attributeName) ? "confidentiality" : attributeName,
				origin, result);
	}

	public static String buildAddedAttributeMessage(String attributeName, String result) {
		return MessageFormat.format(" The {0} has been changed to: {1}.", attributeName, result);
	}

	public static String buildRemovedAttributeMessage(String attributeName) {
		return MessageFormat.format(" The {0} has been removed.", attributeName);
	}

	public static String buildDeletionMessage(String structureType, String name) {
		return MessageFormat.format("The {0}: {1} has been deleted.", structureType, name);
	}

	public static String buildDependencyConfigurationMessage(String structureType, String dependant, String dependency) {
		return MessageFormat.format("The dependency {2} has been added to the {0}: {1}.", structureType, dependant, dependency);
	}

	public static String buildDependencyDeconfigurationMessage(String structureType, String dependant, String dependency) {
		return MessageFormat.format("The dependency {2}, has been removed from the {0}: {1}.", structureType, dependant, dependency);
	}

}
