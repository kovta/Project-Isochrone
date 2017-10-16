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

import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.core.AbstractMockTest;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.catalog.ProjectStatusRepresentor;
import com.kota.stratagem.ejbserviceclient.exception.ServiceException;
import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.service.ProjectService;

public class ProjectProtocolImplTest extends AbstractMockTest {

	private static final Long PROJECT_ID = 10L;

	private static final String PROJECT_TITLE = "[TEST_PROJECT]-";

	@InjectMocks
	private ProjectProtocolImpl protocol;

	@Mock
	private ProjectService projectService;

	@Mock
	private ProjectConverter projectConverter;

	@Mock
	private DTOExtensionManager extensionManager;

	@Test
	public void createObjectiveRepresentorFromObjectiveId() throws AdaptorException {
		final Project project = Mockito.mock(Project.class);
		Mockito.when(projectService.readComplete(PROJECT_ID)).thenReturn(project);
		Mockito.when(project.getId()).thenReturn(PROJECT_ID);
		final ProjectRepresentor convertedRepresentor = Mockito.mock(ProjectRepresentor.class);
		Mockito.when(projectConverter.toComplete(project)).thenReturn(convertedRepresentor);
		final ProjectRepresentor managedRepresentor = Mockito.mock(ProjectRepresentor.class);
		Mockito.when(extensionManager.prepare(convertedRepresentor)).thenReturn(managedRepresentor);

		final ProjectRepresentor projectRepresentor = this.protocol.getProject(PROJECT_ID);

		Assert.assertNotNull(projectRepresentor);
		Assert.assertEquals(managedRepresentor.getId(), projectRepresentor.getId());
	}

	@Test
	public void createObjectiveRepresentorList() throws ServiceException, AdaptorException {
		final Set<Project> results = new HashSet<>();
		Objective parentObjective = Mockito.mock(Objective.class);
		results.add(new Project(20L, PROJECT_TITLE + "1", "Test 1", ProjectStatus.TESTING, null, true, new Date(), new Date(), parentObjective));
		results.add(new Project(21L, PROJECT_TITLE + "2", "Test 2", ProjectStatus.TESTING, null, true, new Date(), new Date(), parentObjective));
		Mockito.when(projectService.readAll()).thenReturn(results);
		final Set<ProjectRepresentor> convertedProjects = new HashSet<>();
		convertedProjects.add(Mockito.mock(ProjectRepresentor.class));
		convertedProjects.add(Mockito.mock(ProjectRepresentor.class));
		Mockito.when(projectConverter.toSimplified(results)).thenReturn(convertedProjects);
		final List<ProjectRepresentor> managedRepresentors = new ArrayList<>();
		final ProjectRepresentor firstManagedRepresentor = Mockito.mock(ProjectRepresentor.class);
		managedRepresentors.add(firstManagedRepresentor);
		final Long managedId = 21L;
		final String managedName = PROJECT_TITLE + "2";
		final String managedDescription = "Test 2";
		final ProjectStatusRepresentor managedStatus = ProjectStatusRepresentor.TESTING;
		final Date managedDeadline = new Date();
		final Boolean managedConfidentiality = false;
		ObjectiveRepresentor parentObjectiveRepresentor = Mockito.mock(ObjectiveRepresentor.class);
		managedRepresentors.add(new ProjectRepresentor(managedId, managedName, managedDescription, managedStatus, managedDeadline, managedConfidentiality, parentObjectiveRepresentor));
		Mockito.when(extensionManager.prepareBatch(new ArrayList<ProjectRepresentor>(convertedProjects))).thenReturn(new ArrayList(managedRepresentors));

		final List<ObjectiveRepresentor> objectiveRepresentors = this.protocol.getObjectiveProjectClusters();

		Assert.assertEquals(objectiveRepresentors.size(), managedRepresentors.size());
		Assert.assertEquals(objectiveRepresentors.get(0), firstManagedRepresentor);
		Assert.assertEquals(objectiveRepresentors.get(1).getId(), managedId);
		Assert.assertEquals(objectiveRepresentors.get(1).getName(), managedName);
		Assert.assertEquals(objectiveRepresentors.get(1).getDescription(), managedDescription);
		Assert.assertEquals(objectiveRepresentors.get(1).getStatus(), managedStatus);
		Assert.assertEquals(objectiveRepresentors.get(1).getDeadline(), managedDeadline);
		Assert.assertEquals(objectiveRepresentors.get(1).getConfidential(), managedConfidentiality);
	}

}
