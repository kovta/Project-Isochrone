package com.kota.stratagem.ejbservice.protocol;

import java.util.Date;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.core.AbstractMockTest;
import com.kota.stratagem.ejbservice.exception.AdaptorException;
import com.kota.stratagem.ejbservice.preparation.DTOExtensionManager;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.service.TaskService;

public class TaskProtocolImplTest extends AbstractMockTest {

	private static final Long TASK_ID = 10L;

	private static final String TASK_TITLE = "[TEST_TASK]-";

	@InjectMocks
	private TaskProtocolImpl protocol;

	@Mock
	private TaskService taskService;

	@Mock
	private TaskConverter taskConverter;

	@Mock
	private DTOExtensionManager extensionManager;

	@Test
	public void createTaskRepresentorFromTaskId() throws AdaptorException {
		final Task task = Mockito.mock(Task.class);
		Mockito.when(taskService.readComplete(TASK_ID)).thenReturn(task);
		Mockito.when(task.getId()).thenReturn(TASK_ID);
		final TaskRepresentor convertedRepresentor = Mockito.mock(TaskRepresentor.class);
		Mockito.when(taskConverter.toComplete(task)).thenReturn(convertedRepresentor);
		final TaskRepresentor managedRepresentor = Mockito.mock(TaskRepresentor.class);
		Mockito.when(extensionManager.prepare(convertedRepresentor)).thenReturn(managedRepresentor);

		final TaskRepresentor taskRepresentor = this.protocol.getTask(TASK_ID);

		Assert.assertNotNull(taskRepresentor);
		Assert.assertEquals(managedRepresentor.getId(), taskRepresentor.getId());
	}

	@Test
	public void createOrUpdateTaskFromProperties() throws AdaptorException {
		final Long createId = null;
		final Long updateId = 32L;
		Mockito.when(taskService.exists(createId)).thenReturn(false);
		Mockito.when(taskService.exists(updateId)).thenReturn(true);
		final String managedName = TASK_TITLE + "2";
		final String managedDescription = "Test 2";
		final int managedPriority = 10;
		final double managedCompletion = 0;
		final Date managedDeadline = new Date();
		final boolean managedAdmittance = false;
		final Double managedDuration = null;
		final Double managedPessimistic = (double) 9;
		final Double managedRealistic = (double) 7;
		final Double managedOptimistic = (double) 5;
		Long parentSubmoduleId = 34L;
		Long emptyParentObjectiveId = null;
		Long emptyParentProjectId = null;
		Task createdTask = Mockito.mock(Task.class);
		Task updatedTask = Mockito.mock(Task.class);
		Mockito.when(taskService.create(managedName, managedDescription, managedPriority, managedCompletion, managedDeadline, managedAdmittance, TECHNICAL_USER, emptyParentObjectiveId,
				emptyParentProjectId, parentSubmoduleId, managedDuration, managedPessimistic, managedRealistic, managedOptimistic)).thenReturn(createdTask);
		Mockito.when(taskService.update(updateId, managedName, managedDescription, managedPriority, managedCompletion, managedDeadline, managedAdmittance, TECHNICAL_USER, emptyParentObjectiveId,
				emptyParentProjectId, parentSubmoduleId, managedDuration, managedPessimistic, managedRealistic, managedOptimistic)).thenReturn(updatedTask);
		TaskRepresentor convertedCreatedTask = Mockito.mock(TaskRepresentor.class);
		TaskRepresentor convertedUpdatedTask = Mockito.mock(TaskRepresentor.class);
		Mockito.when(taskConverter.toComplete(createdTask)).thenReturn(convertedCreatedTask);
		Mockito.when(taskConverter.toComplete(updatedTask)).thenReturn(convertedUpdatedTask);
		TaskRepresentor managedCreatedTask = new TaskRepresentor(createId, managedName, managedDescription, managedPriority, managedCompletion, managedDeadline, managedDuration, managedAdmittance);
		TaskRepresentor managedUpdatedTask = new TaskRepresentor(updateId, managedName, managedDescription, managedPriority, managedCompletion, managedDeadline, managedDuration, managedAdmittance);
		Mockito.when(extensionManager.prepare(convertedCreatedTask)).thenReturn(managedCreatedTask);
		Mockito.when(extensionManager.prepare(convertedUpdatedTask)).thenReturn(managedUpdatedTask);

		final TaskRepresentor createdTaskRepresentor = this.protocol.saveTask(createId, managedName, managedDescription, managedPriority, managedCompletion, managedDeadline, managedAdmittance,
				TECHNICAL_USER, emptyParentObjectiveId, emptyParentProjectId, parentSubmoduleId, managedDuration, managedPessimistic, managedRealistic, managedOptimistic);
		final TaskRepresentor updatedTaskRepresentor = this.protocol.saveTask(updateId, managedName, managedDescription, managedPriority, managedCompletion, managedDeadline, managedAdmittance,
				TECHNICAL_USER, emptyParentObjectiveId, emptyParentProjectId, parentSubmoduleId, managedDuration, managedPessimistic, managedRealistic, managedOptimistic);

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