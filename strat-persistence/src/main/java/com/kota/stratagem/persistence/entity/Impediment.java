package com.kota.stratagem.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kota.stratagem.persistence.entity.trunk.ImpedimentStatus;
import com.kota.stratagem.persistence.entity.trunk.Priority;
import com.kota.stratagem.persistence.parameter.ImpedimentParameter;
import com.kota.stratagem.persistence.query.ImpedimentQuery;

@Entity
@Table(name = "impediments")
@NamedQueries(value = { //
		@NamedQuery(name = ImpedimentQuery.COUNT_BY_ID, query = "SELECT COUNT(i) FROM Impediment i WHERE i.id=:" + ImpedimentParameter.ID),
		@NamedQuery(name = ImpedimentQuery.GET_ALL_IMPEDIMENTS, query = "SELECT i FROM Impediment i ORDER BY i.name"),
		@NamedQuery(name = ImpedimentQuery.GET_BY_ID, query = "SELECT i FROM Impediment i WHERE i.id=:" + ImpedimentParameter.ID),
		@NamedQuery(name = ImpedimentQuery.REMOVE_BY_ID, query = "DELETE FROM Impediment i WHERE i.id=:" + ImpedimentParameter.ID)
		//
})
@SequenceGenerator(name = "impedimentGenerator", sequenceName = "impediments_impediment_id_seq", allocationSize = 1)
public class Impediment implements Serializable {

	private static final long serialVersionUID = -8501472567228998129L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "impedimentGenerator")
	@Column(name = "impediment_id", nullable = false)
	private Long id;

	@Column(name = "impediment_name", nullable = false)
	private String name;

	@Column(name = "impediment_description", nullable = true)
	private String description;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "impediment_priority_id", nullable = false)
	private Priority priority;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "impediment_status_id", nullable = false)
	private ImpedimentStatus status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "impediment_report_date", nullable = false)
	private Date reportDate;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "impediment_reporter", nullable = false)
	private AppUser reporter;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "impediment_processor", nullable = true)
	private AppUser processor;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "impediment_creator", nullable = false)
	private AppUser creator;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "impediment_creation_date", nullable = false)
	private Date creationDate;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = AppUser.class)
	@JoinColumn(name = "impediment_modifier", nullable = false)
	private AppUser modifier;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "impediment_modification_date", nullable = false)
	private Date modificationDate;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Remedy.class, mappedBy = "impediment")
	private Set<Remedy> remedies;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Project.class)
	@JoinTable(name = "project_impediments", joinColumns = @JoinColumn(name = "task_impediment_impediment_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "project_impediment_project_id", nullable = false))
	private Project project;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Task.class)
	@JoinTable(name = "task_impediments", joinColumns = @JoinColumn(name = "task_impediment_impediment_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "task_impediment_task_id", nullable = false))
	private Task task;

	public Impediment() {
		this.remedies = new HashSet<>();
	}

	public Impediment(Long id, String name, String description, Priority priority, ImpedimentStatus status, Date reportDate, AppUser reporter,
			AppUser processor, AppUser creator, Date creationDate, AppUser modifier, Date modificationDate, Set<Remedy> remedies, Project project, Task task) {
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
		this.remedies = remedies;
		this.project = project;
		this.task = task;
	}

	public Impediment(String name, String description, Priority priority, ImpedimentStatus status, Date reportDate, AppUser reporter, AppUser processor,
			AppUser creator, Date creationDate, AppUser modifier, Date modificationDate, Set<Remedy> remedies, Project project, Task task) {
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
		this.remedies = remedies;
		this.project = project;
		this.task = task;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public ImpedimentStatus getStatus() {
		return this.status;
	}

	public void setStatus(ImpedimentStatus status) {
		this.status = status;
	}

	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public AppUser getReporter() {
		return this.reporter;
	}

	public void setReporter(AppUser reporter) {
		this.reporter = reporter;
	}

	public AppUser getProcessor() {
		return this.processor;
	}

	public void setProcessor(AppUser processor) {
		this.processor = processor;
	}

	public AppUser getCreator() {
		return this.creator;
	}

	public void setCreator(AppUser creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public AppUser getModifier() {
		return this.modifier;
	}

	public void setModifier(AppUser modifier) {
		this.modifier = modifier;
	}

	public Date getModificationDate() {
		return this.modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Set<Remedy> getRemedies() {
		return this.remedies;
	}

	public void setRemedies(Set<Remedy> remedies) {
		this.remedies = remedies;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "Impediment [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", priority=" + this.priority + ", status="
				+ this.status + ", reportDate=" + this.reportDate + ", reporter=" + this.reporter + ", processor=" + this.processor + ", creator="
				+ this.creator + ", creationDate=" + this.creationDate + ", modifier=" + this.modifier + ", modificationDate=" + this.modificationDate
				+ ", remedies=" + this.remedies + ", project=" + this.project + ", task=" + this.task + "]";
	}

}
