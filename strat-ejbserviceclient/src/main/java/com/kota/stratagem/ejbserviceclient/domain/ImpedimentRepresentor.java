package com.kota.stratagem.ejbserviceclient.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImpedimentRepresentor implements Serializable {

	private static final long serialVersionUID = 3043909154399999429L;

	private Long id;
	private final String name;
	private final String description;
	private final PriorityRepresentor priority;
	private final ImpedimentStatusRepresentor status;
	private final Date reportDate;
	private final AppUserRepresentor reporter;
	private final AppUserRepresentor processor;
	private final AppUserRepresentor creator;
	private final Date creationDate;
	private final AppUserRepresentor modifier;
	private final Date modificationDate;
	private final List<RemedyRepresentor> remedies;
	private ProjectRepresentor project;
	private TaskRepresentor task;

	public ImpedimentRepresentor() {
		this(null, "", "", PriorityRepresentor.MEDIUM, ImpedimentStatusRepresentor.OPEN, new Date(), null, null, null, new Date(), null, new Date());
	}

	// Projects and Objectives removed from constructor due to structure ambiguity
	public ImpedimentRepresentor(Long id, String name, String description, PriorityRepresentor priority, ImpedimentStatusRepresentor status, Date reportDate,
			AppUserRepresentor reporter, AppUserRepresentor processor, AppUserRepresentor creator, Date creationDate, AppUserRepresentor modifier,
			Date modificationDate) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.reportDate = reportDate;
		this.reporter = reporter;
		this.processor = processor;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
		this.remedies = new ArrayList<>();
		this.project = null;
		this.task = null;
	}

	public ImpedimentRepresentor(String name, String description, PriorityRepresentor priority, ImpedimentStatusRepresentor status, Date reportDate,
			AppUserRepresentor reporter, AppUserRepresentor processor, AppUserRepresentor creator, Date creationDate, AppUserRepresentor modifier,
			Date modificationDate) {
		super();
		this.name = name;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.reportDate = reportDate;
		this.reporter = reporter;
		this.processor = processor;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modifier = modifier;
		this.modificationDate = modificationDate;
		this.remedies = new ArrayList<>();
		this.project = null;
		this.task = null;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public PriorityRepresentor getPriority() {
		return this.priority;
	}

	public ImpedimentStatusRepresentor getStatus() {
		return this.status;
	}

	public Date getReportDate() {
		return this.reportDate;
	}

	public AppUserRepresentor getReporter() {
		return this.reporter;
	}

	public AppUserRepresentor getProcessor() {
		return this.processor;
	}

	public AppUserRepresentor getCreator() {
		return this.creator;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public AppUserRepresentor getModifier() {
		return this.modifier;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	public List<RemedyRepresentor> getRemedies() {
		return this.remedies;
	}

	public ProjectRepresentor getProject() {
		return this.project;
	}

	public void setProject(ProjectRepresentor project) {
		this.project = project;
	}

	public TaskRepresentor getTask() {
		return this.task;
	}

	public void setTask(TaskRepresentor task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "\nImpedimentRepresentor [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority
				+ ", status=" + this.status + ", reportDate=" + this.reportDate + ", reporter=" + this.reporter + ", processor=" + this.processor + ", creator="
				+ this.creator + ", creationDate=" + this.creationDate + ", modifier=" + this.modifier + ", modificationDate=" + this.modificationDate
				+ ", remedies=" + this.remedies + ", project=" + this.project + ", task=" + this.task + "]\n";
	}

	public void addRemedy(RemedyRepresentor remedy) {
		this.remedies.add(remedy);
	}

}
