package com.kota.stratagem.persistence.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.kota.stratagem.persistence.entity.Objective;
import com.kota.stratagem.persistence.entity.Project;
import com.kota.stratagem.persistence.entity.Submodule;
import com.kota.stratagem.persistence.entity.Task;
import com.kota.stratagem.persistence.entity.trunk.ObjectiveStatus;
import com.kota.stratagem.persistence.entity.trunk.ProjectStatus;
import com.kota.stratagem.persistence.exception.PersistenceServiceException;

public class ServiceImplIntegrationTest {

	private static final String PERSISTENCE_UNIT = "strat-persistence-test-unit";

	private static final Long PRISTINE_PROJECT_IDENTIFIER = 8L;
	private static final Long PRISTINE_TASK_IDENTIFIER = 15L;

	private ObjectiveServiceImpl objectiveService;
	private ProjectServiceImpl projectService;
	private SubmoduleServiceImpl submoduleService;
	private TaskServiceImpl taskService;

	@BeforeClass
	private void setUp() {
		Thread.currentThread().setContextClassLoader(new ClassLoader() {
			@Override
			public Enumeration<URL> getResources(String name) throws IOException {
				if (name.equals("META-INF/persistence.xml")) {
					return Collections.enumeration(Arrays.asList(new File("src/test/resources/persistence.xml").toURI().toURL()));
				}
				return super.getResources(name);
			}
		});

		final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		final EntityManager entityManager = factory.createEntityManager();

		this.objectiveService = new ObjectiveServiceImpl();
		this.objectiveService.setEntityManager(entityManager);

		this.projectService = new ProjectServiceImpl();
		this.projectService.setEntityManager(entityManager);

		this.submoduleService = new SubmoduleServiceImpl();
		this.submoduleService.setEntityManager(entityManager);

		this.taskService = new TaskServiceImpl();
		this.taskService.setEntityManager(entityManager);
	}

	@Test(groups = "integration")
	private void createProject() throws PersistenceServiceException {
		if (this.projectService.exists(PRISTINE_TASK_IDENTIFIER)) {
			this.projectService.getEntityManager().getTransaction().begin();
			this.projectService.delete(PRISTINE_TASK_IDENTIFIER);
			this.projectService.getEntityManager().getTransaction().commit();
		}

		this.projectService.getEntityManager().getTransaction().begin();
		this.projectService.create("Test Project", "", ProjectStatus.TESTING, null, true, "adam", 7L);
		this.projectService.getEntityManager().getTransaction().commit();

		final Project project = this.projectService.readElementary(PRISTINE_TASK_IDENTIFIER);
		Assert.assertEquals(project.getName(), "Test Project");
	}

	@Test(groups = "integration")
	private void createTask() throws PersistenceServiceException {
		if (this.taskService.exists(PRISTINE_TASK_IDENTIFIER)) {
			this.taskService.getEntityManager().getTransaction().begin();
			this.taskService.delete(PRISTINE_TASK_IDENTIFIER);
			this.taskService.getEntityManager().getTransaction().commit();
		}

		this.taskService.getEntityManager().getTransaction().begin();
		this.taskService.create("Project Level Test Task 1", "", 10, 0, null, false, "adam", null, 8L, null, null, null, null, null);
		this.taskService.getEntityManager().getTransaction().commit();

		final Task task = this.taskService.readElementary(PRISTINE_TASK_IDENTIFIER);
		Assert.assertEquals(task.getName(), "Project Level Test Task 1");
	}

	@Test(groups = "integration")
	private void readSampleObjective() throws PersistenceServiceException {
		final Objective objective = this.objectiveService.readElementary(7L);
		this.assertObjective(objective, 7L, "Test Objective", "", ObjectiveStatus.PLANNED, false);
	}

	private void assertObjective(final Objective objective, Long id, String name, String description, ObjectiveStatus status, Boolean confidential) {
		Assert.assertEquals(objective.getId(), id);
		Assert.assertEquals(objective.getName(), name);
		Assert.assertEquals(objective.getDescription(), description);
		Assert.assertEquals(objective.getStatus(), status);
		Assert.assertEquals(objective.getConfidential(), confidential);
	}

	@Test(groups = "integration")
	private void readSampleProject() throws PersistenceServiceException {
		final Project project = this.projectService.readElementary(8L);
		this.assertProject(project, 8L, "Test Project", "", ProjectStatus.PROPOSED, true, 7L);
	}

	private void assertProject(final Project project, Long id, String name, String description, ProjectStatus status, Boolean confidential, Long objective) {
		Assert.assertEquals(project.getId(), id);
		Assert.assertEquals(project.getName(), name);
		Assert.assertEquals(project.getDescription(), description);
		Assert.assertEquals(project.getStatus(), status);
		Assert.assertEquals(project.getConfidential(), confidential);
		Assert.assertEquals(project.getObjective().getId(), objective);
	}

	@Test(groups = "integration")
	private void readSampleSubmodule() throws PersistenceServiceException {
		final Submodule submodule = this.submoduleService.readElementary(6L);
		Assert.assertEquals(submodule.getId(), (Long) 6L);
	}

	@Test(groups = "integration")
	private void readSampleTask() throws PersistenceServiceException {
		final Task task = this.taskService.readElementary(26L);
		Assert.assertEquals(task.getId(), (Long) 26L);
	}

	@AfterClass
	private void tearDown() {
		this.objectiveService.getEntityManager().close();
	}

}
