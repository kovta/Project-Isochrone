package com.kota.stratagem.ejbservice.preparation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.comparison.congregated.ObjectiveClusterComparator;
import com.kota.stratagem.ejbservice.comparison.congregated.ProjectSummaryComparator;
import com.kota.stratagem.ejbservice.comparison.congregated.SubmoduleSummaryComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.SubmoduleCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TeamAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueSubmoduleComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueTaskComparator;
import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.converter.evaluation.CPMNodeConverter;
import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbservice.evaluation.DependencyNetworkEvaluator;
import com.kota.stratagem.ejbservice.exception.CyclicDependencyException;
import com.kota.stratagem.ejbservice.exception.InvalidNodeTypeException;
import com.kota.stratagem.ejbservice.interceptor.Certified;
import com.kota.stratagem.ejbservice.qualifier.Definitive;
import com.kota.stratagem.ejbservice.qualifier.Estimated;
import com.kota.stratagem.ejbservice.qualifier.ProjectOriented;
import com.kota.stratagem.ejbservice.qualifier.SubmoduleOriented;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.service.TaskService;

@ProjectOriented
public class ProjectExtensionManager extends AbstractDTOExtensionManager {

	@EJB
	private TaskService taskService;

	@Inject
	private TaskConverter taskConverter;

	@Inject
	private CPMNodeConverter cpmNodeConverter;

	@Inject
	@Estimated
	private DependencyNetworkEvaluator estimatedEvaluator;

	@Inject
	@Definitive
	private DependencyNetworkEvaluator definitiveEvaluator;

	@Inject
	@SubmoduleOriented
	private DTOExtensionManager extensionManager;

	ProjectRepresentor representor;
	List<ProjectRepresentor> components;
	List<ObjectiveRepresentor> representors;

	@Override
	@Certified(ProjectRepresentor.class)
	public Object prepare(Object representor) {
		this.representor = (ProjectRepresentor) representor;
		return super.prepare(representor);
	}

	@Override
	@Certified(ProjectRepresentor.class)
	public Object prepareForOwner(Object representor) {
		this.representor = (ProjectRepresentor) representor;
		return super.prepareForOwner(representor);
	}

	@Override
	@Certified(ObjectiveRepresentor.class)
	public List<?> prepareBatch(List<?> representors) {
		this.representors = (List<ObjectiveRepresentor>) representors;
		return super.prepareBatch(representors);
	}

	@Override
	protected void addRepresentorSpecificProperties() {
		this.addOwnerDependantProperties();
		this.provideEvaluationDetails();
		this.provideCategorizedSubmodules();
		this.provideCategorizedTasks();
	}

	@Override
	protected void addOwnerDependantProperties() {
		this.prepareSubComponents();
		this.provideProgressionDetials();
	}

	@Override
	protected void sortSpecializedCollections() {
		Collections.sort(this.representor.getOverdueSubmodules(), new OverdueSubmoduleComparator());
		Collections.sort(this.representor.getOngoingSubmodules(), new SubmoduleCompletionComparator());
		Collections.sort(this.representor.getCompletedSubmodules(), new SubmoduleSummaryComparator());
		Collections.sort(this.representor.getOverdueTasks(), new OverdueTaskComparator());
		Collections.sort(this.representor.getOngoingTasks(), new TaskCompletionComparator());
		Collections.sort(this.representor.getCompletedTasks(), new TaskNameComparator());
	}

	@Override
	protected void sortJointCollection() {
		Collections.sort(this.representors, new ObjectiveClusterComparator());
		for (final ObjectiveRepresentor objective : this.representors) {
			Collections.sort(objective.getProjects(), new ProjectSummaryComparator());
		}
	}

	@Override
	protected void sortBaseCollections() {
		Collections.sort(this.representor.getAssignedUsers(), new AppUserAssignmentRecipientNameComparator());
		Collections.sort(this.representor.getAssignedTeams(), new TeamAssignmentRecipientNameComparator());
	}

	private void prepareSubComponents() {
		final List<SubmoduleRepresentor> submodules = new ArrayList<SubmoduleRepresentor>();
		for (final SubmoduleRepresentor submodule : this.representor.getSubmodules()) {
			submodules.add((SubmoduleRepresentor) this.extensionManager.prepareForOwner(submodule));
		}
		this.representor.setSubmodules(submodules);
	}

