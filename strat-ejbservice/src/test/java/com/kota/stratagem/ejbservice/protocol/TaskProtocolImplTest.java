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

import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.core.AbstractMockTest;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.SubmoduleService;
import com.kota.stratagem.persistence.service.TaskService;

public class TaskProtocolImplTest extends AbstractMockTest {

	private static final Long TASK_ID = 10L;

	private static final String TASK_TITLE = "[TEST_TASK]-";

	private static final Long TASK_PARENT_SUBMODULE_ID = 11L;

	@InjectMocks
	private TaskProtocolImpl protocol;

	@Mock
	private ObjectiveService objectiveService;

	@Mock
	private ProjectService projectService;

	@Mock
	private SubmoduleService submoduleService;

	@Mock
	private TaskService taskService;

	@Mock
	private TaskConverter taskConverter;

	@Mock
	private DTOExtensionManager extensionManager;

	@Test
	public void createTaskRepresentorFromTaskId() throws AdaptorException {
		final Task task = Mockito.mock(Task.class);
		Mockito.when(this.taskService.readComplete(TASK_ID)).thenReturn(task);
		Mockito.when(task.getId()).thenReturn(TASK_ID);
		final TaskRepresentor convertedRepresentor = Mockito.mock(TaskRepresentor.class);
		Mockito.when(this.taskConverter.toComplete(task)).thenReturn(convertedRepresentor);
		final TaskRepresentor managedRepresentor = Mockito.mock(TaskRepresentor.class);
		Mockito.when(this.extensionManager.prepare(convertedRepresentor)).thenReturn(managedRepresentor);

		final TaskRepresentor taskRepresentor = this.protocol.getTask(TASK_ID);

		Assert.assertNotNull(taskRepresentor);
		Assert.assertEquals(managedRepresentor.getId(), taskRepresentor.getId());
	}

