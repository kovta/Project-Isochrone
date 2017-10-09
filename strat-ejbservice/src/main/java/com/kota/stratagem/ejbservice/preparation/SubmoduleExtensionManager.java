package com.kota.stratagem.ejbservice.preparation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.SubmoduleNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskCompletionComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TeamAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.stagnated.OverdueTaskComparator;
import com.kota.stratagem.ejbservice.converter.SubmoduleConverter;
import com.kota.stratagem.ejbservice.converter.evaluation.CPMNodeConverter;
import com.kota.stratagem.ejbservice.interceptor.Certified;
import com.kota.stratagem.ejbservice.qualifier.SubmoduleOriented;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.designation.CPMNode;
import com.kota.stratagem.persistence.qualifier.TaskFocused;
import com.kota.stratagem.persistence.service.SubmoduleService;

@SubmoduleOriented
public class SubmoduleExtensionManager extends AbstractDTOExtensionManager {

	@EJB
	private SubmoduleService submoduleService;

	@Inject
	private SubmoduleConverter submoduleConverter;

	@Inject
	@TaskFocused
	private CPMNodeConverter cpmNodeConverter;

	SubmoduleRepresentor representor;
	List<SubmoduleRepresentor> representors;

	@Override
	@Certified(SubmoduleRepresentor.class)
	public Object prepare(Object representor) {
		this.representor = (SubmoduleRepresentor) representor;
		return super.prepare(representor);
	}

	@Override
	@Certified(SubmoduleRepresentor.class)
	public Object prepareForOwner(Object representor) {
		this.representor = (SubmoduleRepresentor) representor;
		return super.prepareForOwner(representor);
	}

	@Override
	@Certified(SubmoduleRepresentor.class)
	public List<?> prepareBatch(List<?> representors) {
		this.representors = (List<SubmoduleRepresentor>) representors;
		return super.prepareBatch(representors);
	}

	@Override
	protected void addRepresentorSpecificProperties() {
		this.addOwnerDependantProperties();
		this.provideCategorizedTasks();
		this.provideDependencyChain();
		this.provideDependantCount();
		this.provideDependencyCount();
	}

	@Override
	protected void addOwnerDependantProperties() {
		this.provideProgressionDetials();
		this.provideEvaluationDetails();
	}

	@Override
	protected void sortSpecializedCollections() {
		Collections.sort(this.representor.getOverdueTasks(), new OverdueTaskComparator());
		Collections.sort(this.representor.getOngoingTasks(), new TaskCompletionComparator());
		Collections.sort(this.representor.getCompletedTasks(), new TaskNameComparator());
	}

	@Override
	protected void sortBaseCollections() {
		Collections.sort(this.representor.getAssignedUsers(), new AppUserAssignmentRecipientNameComparator());
		Collections.sort(this.representor.getAssignedTeams(), new TeamAssignmentRecipientNameComparator());
	}

	@Override
	protected void sortJointCollection() {
		Collections.reverse(this.representor.getDependantChain());
		for (final List<SubmoduleRepresentor> dependantLevel : this.representor.getDependantChain()) {
			Collections.sort(dependantLevel, new SubmoduleNameComparator());
		}
		for (final List<SubmoduleRepresentor> dependencyLevel : this.representor.getDependencyChain()) {
			Collections.sort(dependencyLevel, new SubmoduleNameComparator());
		}
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
		this.representor.setCompletion(this.representor.getTasks().size() != 0 ? progressSum / this.representor.getTasks().size() : 0);
		this.representor.setDurationSum(durationSum);
		this.representor.setCompletedDurationSum(completedDurationSum);
	}

