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
import com.kota.stratagem.ejbservice.converter.ObjectiveConverter;
import com.kota.stratagem.ejbservice.converter.ProjectConverter;
import com.kota.stratagem.ejbservice.converter.SubmoduleConverter;
import com.kota.stratagem.ejbservice.converter.TaskConverter;
import com.kota.stratagem.ejbservice.domain.TaskDependencyLayer;
import com.kota.stratagem.ejbservice.interceptor.Certified;
import com.kota.stratagem.ejbservice.qualifier.TaskOriented;
import com.kota.stratagem.ejbserviceclient.domain.ProjectRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.SubmoduleRepresentor;
import com.kota.stratagem.ejbserviceclient.domain.TaskRepresentor;
import com.kota.stratagem.persistence.service.ObjectiveService;
import com.kota.stratagem.persistence.service.ProjectService;
import com.kota.stratagem.persistence.service.SubmoduleService;
import com.kota.stratagem.persistence.service.TaskService;

@TaskOriented
public class TaskExtensionManager extends AbstractDTOExtensionManager {

	@EJB
	private ObjectiveService objectiveService;

	@EJB
	private ProjectService projectService;

	@EJB
	private SubmoduleService submoduleService;

	@EJB
	private TaskService taskService;

	@Inject
	private ObjectiveConverter objectiveConverter;

	@Inject
	private ProjectConverter projectConverter;

