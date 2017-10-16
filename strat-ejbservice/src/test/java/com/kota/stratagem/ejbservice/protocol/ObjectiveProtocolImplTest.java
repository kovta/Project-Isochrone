package com.kota.stratagem.ejbservice.protocol;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.kota.stratagem.ejbservice.converter.ObjectiveConverter;
import com.kota.stratagem.ejbservice.core.AbstractMockTest;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ObjectiveStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.service.ObjectiveService;

public class ObjectiveProtocolImplTest extends AbstractMockTest {

	private static final Long OBJECTIVE_ID = 10L;

	private static final String OBJECTIVE_TITLE = "[TEST_OBJECTIVE]-";

	@InjectMocks
	private ObjectiveProtocolImpl protocol;

	@Mock
	private ObjectiveService objectiveService;

	@Mock
	private ObjectiveConverter objectiveConverter;

	@Mock
	private DTOExtensionManager extensionManager;

	@Test
	public void createObjectiveRepresentorFromObjectiveId() throws ServiceException {
		final Objective objective = Mockito.mock(Objective.class);
		Mockito.when(objectiveService.readComplete(OBJECTIVE_ID)).thenReturn(objective);
		Mockito.when(objective.getId()).thenReturn(OBJECTIVE_ID);
		final ObjectiveRepresentor convertedRepresentor = Mockito.mock(ObjectiveRepresentor.class);
		Mockito.when(objectiveConverter.toComplete(objective)).thenReturn(convertedRepresentor);
		final ObjectiveRepresentor managedRepresentor = Mockito.mock(ObjectiveRepresentor.class);
		Mockito.when(extensionManager.prepare(convertedRepresentor)).thenReturn(managedRepresentor);

		final ObjectiveRepresentor objectiveRepresentor = this.protocol.getObjective(OBJECTIVE_ID);

		Assert.assertNotNull(objectiveRepresentor);
		Assert.assertEquals(managedRepresentor.getId(), objectiveRepresentor.getId());
	}

	@Test
	public void createObjectiveRepresentorList() throws AdaptorException {
		final Set<Objective> results = new HashSet<>();
		results.add(new Objective(20L, OBJECTIVE_TITLE + "1", "Test 1", 10, ObjectiveStatus.PLANNED, null, true, new Date(), new Date()));
		results.add(new Objective(21L, OBJECTIVE_TITLE + "2", "Test 2", 10, ObjectiveStatus.PLANNED, null, true, new Date(), new Date()));
		Mockito.when(objectiveService.readAll()).thenReturn(results);
		final Set<ObjectiveRepresentor> convertedObjectives = new HashSet<>();
		convertedObjectives.add(Mockito.mock(ObjectiveRepresentor.class));
		convertedObjectives.add(Mockito.mock(ObjectiveRepresentor.class));
		Mockito.when(objectiveConverter.toSimplified(results)).thenReturn(convertedObjectives);
		final List<ObjectiveRepresentor> managedRepresentors = new ArrayList<>();
		final ObjectiveRepresentor firstManagedRepresentor = Mockito.mock(ObjectiveRepresentor.class);
		managedRepresentors.add(firstManagedRepresentor);
		final Long managedId = 21L;
		final String managedName = OBJECTIVE_TITLE + "2";
		final String managedDescription = "Test 2";
		final int managedPriority = 10;
		final ObjectiveStatusRepresentor managedStatus = ObjectiveStatusRepresentor.PLANNED;
		final Date managedDeadline = new Date();
		final Boolean managedConfidentiality = false;
		managedRepresentors.add(new ObjectiveRepresentor(managedId, managedName, managedDescription, managedPriority, managedStatus, managedDeadline, managedConfidentiality));
		Mockito.when(extensionManager.prepareBatch(new ArrayList<ObjectiveRepresentor>(convertedObjectives))).thenReturn(new ArrayList(managedRepresentors));

		final List<ObjectiveRepresentor> objectiveRepresentors = this.protocol.getAllObjectives();

		Assert.assertEquals(objectiveRepresentors.size(), managedRepresentors.size());
		Assert.assertEquals(objectiveRepresentors.get(0), firstManagedRepresentor);
		Assert.assertEquals(objectiveRepresentors.get(1).getId(), managedId);
		Assert.assertEquals(objectiveRepresentors.get(1).getName(), managedName);
		Assert.assertEquals(objectiveRepresentors.get(1).getDescription(), managedDescription);
		Assert.assertEquals(objectiveRepresentors.get(1).getPriority(), managedPriority);
		Assert.assertEquals(objectiveRepresentors.get(1).getStatus(), managedStatus);
		Assert.assertEquals(objectiveRepresentors.get(1).getDeadline(), managedDeadline);
		Assert.assertEquals(objectiveRepresentors.get(1).getConfidential(), managedConfidentiality);
	}

