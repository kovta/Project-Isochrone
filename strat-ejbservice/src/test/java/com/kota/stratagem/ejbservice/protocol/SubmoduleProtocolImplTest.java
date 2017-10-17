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
		Mockito.when(submoduleService.readComplete(SUBMODULE_ID)).thenReturn(submodule);
		Mockito.when(submodule.getId()).thenReturn(SUBMODULE_ID);
		final SubmoduleRepresentor convertedRepresentor = Mockito.mock(SubmoduleRepresentor.class);
		Mockito.when(submoduleConverter.toComplete(submodule)).thenReturn(convertedRepresentor);
		final SubmoduleRepresentor managedRepresentor = Mockito.mock(SubmoduleRepresentor.class);
		Mockito.when(extensionManager.prepare(convertedRepresentor)).thenReturn(managedRepresentor);

		final SubmoduleRepresentor submoduleRepresentor = this.protocol.getSubmodule(SUBMODULE_ID);

		Assert.assertNotNull(submoduleRepresentor);
		Assert.assertEquals(managedRepresentor.getId(), submoduleRepresentor.getId());
	}

	@Test
	public void createSubmoduleRepresentorList() throws AdaptorException {
		final List<SubmoduleRepresentor> dependencyConfigurations = new ArrayList<>();
		dependencyConfigurations.add(Mockito.mock(SubmoduleRepresentor.class));
		dependencyConfigurations.add(Mockito.mock(SubmoduleRepresentor.class));
		final SubmoduleRepresentor submoduleParameter = Mockito.mock(SubmoduleRepresentor.class);
		ProjectRepresentor parentProjectRepresentor = Mockito.mock(ProjectRepresentor.class);
		Mockito.when(submoduleParameter.getProject()).thenReturn(parentProjectRepresentor);
		Mockito.when(parentProjectRepresentor.getId()).thenReturn(SUBMODULE_PARENT_PROJECT_ID);
		Project parentProject = Mockito.mock(Project.class);
		Mockito.when(projectService.readWithSubmodules(SUBMODULE_PARENT_PROJECT_ID)).thenReturn(parentProject);
		final Set<Submodule> projectSubmodules = new HashSet<>();
		projectSubmodules.add(Mockito.mock(Submodule.class));
		projectSubmodules.add(Mockito.mock(Submodule.class));
		projectSubmodules.add(Mockito.mock(Submodule.class));
		Mockito.when(parentProject.getSubmodules()).thenReturn(projectSubmodules);
		final Set<SubmoduleRepresentor> convertedSubmodules = new HashSet<>();
		convertedSubmodules.add(Mockito.mock(SubmoduleRepresentor.class));
		convertedSubmodules.add(Mockito.mock(SubmoduleRepresentor.class));
		convertedSubmodules.add(Mockito.mock(SubmoduleRepresentor.class));
		Mockito.when(submoduleConverter.toDispatchable(projectSubmodules)).thenReturn(convertedSubmodules);

		final List<SubmoduleRepresentor> parameterDependencies = new ArrayList<>();
		parameterDependencies.add(Mockito.mock(SubmoduleRepresentor.class));
		final List<SubmoduleRepresentor> parameterDependants = new ArrayList<>();
		parameterDependants.add(Mockito.mock(SubmoduleRepresentor.class));

		final List<SubmoduleRepresentor> compliantConfigurations = new ArrayList<>();
		SubmoduleRepresentor firstCompliantSubmodule = Mockito.mock(SubmoduleRepresentor.class);
		SubmoduleRepresentor secondCompliantSubmodule = Mockito.mock(SubmoduleRepresentor.class);
		compliantConfigurations.add(firstCompliantSubmodule);
		compliantConfigurations.add(secondCompliantSubmodule);

		Mockito.when(extensionManager.prepareBatch(dependencyConfigurations)).thenReturn(new ArrayList(compliantConfigurations));

		final List<SubmoduleRepresentor> submoduleRepresentors = this.protocol.getCompliantDependencyConfigurations(submoduleParameter);

		Assert.assertEquals(submoduleRepresentors.size(), compliantConfigurations.size());
		Assert.assertEquals(submoduleRepresentors.get(0), firstCompliantSubmodule);
		Assert.assertEquals(submoduleRepresentors.get(1), secondCompliantSubmodule);
	}

	@Test
	public void createOrUpdateSubmoduleFromProperties() throws AdaptorException {
		final Long createId = null;
		final Long updateId = 32L;
		Mockito.when(submoduleService.exists(createId)).thenReturn(false);
		Mockito.when(submoduleService.exists(updateId)).thenReturn(true);
		final Long managedId = 33L;
		final String managedName = SUBMODULE_TITLE + "2";
		final String managedDescription = "Test 2";
		final Date managedDeadline = new Date();
		Long parentProjectId = 34L;
		Submodule createdSubmodule = Mockito.mock(Submodule.class);
		Submodule updatedSubmodule = Mockito.mock(Submodule.class);
		Mockito.when(submoduleService.create(managedName, managedDescription, managedDeadline, TECHNICAL_USER, parentProjectId)).thenReturn(createdSubmodule);
		Mockito.when(submoduleService.update(updateId, managedName, managedDescription, managedDeadline, TECHNICAL_USER)).thenReturn(updatedSubmodule);
		SubmoduleRepresentor convertedCreatedSubmodule = Mockito.mock(SubmoduleRepresentor.class);
		SubmoduleRepresentor convertedUpdatedSubmodule = Mockito.mock(SubmoduleRepresentor.class);
		Mockito.when(submoduleConverter.toComplete(createdSubmodule)).thenReturn(convertedCreatedSubmodule);
		Mockito.when(submoduleConverter.toComplete(updatedSubmodule)).thenReturn(convertedUpdatedSubmodule);
		ProjectRepresentor parentProjectRepresentor = Mockito.mock(ProjectRepresentor.class);
		SubmoduleRepresentor managedCreatedProject = new SubmoduleRepresentor(managedId, managedName, managedDescription, managedDeadline, parentProjectRepresentor);
		SubmoduleRepresentor managedUpdatedProject = new SubmoduleRepresentor(updateId, managedName, managedDescription, managedDeadline, parentProjectRepresentor);
		Mockito.when(extensionManager.prepare(convertedCreatedSubmodule)).thenReturn(managedCreatedProject);
		Mockito.when(extensionManager.prepare(convertedUpdatedSubmodule)).thenReturn(managedUpdatedProject);

		final SubmoduleRepresentor createdProjectRepresentor = this.protocol.saveSubmodule(createId, managedName, managedDescription, managedDeadline, TECHNICAL_USER, parentProjectId);
		final SubmoduleRepresentor updatedProjectRepresentor = this.protocol.saveSubmodule(updateId, managedName, managedDescription, managedDeadline, TECHNICAL_USER, parentProjectId);

		Assert.assertEquals(createdProjectRepresentor.getName(), managedCreatedProject.getName());
		Assert.assertEquals(createdProjectRepresentor.getDescription(), managedCreatedProject.getDescription());
		Assert.assertEquals(createdProjectRepresentor.getDeadline(), managedCreatedProject.getDeadline());

		Assert.assertEquals(updatedProjectRepresentor.getName(), managedUpdatedProject.getName());
		Assert.assertEquals(updatedProjectRepresentor.getDescription(), managedUpdatedProject.getDescription());
		Assert.assertEquals(updatedProjectRepresentor.getDeadline(), managedUpdatedProject.getDeadline());
	}

}
