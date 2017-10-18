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

import com.kota.stratagem.ejbservice.converter.SubmoduleConverter;
import com.kota.stratagem.ejbservice.core.AbstractMockTest;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.SubmoduleService;

public class SubmoduleProtocolImplTest extends AbstractMockTest {

	private static final Long SUBMODULE_ID = 10L;

	private static final Long SUBMODULE_PARENT_PROJECT_ID = 11L;

	private static final String SUBMODULE_TITLE = "[TEST_SUBMODULE]-";

	@InjectMocks
	private SubmoduleProtocolImpl protocol;

	@Mock
	private ProjectService projectService;

	@Mock
	private SubmoduleService submoduleService;

	@Mock
	private SubmoduleConverter submoduleConverter;

	@Mock
	private DTOExtensionManager extensionManager;

	@Test
	public void createSubmoduleRepresentorFromSubmoduleId() throws AdaptorException {
		final Submodule submodule = Mockito.mock(Submodule.class);
		Mockito.when(this.submoduleService.readComplete(SUBMODULE_ID)).thenReturn(submodule);
		Mockito.when(submodule.getId()).thenReturn(SUBMODULE_ID);
		final SubmoduleRepresentor convertedRepresentor = Mockito.mock(SubmoduleRepresentor.class);
		Mockito.when(this.submoduleConverter.toComplete(submodule)).thenReturn(convertedRepresentor);
		final SubmoduleRepresentor managedRepresentor = Mockito.mock(SubmoduleRepresentor.class);
		Mockito.when(this.extensionManager.prepare(convertedRepresentor)).thenReturn(managedRepresentor);

		final SubmoduleRepresentor submoduleRepresentor = this.protocol.getSubmodule(SUBMODULE_ID);

		Assert.assertNotNull(submoduleRepresentor);
		Assert.assertEquals(managedRepresentor.getId(), submoduleRepresentor.getId());
	}

	@Test
	public void createConfigurableSubmoduleRepresentorList() throws AdaptorException {
		final ProjectRepresentor parentProjectRepresentor = Mockito.mock(ProjectRepresentor.class);
		final Long managedIdA = 21L;
		final Long managedIdB = 22L;
		final Long managedIdC = 23L;
		final Long managedIdD = 24L;
		final String managedName = SUBMODULE_TITLE + "2";
		final String managedDescription = "Test 2";
		final Date managedDeadline = new Date();
		final SubmoduleRepresentor submoduleParameter = new SubmoduleRepresentor(managedIdA, managedName, managedDescription, managedDeadline,
				parentProjectRepresentor);
		Mockito.when(parentProjectRepresentor.getId()).thenReturn(SUBMODULE_PARENT_PROJECT_ID);
		final Project parentProject = Mockito.mock(Project.class);
		Mockito.when(this.projectService.readWithSubmodules(SUBMODULE_PARENT_PROJECT_ID)).thenReturn(parentProject);
		final Set<Submodule> projectSubmodules = new HashSet<>();
		projectSubmodules.add(Mockito.mock(Submodule.class));
		projectSubmodules.add(Mockito.mock(Submodule.class));
		projectSubmodules.add(Mockito.mock(Submodule.class));
		projectSubmodules.add(Mockito.mock(Submodule.class));
		Mockito.when(parentProject.getSubmodules()).thenReturn(projectSubmodules);
		final SubmoduleRepresentor projectLevelSubmoduleRepresentorB = new SubmoduleRepresentor(managedIdB, SUBMODULE_TITLE + 3, managedDescription,
				managedDeadline, parentProjectRepresentor);
		final SubmoduleRepresentor projectLevelSubmoduleRepresentorC = new SubmoduleRepresentor(managedIdC, SUBMODULE_TITLE + 4, managedDescription,
				managedDeadline, parentProjectRepresentor);
		final SubmoduleRepresentor projectLevelSubmoduleRepresentorD = new SubmoduleRepresentor(managedIdD, SUBMODULE_TITLE + 5, managedDescription,
				managedDeadline, parentProjectRepresentor);
		final Set<SubmoduleRepresentor> convertedSubmodules = new HashSet<>();
		convertedSubmodules.add(submoduleParameter);
		convertedSubmodules.add(projectLevelSubmoduleRepresentorB);
		convertedSubmodules.add(projectLevelSubmoduleRepresentorC);
		convertedSubmodules.add(projectLevelSubmoduleRepresentorD);
		Mockito.when(this.submoduleConverter.toElementary(projectSubmodules)).thenReturn(convertedSubmodules);
		final List<SubmoduleRepresentor> dependencyConfigurations = new ArrayList<>();
		dependencyConfigurations.addAll(convertedSubmodules);
		dependencyConfigurations.remove(submoduleParameter);
		final List<List<SubmoduleRepresentor>> parameterDependencies = new ArrayList<>();
		final List<SubmoduleRepresentor> dependencyLevel = new ArrayList<>();
		dependencyLevel.add(projectLevelSubmoduleRepresentorB);
		parameterDependencies.add(dependencyLevel);
		final List<List<SubmoduleRepresentor>> parameterDependants = new ArrayList<>();
		final List<SubmoduleRepresentor> dependantLevel = new ArrayList<>();
		dependencyLevel.add(projectLevelSubmoduleRepresentorC);
		parameterDependencies.add(dependantLevel);
		submoduleParameter.setDependantChain(parameterDependencies);
		submoduleParameter.setDependantChain(parameterDependants);
		final List<SubmoduleRepresentor> compliantConfigurations = new ArrayList<>();
		compliantConfigurations.add(projectLevelSubmoduleRepresentorD);
		Mockito.when(this.extensionManager.prepareBatch(dependencyConfigurations)).thenReturn(new ArrayList(compliantConfigurations));

		final List<SubmoduleRepresentor> submoduleRepresentors = this.protocol.getCompliantDependencyConfigurations(submoduleParameter);

		Assert.assertEquals(submoduleRepresentors.size(), compliantConfigurations.size());
		Assert.assertEquals(submoduleRepresentors.get(0), projectLevelSubmoduleRepresentorD);
	}

