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
	public void createProjectRepresentorFromProjectId() throws AdaptorException {
		final Project project = Mockito.mock(Project.class);
		Mockito.when(this.projectService.readComplete(PROJECT_ID)).thenReturn(project);
		Mockito.when(project.getId()).thenReturn(PROJECT_ID);
		final ProjectRepresentor convertedRepresentor = Mockito.mock(ProjectRepresentor.class);
		Mockito.when(this.projectConverter.toComplete(project)).thenReturn(convertedRepresentor);
		final ProjectRepresentor managedRepresentor = Mockito.mock(ProjectRepresentor.class);
		Mockito.when(this.extensionManager.prepare(convertedRepresentor)).thenReturn(managedRepresentor);

		final ProjectRepresentor projectRepresentor = this.protocol.getProject(PROJECT_ID);

		Assert.assertNotNull(projectRepresentor);
		Assert.assertEquals(managedRepresentor.getId(), projectRepresentor.getId());
	}

	@Test
	public void createObjectiveRepresentorList() throws ServiceException, AdaptorException {
		final Set<Project> results = new HashSet<>();
		final Objective parentObjective = Mockito.mock(Objective.class);
		results.add(new Project(20L, PROJECT_TITLE + "1", "Test 1", ProjectStatus.TESTING, null, true, new Date(), new Date(), parentObjective));
		results.add(new Project(21L, PROJECT_TITLE + "2", "Test 2", ProjectStatus.TESTING, null, true, new Date(), new Date(), parentObjective));
		Mockito.when(this.projectService.readAll()).thenReturn(results);

		final Set<ProjectRepresentor> convertedProjects = new HashSet<>();
		final Long managedId = 21L;
		final String managedName = PROJECT_TITLE + "2";
		final String managedDescription = "Test 2";
		final ProjectStatusRepresentor managedProjcetStatus = ProjectStatusRepresentor.TESTING;
		final Date managedDeadline = new Date();
		final Boolean managedConfidentiality = false;

		final List<ObjectiveRepresentor> representorClusters = new ArrayList<>();
		final ObjectiveRepresentor parentObjectiveRepresentor = Mockito.mock(ObjectiveRepresentor.class);
		representorClusters.add(parentObjectiveRepresentor);

		final ProjectRepresentor firstConvertedProject = Mockito.mock(ProjectRepresentor.class);
		final ProjectRepresentor secondConvertedProject = new ProjectRepresentor(managedId, managedName, managedDescription, managedProjcetStatus,
				managedDeadline, managedConfidentiality, parentObjectiveRepresentor);
		convertedProjects.add(firstConvertedProject);
		convertedProjects.add(secondConvertedProject);
		Mockito.when(firstConvertedProject.getObjective()).thenReturn(parentObjectiveRepresentor);
		Mockito.when(this.projectConverter.toSimplified(results)).thenReturn(convertedProjects);

		final List<ObjectiveRepresentor> managedRepresentors = new ArrayList<>();
		final List<ProjectRepresentor> convertedProjectRepresentors = new ArrayList<>(convertedProjects);
		Mockito.when(parentObjectiveRepresentor.getProjects()).thenReturn(new ArrayList(convertedProjectRepresentors));
		managedRepresentors.add(parentObjectiveRepresentor);
		Mockito.when(this.extensionManager.prepareBatch(representorClusters)).thenReturn(new ArrayList(managedRepresentors));

		final List<ObjectiveRepresentor> objectiveRepresentors = this.protocol.getObjectiveProjectClusters();

		Assert.assertEquals(objectiveRepresentors.size(), managedRepresentors.size());
		Assert.assertEquals(objectiveRepresentors.get(0), parentObjectiveRepresentor);
		Assert.assertEquals(objectiveRepresentors.get(0).getProjects().size(), convertedProjects.size());
		Assert.assertTrue(objectiveRepresentors.get(0).getProjects().contains(firstConvertedProject));
		Assert.assertTrue(objectiveRepresentors.get(0).getProjects().contains(secondConvertedProject));
	}

	@Test
	public void createOrUpdateProjectFromProperties() throws AdaptorException {
		final Long createId = null;
		final Long updateId = 32L;
		Mockito.when(this.projectService.exists(createId)).thenReturn(false);
		Mockito.when(this.projectService.exists(updateId)).thenReturn(true);
		final Long managedId = 33L;
		final String managedName = PROJECT_TITLE + "2";
		final String managedDescription = "Test 2";
		final ProjectStatus managedStatus = ProjectStatus.TESTING;
		final Date managedDeadline = new Date();
		final Boolean managedConfidentiality = false;
		final Long parentObjectiveId = 34L;
		final Project createdProject = Mockito.mock(Project.class);
		final Project updatedProject = Mockito.mock(Project.class);
		Mockito.when(this.projectService.create(managedName, managedDescription, managedStatus, managedDeadline, managedConfidentiality, TECHNICAL_USER,
				parentObjectiveId)).thenReturn(createdProject);
		Mockito.when(
				this.projectService.update(updateId, managedName, managedDescription, managedStatus, managedDeadline, managedConfidentiality, TECHNICAL_USER))
				.thenReturn(updatedProject);
		final ProjectRepresentor convertedCreatedProject = Mockito.mock(ProjectRepresentor.class);
		final ProjectRepresentor convertedUpdatedProject = Mockito.mock(ProjectRepresentor.class);
		Mockito.when(this.projectConverter.toComplete(createdProject)).thenReturn(convertedCreatedProject);
		Mockito.when(this.projectConverter.toComplete(updatedProject)).thenReturn(convertedUpdatedProject);
		final ProjectStatusRepresentor managedStatusRepresentor = ProjectStatusRepresentor.TESTING;
		final ObjectiveRepresentor parentObjectiveRepresentor = Mockito.mock(ObjectiveRepresentor.class);
		final ProjectRepresentor managedCreatedProject = new ProjectRepresentor(managedId, managedName, managedDescription, managedStatusRepresentor,
				managedDeadline, managedConfidentiality, parentObjectiveRepresentor);
		final ProjectRepresentor managedUpdatedProject = new ProjectRepresentor(updateId, managedName, managedDescription, managedStatusRepresentor,
				managedDeadline, managedConfidentiality, parentObjectiveRepresentor);
		Mockito.when(this.extensionManager.prepare(convertedCreatedProject)).thenReturn(managedCreatedProject);
		Mockito.when(this.extensionManager.prepare(convertedUpdatedProject)).thenReturn(managedUpdatedProject);

		final ProjectRepresentor createdProjectRepresentor = this.protocol.saveProject(createId, managedName, managedDescription, managedStatusRepresentor,
				managedDeadline, managedConfidentiality, TECHNICAL_USER, parentObjectiveId);
		final ProjectRepresentor updatedProjectRepresentor = this.protocol.saveProject(updateId, managedName, managedDescription, managedStatusRepresentor,
				managedDeadline, managedConfidentiality, TECHNICAL_USER, parentObjectiveId);

		Assert.assertEquals(createdProjectRepresentor.getName(), managedCreatedProject.getName());
		Assert.assertEquals(createdProjectRepresentor.getDescription(), managedCreatedProject.getDescription());
		Assert.assertEquals(createdProjectRepresentor.getStatus(), managedCreatedProject.getStatus());
		Assert.assertEquals(createdProjectRepresentor.getDeadline(), managedCreatedProject.getDeadline());
		Assert.assertEquals(createdProjectRepresentor.getConfidential(), managedCreatedProject.getConfidential());

		Assert.assertEquals(updatedProjectRepresentor.getName(), managedUpdatedProject.getName());
		Assert.assertEquals(updatedProjectRepresentor.getDescription(), managedUpdatedProject.getDescription());
		Assert.assertEquals(updatedProjectRepresentor.getStatus(), managedUpdatedProject.getStatus());
		Assert.assertEquals(updatedProjectRepresentor.getDeadline(), managedUpdatedProject.getDeadline());
		Assert.assertEquals(updatedProjectRepresentor.getConfidential(), managedUpdatedProject.getConfidential());
	}

}
