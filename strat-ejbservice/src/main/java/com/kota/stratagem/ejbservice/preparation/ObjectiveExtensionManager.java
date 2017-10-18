package com.kota.stratagem.ejbservice.preparation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.comparison.congregated.ObjectiveSummaryComparator;
import com.kota.stratagem.ejbservice.comparison.congregated.ProjectSummaryComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.ProjectCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TeamAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueProjectComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueTaskComparator;
import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.converter.evaluation.CPMNodeConverter;
import com.kota.stratagem.ejbservice.domain.CPMResult;
import com.kota.stratagem.ejbservice.interceptor.Certified;
import com.kota.stratagem.ejbservice.qualifier.ObjectiveOriented;
import com.kota.stratagem.ejbservice.qualifier.ProjectOriented;
import com.kota.stratagem.ejbserviceclient.domain.ObjectiveRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;
import com.kota.stratagem.persistence.qualifier.SubmoduleFocused;
import com.kota.stratagem.persistence.qualifier.TaskFocused;
import com.kota.stratagem.persistence.service.ProjectService;

@ObjectiveOriented
public class ObjectiveExtensionManager extends AbstractDTOExtensionManager {

	@EJB
	private ProjectService projectService;

	@Inject
	private ProjectConverter projectConverter;

	@Inject
	@SubmoduleFocused
	private CPMNodeConverter submoduleBasedCPMNodeConverter;

	@Inject
	@TaskFocused
	private CPMNodeConverter taskBasedCPMNodeConverter;

	@Inject
	@ProjectOriented
	private DTOExtensionManager extensionManager;

	ObjectiveRepresentor representor;
	List<ObjectiveRepresentor> representors;

	@Override
	@Certified(ObjectiveRepresentor.class)
	public Object prepare(Object representor) {
		this.representor = (ObjectiveRepresentor) representor;
		return super.prepare(representor);
	}

	@Override
	public Object prepareForOwner(Object representor) {
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
		this.prepareSubComponents();
		this.provideProgressionDetials();
		this.provideEvaluationDetails();
		this.provideCategorizedProjects();
		this.provideCategorizedTasks();
	}

	@Override
	protected void addOwnerDependantProperties() {

	}

	@Override
	protected void sortSpecializedCollections() {
		Collections.sort(this.representor.getOverdueProjects(), new OverdueProjectComparator());
		Collections.sort(this.representor.getOngoingProjects(), new ProjectCompletionComparator());
		Collections.sort(this.representor.getCompletedProjects(), new ProjectSummaryComparator());
		Collections.sort(this.representor.getOverdueTasks(), new OverdueTaskComparator());
		Collections.sort(this.representor.getOngoingTasks(), new TaskCompletionComparator());
		Collections.sort(this.representor.getCompletedTasks(), new TaskNameComparator());
	}

	@Override
	protected void sortJointCollection() {
		Collections.sort(this.representors, new ObjectiveSummaryComparator());
	}

	@Override
	protected void sortBaseCollections() {
		Collections.sort(this.representor.getAssignedUsers(), new AppUserAssignmentRecipientNameComparator());
		Collections.sort(this.representor.getAssignedTeams(), new TeamAssignmentRecipientNameComparator());
	}

	private void prepareSubComponents() {
		final List<ProjectRepresentor> projects = new ArrayList<ProjectRepresentor>();
		for (final ProjectRepresentor project : this.representor.getProjects()) {
			projects.add((ProjectRepresentor) this.extensionManager
					.prepareForOwner(this.projectConverter.toSimplified(this.projectService.readWithSubmodulesAndTasks(project.getId()))));
		}
		this.representor.setProjects(projects);
	}

	private void provideProgressionDetials() {
		int progressSum = 0;
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
		for (final ProjectRepresentor project : this.representor.getProjects()) {
			progressSum += project.getCompletion();
			durationSum += project.getDurationSum();
			completedDurationSum += project.getCompletedDurationSum();
		}
		final int componentCount = this.representor.getTasks().size() + this.representor.getProjects().size();
		this.representor.setCompletion(componentCount != 0 ? progressSum / componentCount : 0);
		this.representor.setDurationSum(durationSum);
		this.representor.setCompletedDurationSum(completedDurationSum);
	}

	private void provideEvaluationDetails() {
		if (!this.representor.isCompleted()) {
			Boolean estimated = false, configured = false;
			final List<CPMNode> taskComponents = new ArrayList<>(), network = new ArrayList<>();
			for (final TaskRepresentor task : this.representor.getTasks()) {
				if (task.isEstimated() && !task.isCompleted()) {
					estimated = true;
					configured = true;
					this.provider.addCompletionAdaptedComponent(taskComponents, task);
				} else if (task.isDurationProvided() && !task.isCompleted()) {
					configured = true;
					this.provider.addCompletionAdaptedComponent(taskComponents, task);
				}
			}
			Double expectedDurationSummary = 0.0, varianceSummary = 0.0;
			for (final ProjectRepresentor project : this.representor.getProjects()) {
				if (!project.isCompleted()) {
					configured = true;
					expectedDurationSummary += project.getExpectedDuration();
					varianceSummary += project.getVariance();
				}
			}
			if (configured) {
				network.addAll(this.taskBasedCPMNodeConverter.to(taskComponents));
				final CPMResult result = this.provider.evaluateDependencyNetwork(network, estimated);
				final CPMResult finalResult = new CPMResult(expectedDurationSummary + result.getExpectedDuration(),
						Math.sqrt(varianceSummary + Math.pow(result.getStandardDeviation(), 2)));
				this.provider.provideEstimations(finalResult, this.representor);
			} else {
				this.provider.provideBlankEstimations(this.representor);
			}
		}
	}

	private void provideCategorizedProjects() {
		final List<ProjectRepresentor> overdueProjects = new ArrayList<>(), ongoingProjects = new ArrayList<>(), completedProjects = new ArrayList<>();
		for (final ProjectRepresentor representor : this.representor.getProjects()) {
			if ((representor.getUrgencyLevel() == 3) && (!representor.isCompleted())) {
				overdueProjects.add(representor);
			} else if ((representor.getUrgencyLevel() != 3) && (!representor.isCompleted())) {
				ongoingProjects.add(representor);
			} else if (representor.isCompleted()) {
				completedProjects.add(representor);
			}
		}
		this.representor.setOverdueProjects(overdueProjects);
		this.representor.setOngoingProjects(ongoingProjects);
		this.representor.setCompletedProjects(completedProjects);
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