	private void provideEvaluationDetails() {
		if (!this.representor.isCompleted()) {
			Boolean estimated = false, configured = false;
			final List<CPMNode> components = new ArrayList<>(), network = new ArrayList<>();
			for (final TaskRepresentor task : this.representor.getTasks()) {
				if (task.isEstimated() && !task.isCompleted()) {
					estimated = true;
					configured = true;
					this.provider.addCompletionAdaptedComponent(components, task);
				} else if (task.isDurationProvided() && !task.isCompleted()) {
					configured = true;
					this.provider.addCompletionAdaptedComponent(components, task);
				}
			}
			if (configured) {
				network.addAll(this.cpmNodeConverter.to(components));
				this.provider.provideEstimations(this.provider.evaluateDependencyNetwork(network, estimated), this.representor);
			} else {
				this.provider.provideBlankEstimations(this.representor);
			}
		}
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

	public void provideDependencyChain() {
		final List<Long> identifiers = new ArrayList<>();
		if (!this.representor.getDependantSubmodules().isEmpty()) {
			final List<List<SubmoduleRepresentor>> dependantChain = new ArrayList<>();
			final Queue<SubmoduleRepresentor> queue = new LinkedList<SubmoduleRepresentor>();
			queue.add(this.representor);
			SubmoduleRepresentor current;
			while ((current = queue.poll()) != null) {
				this.traverseDependants(current, queue, dependantChain, identifiers);
			}
			this.representor.setDependantChain(dependantChain);
		}
		if (!this.representor.getSubmoduleDependencies().isEmpty()) {
			final List<List<SubmoduleRepresentor>> dependencyChain = new ArrayList<>();
			final Queue<SubmoduleRepresentor> queue = new LinkedList<SubmoduleRepresentor>();
			queue.add(this.representor);
			SubmoduleRepresentor current;
			while ((current = queue.poll()) != null) {
				this.traverseDependencies(current, queue, dependencyChain, identifiers);
			}
			this.representor.setDependencyChain(dependencyChain);
		}
	}

	private void traverseDependants(SubmoduleRepresentor node, Queue<SubmoduleRepresentor> queue, List<List<SubmoduleRepresentor>> dependantChain, List<Long> identifiers) {
		if (!node.getDependantSubmodules().isEmpty()) {
			final List<SubmoduleRepresentor> nodes = new ArrayList<SubmoduleRepresentor>(node.getDependantSubmodules());
			for (final SubmoduleRepresentor dependant : node.getDependantSubmodules()) {
				if (identifiers.contains(dependant.getId())) {
					nodes.remove(dependant);
				} else {
					identifiers.add(dependant.getId());
				}
			}
			if (!nodes.isEmpty()) {
				dependantChain.add(nodes);
			}
			for (int i = 0; i < node.getDependantSubmodules().size(); i++) {
				final SubmoduleRepresentor dependantNode = this.submoduleConverter
						.toDependencyExtended(this.submoduleService.readWithDirectDependencies(node.getDependantSubmodules().get(i).getId()));
				queue.add(dependantNode);
			}
		}
	}

	private void traverseDependencies(SubmoduleRepresentor node, Queue<SubmoduleRepresentor> queue, List<List<SubmoduleRepresentor>> dependencyChain, List<Long> identifiers) {
		if (!node.getSubmoduleDependencies().isEmpty()) {
			final List<SubmoduleRepresentor> nodes = new ArrayList<SubmoduleRepresentor>(node.getSubmoduleDependencies());
			for (final SubmoduleRepresentor dependency : node.getSubmoduleDependencies()) {
				if (identifiers.contains(dependency.getId())) {
					nodes.remove(dependency);
				} else {
					identifiers.add(dependency.getId());
				}
			}
			if (!nodes.isEmpty()) {
				dependencyChain.add(nodes);
			}
			for (int i = 0; i < node.getSubmoduleDependencies().size(); i++) {
				final SubmoduleRepresentor dependencyNode = this.submoduleConverter
						.toDependencyExtended(this.submoduleService.readWithDirectDependencies(node.getSubmoduleDependencies().get(i).getId()));
				queue.add(dependencyNode);
			}
		}
	}

	public void provideDependantCount() {
		int total = 0;
		for (final List<SubmoduleRepresentor> dependantLevel : this.representor.getDependantChain()) {
			total += dependantLevel.size();
		}
		this.representor.setDependantCount(total);
	}

	public void provideDependencyCount() {
		int total = 0;
		for (final List<SubmoduleRepresentor> dependencyLevel : this.representor.getDependencyChain()) {
			total += dependencyLevel.size();
		}
		this.representor.setDependencyCount(total);
	}

}