	@Test
	public void createOrUpdateObjectiveFromProperties() throws AdaptorException {
		final Long createId = null;
		final Long updateId = 32L;
		Mockito.when(objectiveService.exists(createId)).thenReturn(false);
		Mockito.when(objectiveService.exists(updateId)).thenReturn(true);
		final Long managedId = 21L;
		final String managedName = OBJECTIVE_TITLE + "2";
		final String managedDescription = "Test 2";
		final int managedPriority = 10;
		final ObjectiveStatus managedStatus = ObjectiveStatus.PLANNED;
		final Date managedDeadline = new Date();
		final Boolean managedConfidentiality = false;
		Objective createdObjective = Mockito.mock(Objective.class);
		Objective updatedObjective = Mockito.mock(Objective.class);
		Mockito.when(objectiveService.create(managedName, managedDescription, managedPriority, managedStatus, managedDeadline, managedConfidentiality, TECHNICAL_USER)).thenReturn(createdObjective);
		Mockito.when(objectiveService.update(updateId, managedName, managedDescription, managedPriority, managedStatus, managedDeadline, managedConfidentiality, TECHNICAL_USER))
				.thenReturn(updatedObjective);
		ObjectiveRepresentor convertedCreatedObjective = Mockito.mock(ObjectiveRepresentor.class);
		ObjectiveRepresentor convertedUpdatedObjective = Mockito.mock(ObjectiveRepresentor.class);
		Mockito.when(objectiveConverter.toComplete(createdObjective)).thenReturn(convertedCreatedObjective);
		Mockito.when(objectiveConverter.toComplete(updatedObjective)).thenReturn(convertedUpdatedObjective);
		ObjectiveStatusRepresentor managedStatusRepresentor = ObjectiveStatusRepresentor.PLANNED;
		ObjectiveRepresentor managedCreatedObjective = new ObjectiveRepresentor(managedId, managedName, managedDescription, managedPriority, managedStatusRepresentor, managedDeadline,
				managedConfidentiality);
		ObjectiveRepresentor managedUpdatedObjective = new ObjectiveRepresentor(updateId, managedName, managedDescription, managedPriority, managedStatusRepresentor, managedDeadline,
				managedConfidentiality);
		Mockito.when(extensionManager.prepare(convertedCreatedObjective)).thenReturn(managedCreatedObjective);
		Mockito.when(extensionManager.prepare(convertedUpdatedObjective)).thenReturn(managedUpdatedObjective);

		final ObjectiveRepresentor createdObjectiveRepresentor = this.protocol.saveObjective(createId, managedName, managedDescription, managedPriority, managedStatusRepresentor, managedDeadline,
				managedConfidentiality, TECHNICAL_USER);
		final ObjectiveRepresentor updatedObjectiveRepresentor = this.protocol.saveObjective(updateId, managedName, managedDescription, managedPriority, managedStatusRepresentor, managedDeadline,
				managedConfidentiality, TECHNICAL_USER);

		Assert.assertEquals(createdObjectiveRepresentor.getName(), managedCreatedObjective.getName());
		Assert.assertEquals(createdObjectiveRepresentor.getDescription(), managedCreatedObjective.getDescription());
		Assert.assertEquals(createdObjectiveRepresentor.getPriority(), managedCreatedObjective.getPriority());
		Assert.assertEquals(createdObjectiveRepresentor.getStatus(), managedCreatedObjective.getStatus());
		Assert.assertEquals(createdObjectiveRepresentor.getDeadline(), managedCreatedObjective.getDeadline());
		Assert.assertEquals(createdObjectiveRepresentor.getConfidential(), managedCreatedObjective.getConfidential());

		Assert.assertEquals(updatedObjectiveRepresentor.getName(), managedUpdatedObjective.getName());
		Assert.assertEquals(updatedObjectiveRepresentor.getDescription(), managedUpdatedObjective.getDescription());
		Assert.assertEquals(updatedObjectiveRepresentor.getPriority(), managedUpdatedObjective.getPriority());
		Assert.assertEquals(updatedObjectiveRepresentor.getStatus(), managedUpdatedObjective.getStatus());
		Assert.assertEquals(updatedObjectiveRepresentor.getDeadline(), managedUpdatedObjective.getDeadline());
		Assert.assertEquals(updatedObjectiveRepresentor.getConfidential(), managedUpdatedObjective.getConfidential());
	}

}
