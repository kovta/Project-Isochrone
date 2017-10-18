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

	private static final Long PRISTINE_OBJECTIVE_IDENTIFIER = 8L;
	private static final Long PRISTINE_PROJECT_IDENTIFIER = 11L;
	private static final Long PRISTINE_SUBMODULE_IDENTIFIER = 20L;
	private static final Long PRISTINE_TASK_IDENTIFIER = 80L;

	private static final String PRISTINE_OBJECTIVE_TITLE = "[TEST_OBJECTIVE]";
	private static final String PRISTINE_PROJECT_TITLE = "[TEST_PROJECT]";
	private static final String PRISTINE_SUBMODULE_TITLE = "[TEST_SUBMODULE]";

	private AppUserServiceImpl appUserService;
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

		this.appUserService = new AppUserServiceImpl();
		this.appUserService.setEntityManager(entityManager);

		this.objectiveService = new ObjectiveServiceImpl();
		this.objectiveService.setEntityManager(entityManager);
		this.resolveServiceDependencies(this.objectiveService);

		this.projectService = new ProjectServiceImpl();
		this.projectService.setEntityManager(entityManager);
		this.resolveServiceDependencies(this.projectService);

		this.submoduleService = new SubmoduleServiceImpl();
		this.submoduleService.setEntityManager(entityManager);
		this.resolveServiceDependencies(this.submoduleService);

		this.taskService = new TaskServiceImpl();
		this.taskService.setEntityManager(entityManager);
		this.resolveServiceDependencies(this.taskService);
	}

	private <T extends IntegrationDependencyContainer> void resolveServiceDependencies(T serviceInstance) {
		if (serviceInstance.getAppUserService() == null) {
			serviceInstance.setAppUserService(new AppUserServiceImpl());
		}
		if (serviceInstance.getObjectiveService() == null) {
			serviceInstance.setObjectiveService(new ObjectiveServiceImpl());
		}
		if (serviceInstance.getProjectService() == null) {
			serviceInstance.setProjectService(new ProjectServiceImpl());
		}
		if (serviceInstance.getSubmoduleService() == null) {
			serviceInstance.setSubmoduleService(new SubmoduleServiceImpl());
		}
		if (serviceInstance.getTaskService() == null) {
			serviceInstance.setTaskService(new TaskServiceImpl());
		}
	}

	@Test(groups = "integration")
	private void createObjective() throws PersistenceServiceException {
		this.objectiveService.setAppUserService(this.appUserService);
		if (this.objectiveService.exists(PRISTINE_OBJECTIVE_IDENTIFIER)) {
			this.objectiveService.getEntityManager().getTransaction().begin();
			this.objectiveService.delete(PRISTINE_OBJECTIVE_IDENTIFIER);
			this.objectiveService.getEntityManager().getTransaction().commit();
		}

		this.objectiveService.getEntityManager().getTransaction().begin();
		final Objective objective = this.objectiveService.create(PRISTINE_OBJECTIVE_TITLE, "", 10, ObjectiveStatus.PLANNED, null, true, "adam");
		this.objectiveService.getEntityManager().getTransaction().commit();
		Assert.assertNotNull(objective);
		for (final Objective obj : this.objectiveService.readAll()) {
			if (obj.getName().equals(PRISTINE_OBJECTIVE_TITLE)) {
				this.objectiveService.getEntityManager().getTransaction().begin();
				this.objectiveService.delete(obj.getId());
				this.objectiveService.getEntityManager().getTransaction().commit();
			}
		}
	}

	@Test(groups = "integration")
	private void createProject() throws PersistenceServiceException {
		this.taskService.setAppUserService(this.appUserService);
		this.submoduleService.setTaskService(this.taskService);
		this.projectService.setAppUserService(this.appUserService);
		this.projectService.setObjectiveService(this.objectiveService);
		this.projectService.setSubmoduleService(this.submoduleService);
		this.projectService.setTaskService(this.taskService);
		if (this.projectService.exists(PRISTINE_PROJECT_IDENTIFIER)) {
			this.projectService.getEntityManager().getTransaction().begin();
			this.projectService.delete(PRISTINE_PROJECT_IDENTIFIER);
			this.projectService.getEntityManager().getTransaction().commit();
		}

		this.projectService.getEntityManager().getTransaction().begin();
		final Project project = this.projectService.create(PRISTINE_PROJECT_TITLE, "", ProjectStatus.TESTING, null, true, "adam", 7L);
		this.projectService.getEntityManager().getTransaction().commit();
		Assert.assertNotNull(project);
		for (final Project prj : this.projectService.readAll()) {
			if (prj.getName().equals(PRISTINE_PROJECT_TITLE)) {
				this.projectService.getEntityManager().getTransaction().begin();
				this.projectService.delete(prj.getId());
				this.projectService.getEntityManager().getTransaction().commit();
			}
		}
	}

	@Test(groups = "integration")
	private void createSubmodule() throws PersistenceServiceException {
		this.submoduleService.setAppUserService(this.appUserService);
		this.submoduleService.setProjectService(this.projectService);
		this.submoduleService.setTaskService(this.taskService);
		if (this.submoduleService.exists(PRISTINE_SUBMODULE_IDENTIFIER)) {
			this.submoduleService.getEntityManager().getTransaction().begin();
			this.submoduleService.delete(PRISTINE_SUBMODULE_IDENTIFIER);
			this.submoduleService.getEntityManager().getTransaction().commit();
		}

		this.submoduleService.getEntityManager().getTransaction().begin();
		final Submodule submodule = this.submoduleService.create(PRISTINE_SUBMODULE_TITLE, "", null, "adam", 10L);
		this.submoduleService.getEntityManager().getTransaction().commit();
		for (final Submodule smd : this.submoduleService.readAll()) {
			if (smd.getName().equals(PRISTINE_SUBMODULE_TITLE)) {
				this.submoduleService.getEntityManager().getTransaction().begin();
				this.submoduleService.delete(smd.getId());
				this.submoduleService.getEntityManager().getTransaction().commit();
			}
		}
		Assert.assertNotNull(submodule);
	}

	@Test(groups = "integration")
	private void createTask() throws PersistenceServiceException {
		this.projectService.setAppUserService(this.appUserService);
		this.taskService.setObjectiveService(this.objectiveService);
		this.taskService.setProjectService(this.projectService);
		this.taskService.setSubmoduleService(this.submoduleService);
		if (this.taskService.exists(PRISTINE_TASK_IDENTIFIER)) {
			this.taskService.getEntityManager().getTransaction().begin();
			this.taskService.delete(PRISTINE_TASK_IDENTIFIER);
			this.taskService.getEntityManager().getTransaction().commit();
		}

		// this.taskService.getEntityManager().getTransaction().begin();
		// final Task task = this.taskService.create("Project Level Test Task 1", "", 10, 0, null, false, "adam", null,
		// 10L, null, null, null, null, null);
		// this.taskService.getEntityManager().getTransaction().commit();
		// Assert.assertNotNull(task);
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
		final Project project = this.projectService.readElementary(10L);
		this.assertProject(project, 10L, "Test Project 2", "", ProjectStatus.PROPOSED, true, 7L);
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
		final Submodule submodule = this.submoduleService.readElementary(21L);
		Assert.assertEquals(submodule.getId(), (Long) 21L);
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
