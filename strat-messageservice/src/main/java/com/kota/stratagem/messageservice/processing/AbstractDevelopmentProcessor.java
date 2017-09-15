package com.kota.stratagem.messageservice.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;

import org.apache.log4j.Logger;

import com.kota.stratagem.messageservice.assembly.MessageAssembler;
import com.kota.stratagem.persistence.entity.AppUser;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;
import com.kota.stratagem.persistence.service.AppUserService;
import com.kota.stratagem.persistence.service.NotificationService;
import com.kota.stratagem.persistence.util.Constants;

public abstract class AbstractDevelopmentProcessor {

	private static final Logger LOGGER = Logger.getLogger(AbstractDevelopmentProcessor.class);

	@EJB
	protected NotificationService notificationService;

	@EJB
	protected AppUserService appUserService;

	protected Map<String, String> processMessageContent(String message, String operationSelector) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Processing " + operationSelector + " message: " + message);
		}
		message = message.replace("[", "");
		message = message.replace("]", "");
		final Map<String, String> dataMap = new HashMap<>();
		for (final String attribute : message.split(", ")) {
			if (attribute.split("=").length > 1) {
				dataMap.put(attribute.split("=")[0], attribute.split("=")[1]);
			}
		}
		return dataMap;
	}

	protected Long[] extractUserIdentifiers(Set<AppUser> users) {
		final List<Long> identifiers = new ArrayList<Long>();
		for (final AppUser user : users) {
			identifiers.add(user.getId());
		}
		return identifiers.toArray(new Long[identifiers.size()]);
	}

	private boolean empty(String str) {
		return "".equals(str) || (str == "") || (str == null);
	}

	private String divergenceSummarizer(Map<String, String> origin_attributes, Map<String, String> result_attributes) {
		String modificationItems = "";
		if ((origin_attributes.get(Constants.DESCRIPTION_DATA_LABEL) == null) && !this.empty(result_attributes.get(Constants.DESCRIPTION_DATA_LABEL).trim())) {
			modificationItems += MessageAssembler.buildAddedAttributeMessage(Constants.DESCRIPTION_DATA_LABEL,
					result_attributes.get(Constants.DESCRIPTION_DATA_LABEL));
		} else if ((origin_attributes.get(Constants.DESCRIPTION_DATA_LABEL) != null) && (result_attributes.get(Constants.DESCRIPTION_DATA_LABEL) == null)
				&& !"".equals((origin_attributes.get(Constants.DESCRIPTION_DATA_LABEL).trim()))) {
			if (!origin_attributes.get(Constants.DESCRIPTION_DATA_LABEL).equals(result_attributes.get(Constants.DESCRIPTION_DATA_LABEL))) {
				modificationItems += MessageAssembler.buildRemovedAttributeMessage(Constants.DESCRIPTION_DATA_LABEL);
			}
		}
		final Iterator<?> it = origin_attributes.entrySet().iterator();
		while (it.hasNext()) {
			final Map.Entry<?, ?> pair = (Map.Entry<?, ?>) it.next();
			if (!Constants.DESCRIPTION_DATA_LABEL.equals(pair.getKey()) && !result_attributes.get(pair.getKey()).equals(pair.getValue())
					&& !Constants.MODIFIER_ID_DATA_LABEL.equals(pair.getKey()) && !Constants.ID_DATA_LABEL.equals(pair.getKey())) {
				if ((result_attributes.get(pair.getKey()) != null) && (pair.getValue() == null)) {
					modificationItems += MessageAssembler.buildAddedAttributeMessage((String) pair.getKey(), result_attributes.get(pair.getKey()));
				} else if ((result_attributes.get(pair.getKey()) == null) && (pair.getValue() != null)) {
					modificationItems += MessageAssembler.buildRemovedAttributeMessage((String) pair.getKey());
				} else {
					modificationItems += MessageAssembler.buildModificationItemMessage((String) pair.getKey(), (String) pair.getValue(),
							result_attributes.get(pair.getKey()));
				}
			}
			it.remove(); // avoids a ConcurrentModificationException
		}
		return modificationItems;
	}

	protected void handleCreationProperties(final Map<String, String> attributes, final String structureType, Set<AppUser> recipients) {
		try {
			this.notificationService.create(Long.parseLong(attributes.get(Constants.CREATOR_ID_DATA_LABEL)),
					MessageAssembler.buildCreationMessage(structureType, attributes.get(Constants.NAME_DATA_LABEL),
							attributes.get(Constants.PRIORITY_DATA_LABEL), attributes.get(Constants.DEADLINE_DATA_LABEL),
							attributes.get(Constants.CONFIDENTIALITY_DATA_LABEL)),
					this.extractUserIdentifiers(recipients));
		} catch (NumberFormatException | PersistenceServiceException e) {
			e.printStackTrace();
		}
	}

	protected void handleAssignmentProperties(final Map<String, String> attributes, final String structureType, final String structureName) {
		try {
			final Set<AppUser> recipient = new HashSet<>();
			recipient.add(this.appUserService.readElementary(Long.parseLong(attributes.get(Constants.RECIPIENT_ID_DATA_LABEL))));
			this.notificationService.create(Long.parseLong(attributes.get(Constants.ENTRUSTOR_ID_DATA_LABEL)),
					MessageAssembler.buildAssignmentMessage(structureType, structureName), this.extractUserIdentifiers(recipient));
		} catch (NumberFormatException | PersistenceServiceException e) {
			e.printStackTrace();
		}
	}

	protected void handleDissociationProperties(final Map<String, String> attributes, final String structureType, final String structureName) {
		try {
			final Set<AppUser> recipient = new HashSet<>();
			recipient.add(this.appUserService.readElementary(Long.parseLong(attributes.get(Constants.RECIPIENT_ID_DATA_LABEL))));
			this.notificationService.create(Long.parseLong(attributes.get(Constants.ENTRUSTOR_ID_DATA_LABEL)),
					MessageAssembler.buildDissociationMessage(structureType, structureName), this.extractUserIdentifiers(recipient));
		} catch (NumberFormatException | PersistenceServiceException e) {
			e.printStackTrace();
		}
	}

	protected void handleModificationProperties(final Map<String, String> origin_attributes, final Map<String, String> result_attributes,
			final String structureType, Set<AppUser> recipients) {
		try {
			this.notificationService.create(Long.parseLong(result_attributes.get(Constants.MODIFIER_ID_DATA_LABEL)),
					MessageAssembler.buildModificationBaseMessage(structureType, origin_attributes.get(Constants.NAME_DATA_LABEL))
							+ this.divergenceSummarizer(origin_attributes, result_attributes),
					this.extractUserIdentifiers(recipients));
		} catch (NumberFormatException | PersistenceServiceException e) {
			e.printStackTrace();
		}
	}

	protected void handleDeletionProperties(final Map<String, String> attributes, final String structureType, String operator, Set<AppUser> recipients) {
		try {
			this.notificationService.create(this.appUserService.readElementary(operator).getId(),
					MessageAssembler.buildDeletionMessage(structureType, attributes.get(Constants.NAME_DATA_LABEL)), this.extractUserIdentifiers(recipients));
		} catch (NumberFormatException | PersistenceServiceException e) {
			e.printStackTrace();
		}
	}

	protected void handleDependencyConfigurationProperties(final Map<String, String> origin_attributes, final Map<String, String> result_attributes,
			final String structureType, Set<AppUser> recipients) {
		try {
			this.notificationService.create(
					Long.parseLong(result_attributes.get(Constants.MODIFIER_ID_DATA_LABEL)), MessageAssembler.buildDependencyConfigurationMessage(structureType,
							origin_attributes.get(Constants.NAME_DATA_LABEL), result_attributes.get(Constants.NAME_DATA_LABEL)),
					this.extractUserIdentifiers(recipients));
		} catch (NumberFormatException | PersistenceServiceException e) {
			e.printStackTrace();
		}
	}

	protected void handleDependencyDeconfigurationProperties(final Map<String, String> origin_attributes, final Map<String, String> result_attributes,
			final String structureType, Set<AppUser> recipients) {
		try {
			this.notificationService.create(Long.parseLong(result_attributes.get(Constants.MODIFIER_ID_DATA_LABEL)),
					MessageAssembler.buildDependencyDeconfigurationMessage(structureType, origin_attributes.get(Constants.NAME_DATA_LABEL),
							result_attributes.get(Constants.NAME_DATA_LABEL)),
					this.extractUserIdentifiers(recipients));
		} catch (NumberFormatException | PersistenceServiceException e) {
			e.printStackTrace();
		}
	}

}