	@Test
	public void createConfigurableTaskRepresentorList() throws AdaptorException {
		final SubmoduleRepresentor parentSubmoduleRepresentor = Mockito.mock(SubmoduleRepresentor.class);
		final Long managedIdA = 21L;
		final Long managedIdB = 22L;
		final Long managedIdC = 23L;
		final Long managedIdD = 24L;
		final Long managedIdE = 25L;
		final String managedName = TASK_TITLE + "2";
		final String managedDescription = "Test 2";
		final int managedPriority = 10;
		final double managedCompletion = 0;
		final Date managedDeadline = new Date();
		final boolean managedAdmittance = false;
		final Double managedDuration = null;
		final TaskRepresentor taskParameter = new TaskRepresentor(managedIdA, managedName, managedDescription, managedPriority, managedCompletion,
				managedDeadline, managedDuration, managedAdmittance);
		taskParameter.setSubmodule(parentSubmoduleRepresentor);
		Mockito.when(parentSubmoduleRepresentor.getId()).thenReturn(TASK_PARENT_SUBMODULE_ID);
		final Submodule parentSubmodule = Mockito.mock(Submodule.class);
		Mockito.when(this.submoduleService.readWithTasks(TASK_PARENT_SUBMODULE_ID)).thenReturn(parentSubmodule);
		final Set<Task> submoduleTasks = new HashSet<>();
		submoduleTasks.add(Mockito.mock(Task.class));
		submoduleTasks.add(Mockito.mock(Task.class));
		submoduleTasks.add(Mockito.mock(Task.class));
		submoduleTasks.add(Mockito.mock(Task.class));
		submoduleTasks.add(Mockito.mock(Task.class));
		Mockito.when(parentSubmodule.getTasks()).thenReturn(submoduleTasks);
		final TaskRepresentor submoduleLevelTaskRepresentorB = new TaskRepresentor(managedIdB, managedName + 3, managedDescription, managedPriority,
				managedCompletion, managedDeadline, managedDuration, managedAdmittance);
		final TaskRepresentor submoduleLevelTaskRepresentorC = new TaskRepresentor(managedIdC, managedName + 4, managedDescription, managedPriority,
				managedCompletion, managedDeadline, managedDuration, managedAdmittance);
		final TaskRepresentor submoduleLevelTaskRepresentorD = new TaskRepresentor(managedIdD, managedName + 5, managedDescription, managedPriority,
				managedCompletion, managedDeadline, managedDuration, managedAdmittance);
		final TaskRepresentor submoduleLevelTaskRepresentorE = new TaskRepresentor(managedIdE, managedName + 6, managedDescription, managedPriority,
				managedCompletion, managedDeadline, managedDuration, managedAdmittance);
		final Set<TaskRepresentor> convertedTasks = new HashSet<>();
		convertedTasks.add(taskParameter);
		convertedTasks.add(submoduleLevelTaskRepresentorB);
		convertedTasks.add(submoduleLevelTaskRepresentorC);
		convertedTasks.add(submoduleLevelTaskRepresentorD);
		convertedTasks.add(submoduleLevelTaskRepresentorE);
		Mockito.when(this.taskConverter.toElementary(submoduleTasks)).thenReturn(convertedTasks);
		final List<TaskRepresentor> dependencyConfigurations = new ArrayList<>();
		dependencyConfigurations.addAll(convertedTasks);
		dependencyConfigurations.remove(taskParameter);
		final List<List<TaskRepresentor>> parameterDependencies = new ArrayList<>();
		final List<TaskRepresentor> dependencyLevel = new ArrayList<>();
		dependencyLevel.add(submoduleLevelTaskRepresentorB);
		parameterDependencies.add(dependencyLevel);
		final List<List<TaskRepresentor>> parameterDependants = new ArrayList<>();
		final List<TaskRepresentor> dependantLevel = new ArrayList<>();
		dependencyLevel.add(submoduleLevelTaskRepresentorC);
		parameterDependencies.add(dependantLevel);
		taskParameter.setDependantChain(parameterDependencies);
		taskParameter.setDependantChain(parameterDependants);
		final List<TaskRepresentor> compliantConfigurations = new ArrayList<>();
		compliantConfigurations.add(submoduleLevelTaskRepresentorD);
		compliantConfigurations.add(submoduleLevelTaskRepresentorE);
		Mockito.when(this.extensionManager.prepareBatch(dependencyConfigurations)).thenReturn(new ArrayList(compliantConfigurations));

		final List<TaskRepresentor> taskRepresentors = this.protocol.getCompliantDependencyConfigurations(taskParameter);

		Assert.assertEquals(taskRepresentors.size(), compliantConfigurations.size());
		Assert.assertTrue(taskRepresentors.contains(submoduleLevelTaskRepresentorD));
		Assert.assertTrue(taskRepresentors.contains(submoduleLevelTaskRepresentorE));
	}

