package com.kota.stratagem.ejbservice.preparation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.kota.stratagem.ejbservice.comparison.dualistic.AppUserAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TaskNameComparator;
import com.kota.stratagem.ejbservice.comparison.dualistic.TeamAssignmentRecipientNameComparator;
import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.interceptor.Certified;
import com.kota.stratagem.ejbservice.qualifier.TaskOriented;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.service.TaskService;

@TaskOriented
public class TaskExtensionManager extends AbstractDTOExtensionManager {

	@EJB
	private TaskService taskService;

	@Inject
	private TaskConverter taskConverter;

	TaskRepresentor representor;
	List<TaskRepresentor> representors;

	@Override
	@Certified(TaskRepresentor.class)
	public Object prepare(Object representor) {
		this.representor = (TaskRepresentor) representor;
		return super.prepare(representor);
	}

	@Override
	@Certified(TaskRepresentor.class)
	public Object prepareForOwner(Object representor) {
		this.representor = (TaskRepresentor) representor;
		return super.prepareForOwner(representor);
	}

	@Override
	@Certified(TaskRepresentor.class)
	public List<?> prepareBatch(List<?> representors) {
		this.representors = (List<TaskRepresentor>) representors;
		return super.prepareBatch(representors);
	}

	@Override
	protected void addRepresentorSpecificProperties() {
		this.provideDependencyChain();
		this.provideDependantCount();
		this.provideDependencyCount();
		this.provideEstimationDetails();
	}

	@Override
	protected void addOwnerDependantProperties() {

	}

	@Override
	protected void sortSpecializedCollections() {
		Collections.reverse(this.representor.getDependantChain());
		for (final List<TaskRepresentor> dependantLevel : this.representor.getDependantChain()) {
			Collections.sort(dependantLevel, new TaskNameComparator());
		}
		for (final List<TaskRepresentor> dependencyLevel : this.representor.getDependencyChain()) {
			Collections.sort(dependencyLevel, new TaskNameComparator());
		}
	}

	@Override
	protected void sortBaseCollections() {
		Collections.sort(this.representor.getAssignedUsers(), new AppUserAssignmentRecipientNameComparator());
		Collections.sort(this.representor.getAssignedTeams(), new TeamAssignmentRecipientNameComparator());
	}

	@Override
	protected void sortJointCollection() {
		Collections.sort(this.representors, new TaskNameComparator());
	}

	public void provideDependencyChain() {
		final List<Long> identifiers = new ArrayList<>();
		if (!this.representor.getDependantTasks().isEmpty()) {
			final List<List<TaskRepresentor>> dependantChain = new ArrayList<>();
			final Queue<TaskRepresentor> queue = new LinkedList<TaskRepresentor>();
			queue.add(this.representor);
			TaskRepresentor current;
			while ((current = queue.poll()) != null) {
				this.traverseDependants(current, queue, dependantChain, identifiers);
			}
			this.representor.setDependantChain(dependantChain);
		}
		if (!this.representor.getTaskDependencies().isEmpty()) {
			final List<List<TaskRepresentor>> dependencyChain = new ArrayList<>();
			final Queue<TaskRepresentor> queue = new LinkedList<TaskRepresentor>();
			queue.add(this.representor);
			TaskRepresentor current;
			while ((current = queue.poll()) != null) {
				this.traverseDependencies(current, queue, dependencyChain, identifiers);
			}
			this.representor.setDependencyChain(dependencyChain);
		}
	}

	private void traverseDependants(TaskRepresentor node, Queue<TaskRepresentor> queue, List<List<TaskRepresentor>> dependantChain, List<Long> identifiers) {
		if (!node.getDependantTasks().isEmpty()) {
			final List<TaskRepresentor> nodes = new ArrayList<TaskRepresentor>(node.getDependantTasks());
			for (final TaskRepresentor dependant : node.getDependantTasks()) {
				if (identifiers.contains(dependant.getId())) {
					nodes.remove(dependant);
				} else {
					identifiers.add(dependant.getId());
				}
			}
			if (!nodes.isEmpty()) {
				dependantChain.add(nodes);
			}
			for (int i = 0; i < node.getDependantTasks().size(); i++) {
				final TaskRepresentor dependantNode = this.taskConverter
						.toSimplified(this.taskService.readWithDirectDependencies(node.getDependantTasks().get(i).getId()));
				queue.add(dependantNode);
			}
		}
	}

	private void traverseDependencies(TaskRepresentor node, Queue<TaskRepresentor> queue, List<List<TaskRepresentor>> dependencyChain, List<Long> identifiers) {
		if (!node.getTaskDependencies().isEmpty()) {
			final List<TaskRepresentor> nodes = new ArrayList<TaskRepresentor>(node.getTaskDependencies());
			for (final TaskRepresentor dependency : node.getTaskDependencies()) {
				if (identifiers.contains(dependency.getId())) {
					nodes.remove(dependency);
				} else {
					identifiers.add(dependency.getId());
				}
			}
			if (!nodes.isEmpty()) {
				dependencyChain.add(nodes);
			}
			for (int i = 0; i < node.getTaskDependencies().size(); i++) {
				final TaskRepresentor dependencyNode = this.taskConverter
						.toSimplified(this.taskService.readWithDirectDependencies(node.getTaskDependencies().get(i).getId()));
				queue.add(dependencyNode);
			}
		}
	}

	public void provideDependantCount() {
		int total = 0;
		for (final List<TaskRepresentor> dependantLevel : this.representor.getDependantChain()) {
			total += dependantLevel.size();
		}
		this.representor.setDependantCount(total);
	}

	public void provideDependencyCount() {
		int total = 0;
		for (final List<TaskRepresentor> dependencyLevel : this.representor.getDependencyChain()) {
			total += dependencyLevel.size();
		}
		this.representor.setDependencyCount(total);
	}

	private void provideEstimationDetails() {
		this.provider.provideBaseEstimationDetails(this.representor);
	}

}