	private void provideProgressionDetials() {
		int progressSum = 0, submoduleTaskCount = 0;
		double durationSum = 0, completedDurationSum = 0;
		for (final TaskRepresentor task : this.representor.getTasks()) {
			progressSum += task.getCompletion();
			if (task.isEstimated()) {
				final double expectedDuration = this.calculator.calculateExpectedDuration(task.getPessimistic(), task.getRealistic(), task.getOptimistic());
				durationSum += expectedDuration;
				completedDurationSum += expectedDuration * (task.getCompletion() / 100);
			} else if (task.isDurationProvided()) {
				durationSum += task.getDuration();
				completedDurationSum += task.getDuration() * (task.getCompletion() / 100);
			}
		}
		for (final SubmoduleRepresentor submodule : this.representor.getSubmodules()) {
			progressSum += submodule.getCompletion();
			submoduleTaskCount += submodule.getTasks().size();
			durationSum += submodule.getDurationSum();
			completedDurationSum += submodule.getCompletedDurationSum();
		}
		final int componentCount = this.representor.getTasks().size() + this.representor.getSubmodules().size();
		this.representor.setTotalTaskCount(this.representor.getTasks().size() + submoduleTaskCount);
		this.representor.setCompletion(componentCount != 0 ? progressSum / componentCount : 0);
		this.representor.setDurationSum(durationSum);
		this.representor.setCompletedDurationSum(completedDurationSum);
	}

	private void provideEvaluationDetails() {
		if (!this.representor.isCompleted()) {
			Boolean estimated = false, configured = false;
			final List<TaskRepresentor> components = new ArrayList<>();
			for (final TaskRepresentor task : this.representor.getTasks()) {
				if (task.isEstimated() && !task.isCompleted()) {
					estimated = true;
					configured = true;
					components.add(this.taskConverter.toSimplified(this.taskService.readWithDirectDependencies(task.getId())));
				} else if (task.isDurationProvided() && !task.isCompleted()) {
					configured = true;
					components.add(this.taskConverter.toSimplified(this.taskService.readWithDirectDependencies(task.getId())));
				}
			}
			CPMResult result = null;
			try {
				if (configured) {
					if (estimated) {
						for (final TaskRepresentor component : components) {
							final double completionRatio = ((100 - component.getCompletion()) / 100);
							component.setPessimistic(component.getPessimistic() * completionRatio);
							component.setRealistic(component.getRealistic() * completionRatio);
							component.setOptimistic(component.getOptimistic() * completionRatio);
						}
						result = this.estimatedEvaluator.evaluate(this.cpmNodeConverter.to(components));
					} else {
						for (final TaskRepresentor component : components) {
							component.setDuration(component.getDuration() / ((100 - component.getCompletion()) / 100));
						}
						result = this.definitiveEvaluator.evaluate(this.cpmNodeConverter.to(components));
					}
				}
			} catch (InvalidNodeTypeException | CyclicDependencyException e) {
				LOGGER.error(e, e);
			}
			if (result != null) {
				this.provider.provideEstimations(result, this.representor);
			}
		}
	}

	private void provideCategorizedSubmodules() {
		final List<SubmoduleRepresentor> overdueSubmodules = new ArrayList<>(), ongoingSubmodules = new ArrayList<>(), completedSubmodules = new ArrayList<>();
		for (final SubmoduleRepresentor representor : this.representor.getSubmodules()) {
			if ((representor.getUrgencyLevel() == 3) && (!representor.isCompleted())) {
				overdueSubmodules.add(representor);
			} else if ((representor.getUrgencyLevel() != 3) && (!representor.isCompleted())) {
				ongoingSubmodules.add(representor);
			} else if (representor.isCompleted()) {
				completedSubmodules.add(representor);
			}
		}
		this.representor.setOverdueSubmodules(overdueSubmodules);
		this.representor.setOngoingSubmodules(ongoingSubmodules);
		this.representor.setCompletedSubmodules(completedSubmodules);
	}

	private void provideCategorizedTasks() {
		final List<TaskRepresentor> overdueTasks = new ArrayList<>(), ongoingTasks = new ArrayList<>(), completedTasks = new ArrayList<>();
		for (final TaskRepresentor representor : this.representor.getTasks()) {
			if ((representor.getUrgencyLevel() == 3) && (!representor.isCompleted())) {
				overdueTasks.add(representor);
			} else if ((representor.getUrgencyLevel() != 3) && (!representor.isCompleted())) {
				ongoingTasks.add(representor);
			} else if (representor.isCompleted()) {
				completedTasks.add(representor);
			}
		}
		this.representor.setOverdueTasks(overdueTasks);
		this.representor.setOngoingTasks(ongoingTasks);
		this.representor.setCompletedTasks(completedTasks);
	}

}