	@Test
	public void createOrUpdateTaskFromProperties() throws AdaptorException {
		final Long createId = null;
		final Long updateId = 32L;
		Mockito.when(this.taskService.exists(createId)).thenReturn(false);
		Mockito.when(this.taskService.exists(updateId)).thenReturn(true);
		final String managedName = TASK_TITLE + "3";
		final String managedDescription = "Test 3";
		final int managedPriority = 10;
		final double managedCompletion = 0;
		final Date managedDeadline = new Date();
		final boolean managedAdmittance = false;
		final Double managedDuration = null;
		final Double managedPessimistic = 9.0;
		final Double managedRealistic = 7.0;
		final Double managedOptimistic = 5.0;
		final Long parentSubmoduleId = 34L;
		final Long emptyParentObjectiveId = null;
		final Long emptyParentProjectId = null;
		final Task createdTask = Mockito.mock(Task.class);
		final Task updatedTask = Mockito.mock(Task.class);
		Mockito.when(this.taskService.create(managedName, managedDescription, managedPriority, managedCompletion, managedDeadline, managedAdmittance,
				TECHNICAL_USER, emptyParentObjectiveId, emptyParentProjectId, parentSubmoduleId, managedDuration, managedPessimistic, managedRealistic,
				managedOptimistic)).thenReturn(createdTask);
		Mockito.when(this.taskService.update(updateId, managedName, managedDescription, managedPriority, managedCompletion, managedDeadline, managedAdmittance,
				TECHNICAL_USER, emptyParentObjectiveId, emptyParentProjectId, parentSubmoduleId, managedDuration, managedPessimistic, managedRealistic,
				managedOptimistic)).thenReturn(updatedTask);
		final TaskRepresentor convertedCreatedTask = Mockito.mock(TaskRepresentor.class);
		final TaskRepresentor convertedUpdatedTask = Mockito.mock(TaskRepresentor.class);
		Mockito.when(this.taskConverter.toComplete(createdTask)).thenReturn(convertedCreatedTask);
		Mockito.when(this.taskConverter.toComplete(updatedTask)).thenReturn(convertedUpdatedTask);
		final TaskRepresentor managedCreatedTask = new TaskRepresentor(createId, managedName, managedDescription, managedPriority, managedCompletion,
				managedDeadline, managedDuration, managedAdmittance);
		final TaskRepresentor managedUpdatedTask = new TaskRepresentor(updateId, managedName, managedDescription, managedPriority, managedCompletion,
				managedDeadline, managedDuration, managedAdmittance);
		Mockito.when(this.extensionManager.prepare(convertedCreatedTask)).thenReturn(managedCreatedTask);
		Mockito.when(this.extensionManager.prepare(convertedUpdatedTask)).thenReturn(managedUpdatedTask);

		final TaskRepresentor createdTaskRepresentor = this.protocol.saveTask(createId, managedName, managedDescription, managedPriority, managedCompletion,
				managedDeadline, managedAdmittance, TECHNICAL_USER, emptyParentObjectiveId, emptyParentProjectId, parentSubmoduleId, managedDuration,
				managedPessimistic, managedRealistic, managedOptimistic);
		final TaskRepresentor updatedTaskRepresentor = this.protocol.saveTask(updateId, managedName, managedDescription, managedPriority, managedCompletion,
				managedDeadline, managedAdmittance, TECHNICAL_USER, emptyParentObjectiveId, emptyParentProjectId, parentSubmoduleId, managedDuration,
				managedPessimistic, managedRealistic, managedOptimistic);

		Assert.assertEquals(createdTaskRepresentor.getName(), managedCreatedTask.getName());
		Assert.assertEquals(createdTaskRepresentor.getDescription(), managedCreatedTask.getDescription());
		Assert.assertEquals(createdTaskRepresentor.getPriority(), managedCreatedTask.getPriority());
		Assert.assertEquals(createdTaskRepresentor.getCompletion(), managedCreatedTask.getCompletion());
		Assert.assertEquals(createdTaskRepresentor.getDeadline(), managedCreatedTask.getDeadline());
		Assert.assertEquals(createdTaskRepresentor.getAdmittance(), managedCreatedTask.getAdmittance());

		Assert.assertEquals(updatedTaskRepresentor.getName(), managedUpdatedTask.getName());
		Assert.assertEquals(updatedTaskRepresentor.getDescription(), managedUpdatedTask.getDescription());
		Assert.assertEquals(updatedTaskRepresentor.getPriority(), managedUpdatedTask.getPriority());
		Assert.assertEquals(updatedTaskRepresentor.getCompletion(), managedUpdatedTask.getCompletion());
		Assert.assertEquals(updatedTaskRepresentor.getDeadline(), managedUpdatedTask.getDeadline());
		Assert.assertEquals(updatedTaskRepresentor.getAdmittance(), managedUpdatedTask.getAdmittance());
	}

}