	@Inject
	private SubmoduleConverter submoduleConverter;

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
		this.prepareSuperComponents();
		this.provideDependencyChain();
		this.provideDependantCount();
		this.provideDependencyCount();
		this.provideEstimationDetails();
	}

	@Override
	protected void addOwnerDependantProperties() {

	}

	private void prepareSuperComponents() {
		if(this.representor.getObjective() != null) {
			this.representor.setObjective(this.objectiveConverter.toDispatchable(this.objectiveService.readWithMonitoring(this.representor.getObjective().getId())));
		} else if(this.representor.getProject() != null) {
			ProjectRepresentor parentProject = this.projectConverter.toDispatchable(this.projectService.readWithMonitoring(this.representor.getProject().getId()));
			parentProject.setObjective(this.objectiveConverter.toDispatchable(this.objectiveService.readWithMonitoring(parentProject.getObjective().getId())));
			this.representor.setProject(parentProject);
		} else if(this.representor.getSubmodule() != null) {
			SubmoduleRepresentor parentSubmodule = this.submoduleConverter.toDispatchable(this.submoduleService.readWithMonitoring(this.representor.getSubmodule().getId()));
			ProjectRepresentor parentProject = this.projectConverter.toDispatchable(this.projectService.readWithMonitoring(parentSubmodule.getProject().getId()));
			parentProject.setObjective(this.objectiveConverter.toDispatchable(this.objectiveService.readWithMonitoring(parentProject.getObjective().getId())));
			parentSubmodule.setProject(parentProject);
			this.representor.setSubmodule(parentSubmodule);
		}
	}

	@Override
	protected void sortSpecializedCollections() {
		Collections.reverse(this.representor.getDependantChain());
		for(final List<TaskRepresentor> dependantLevel : this.representor.getDependantChain()) {
			Collections.sort(dependantLevel, new TaskNameComparator());
		}
		for(final List<TaskRepresentor> dependencyLevel : this.representor.getDependencyChain()) {
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
		if(!this.representor.getDependantTasks().isEmpty() || !this.representor.getTaskDependencies().isEmpty()) {
			final Queue<TaskRepresentor> queue = new LinkedList<TaskRepresentor>();
			final List<Long> identifiers = new ArrayList<>();
			representor.setDependencyLevel(0);
			TaskRepresentor currentNode;
			if(!this.representor.getDependantTasks().isEmpty()) {
				final List<List<TaskRepresentor>> dependantChain = new ArrayList<>();
				final List<TaskDependencyLayer> dependencyLayers = new ArrayList<>();
				queue.add(this.representor);
				while((currentNode = queue.poll()) != null) {
					traverseDependants(currentNode, queue, identifiers, dependencyLayers);
				}
				this.representor.setDependantChain(this.populateDependencyChain(dependantChain, dependencyLayers));
			}
			if(!this.representor.getTaskDependencies().isEmpty()) {
				final List<List<TaskRepresentor>> dependencyChain = new ArrayList<>();
				final List<TaskDependencyLayer> dependencyLayers = new ArrayList<>();
				queue.add(this.representor);
				while((currentNode = queue.poll()) != null) {
					this.traverseDependencies(currentNode, queue, identifiers, dependencyLayers);
				}
				this.representor.setDependencyChain(this.populateDependencyChain(dependencyChain, dependencyLayers));
			}
		}
	}

	private void traverseDependants(TaskRepresentor currentNode, Queue<TaskRepresentor> queue, List<Long> identifiers, List<TaskDependencyLayer> dependencyLayers) {
		if(!currentNode.getDependantTasks().isEmpty()) {
			final List<TaskRepresentor> nodes = new ArrayList<TaskRepresentor>(currentNode.getDependantTasks());
			for(final TaskRepresentor dependant : currentNode.getDependantTasks()) {
				this.mergeIntoIdentifiers(identifiers, dependant, nodes);
			}
			this.mergeIntoDependencyLayer(dependencyLayers, nodes, currentNode.getDependencyLevel() + 1);
			for(int i = 0; i < currentNode.getDependantTasks().size(); i++) {
				final TaskRepresentor dependantNode = this.taskConverter.toSimplified(this.taskService.readWithDirectDependencies(currentNode.getDependantTasks().get(i).getId()));
				dependantNode.setDependencyLevel(currentNode.getDependencyLevel() + 1);
				queue.add(dependantNode);
			}
		}
	}

	private void traverseDependencies(TaskRepresentor currentNode, Queue<TaskRepresentor> queue, List<Long> identifiers, List<TaskDependencyLayer> dependencyLayers) {
		if(!currentNode.getTaskDependencies().isEmpty()) {
			final List<TaskRepresentor> nodes = new ArrayList<TaskRepresentor>(currentNode.getTaskDependencies());
			for(final TaskRepresentor dependency : currentNode.getTaskDependencies()) {
				this.mergeIntoIdentifiers(identifiers, dependency, nodes);
			}
			this.mergeIntoDependencyLayer(dependencyLayers, nodes, currentNode.getDependencyLevel() + 1);
			for(int i = 0; i < currentNode.getTaskDependencies().size(); i++) {
				final TaskRepresentor dependencyNode = this.taskConverter.toSimplified(this.taskService.readWithDirectDependencies(currentNode.getTaskDependencies().get(i).getId()));
				dependencyNode.setDependencyLevel(currentNode.getDependencyLevel() + 1);
				queue.add(dependencyNode);
			}
		}
	}

	private void mergeIntoIdentifiers(List<Long> identifiers, TaskRepresentor node, List<TaskRepresentor> nodes) {
		if(identifiers.contains(node.getId())) {
			nodes.remove(node);
		} else {
			identifiers.add(node.getId());
		}
	}

	private void mergeIntoDependencyLayer(List<TaskDependencyLayer> layers, List<TaskRepresentor> nodes, int dependencyLevel) {
		TaskDependencyLayer index = new TaskDependencyLayer(dependencyLevel, null);
		if(layers.contains(index)) {
			layers.get(layers.indexOf(index)).addDependencies(nodes);
		} else {
			layers.add(new TaskDependencyLayer(dependencyLevel, nodes));
		}
	}

	private List<List<TaskRepresentor>> populateDependencyChain(List<List<TaskRepresentor>> dependantChain, List<TaskDependencyLayer> dependencyLayers) {
		for(TaskDependencyLayer layer : dependencyLayers) {
			if(!layer.getDependencies().isEmpty()) {
				Collections.sort(layer.getDependencies(), new TaskNameComparator());
				dependantChain.add(layer.getDependencies());
			}
		}
		return dependantChain;
	}

	public void provideDependantCount() {
		int total = 0;
		for(final List<TaskRepresentor> dependantLevel : this.representor.getDependantChain()) {
			total += dependantLevel.size();
		}
		this.representor.setDependantCount(total);
	}

	public void provideDependencyCount() {
		int total = 0;
		for(final List<TaskRepresentor> dependencyLevel : this.representor.getDependencyChain()) {
			total += dependencyLevel.size();
		}
		this.representor.setDependencyCount(total);
	}

	private void provideEstimationDetails() {
		this.provider.provideBaseEstimationDetails(this.representor);
	}

}