	@Test
	public void createOrUpdateSubmoduleFromProperties() throws AdaptorException {
		final Long createId = null;
		final Long updateId = 32L;
		Mockito.when(this.submoduleService.exists(createId)).thenReturn(false);
		Mockito.when(this.submoduleService.exists(updateId)).thenReturn(true);
		final Long managedId = 33L;
		final String managedName = SUBMODULE_TITLE + "3";
		final String managedDescription = "Test 3";
		final Date managedDeadline = new Date();
		final Long parentProjectId = 34L;
		final Submodule createdSubmodule = Mockito.mock(Submodule.class);
		final Submodule updatedSubmodule = Mockito.mock(Submodule.class);
		Mockito.when(this.submoduleService.create(managedName, managedDescription, managedDeadline, TECHNICAL_USER, parentProjectId))
				.thenReturn(createdSubmodule);
		Mockito.when(this.submoduleService.update(updateId, managedName, managedDescription, managedDeadline, TECHNICAL_USER)).thenReturn(updatedSubmodule);
		final SubmoduleRepresentor convertedCreatedSubmodule = Mockito.mock(SubmoduleRepresentor.class);
		final SubmoduleRepresentor convertedUpdatedSubmodule = Mockito.mock(SubmoduleRepresentor.class);
		Mockito.when(this.submoduleConverter.toComplete(createdSubmodule)).thenReturn(convertedCreatedSubmodule);
		Mockito.when(this.submoduleConverter.toComplete(updatedSubmodule)).thenReturn(convertedUpdatedSubmodule);
		final ProjectRepresentor parentProjectRepresentor = Mockito.mock(ProjectRepresentor.class);
		final SubmoduleRepresentor managedCreatedProject = new SubmoduleRepresentor(managedId, managedName, managedDescription, managedDeadline,
				parentProjectRepresentor);
		final SubmoduleRepresentor managedUpdatedProject = new SubmoduleRepresentor(updateId, managedName, managedDescription, managedDeadline,
				parentProjectRepresentor);
		Mockito.when(this.extensionManager.prepare(convertedCreatedSubmodule)).thenReturn(managedCreatedProject);
		Mockito.when(this.extensionManager.prepare(convertedUpdatedSubmodule)).thenReturn(managedUpdatedProject);

		final SubmoduleRepresentor createdProjectRepresentor = this.protocol.saveSubmodule(createId, managedName, managedDescription, managedDeadline,
				TECHNICAL_USER, parentProjectId);
		final SubmoduleRepresentor updatedProjectRepresentor = this.protocol.saveSubmodule(updateId, managedName, managedDescription, managedDeadline,
				TECHNICAL_USER, parentProjectId);

		Assert.assertEquals(createdProjectRepresentor.getName(), managedCreatedProject.getName());
		Assert.assertEquals(createdProjectRepresentor.getDescription(), managedCreatedProject.getDescription());
		Assert.assertEquals(createdProjectRepresentor.getDeadline(), managedCreatedProject.getDeadline());

		Assert.assertEquals(updatedProjectRepresentor.getName(), managedUpdatedProject.getName());
		Assert.assertEquals(updatedProjectRepresentor.getDescription(), managedUpdatedProject.getDescription());
		Assert.assertEquals(updatedProjectRepresentor.getDeadline(), managedUpdatedProject.getDeadline());
	}

}
