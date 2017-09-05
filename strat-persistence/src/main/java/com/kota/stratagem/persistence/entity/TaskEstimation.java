package com.kota.stratagem.persistence.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kota.stratagem.persistence.parameter.TaskParameter;
import com.kota.stratagem.persistence.query.TaskEstimationQuery;

@Entity
@Table(name = "task_estimations")
@SequenceGenerator(name = "taskEstimationGenerator", sequenceName = "task_estimations_estimation_id_seq", allocationSize = 1)
@NamedQueries(value = { //
		@NamedQuery(name = TaskEstimationQuery.REMOVE_BY_TASK_ID, query = "DELETE FROM TaskEstimation te WHERE te.task.id=:" + TaskParameter.ID)
		//
})
public class TaskEstimation implements Serializable {

	private static final long serialVersionUID = -1311102550104986804L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taskEstimationGenerator")
	@Column(name = "estimation_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@Column(name = "estimation_pessimist", nullable = false)
	private double pessimistic;

	@Column(name = "estimation_realist", nullable = false)
	private double realistic;

	@Column(name = "estimation_optimist", nullable = false)
	private double optimistic;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Task.class)
	@JoinColumn(name = "estimation_task", referencedColumnName = "task_id", nullable = false)
	private Task task;

	public TaskEstimation() {
		super();
	}

	public TaskEstimation(Long id, double pessimistic, double realistic, double optimistic, Task task) {
		super();
		this.id = id;
		this.pessimistic = pessimistic;
		this.realistic = realistic;
		this.optimistic = optimistic;
		this.task = task;
	}

	public TaskEstimation(double pessimistic, double realistic, double optimistic, Task task) {
		super();
		this.pessimistic = pessimistic;
		this.realistic = realistic;
		this.optimistic = optimistic;
		this.task = task;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public double getPessimistic() {
		return this.pessimistic;
	}

	public double getRealistic() {
		return this.realistic;
	}

	public double getOptimistic() {
		return this.optimistic;
	}

	@Override
	public String toString() {
		return "TaskEstimation [id=" + this.id + ", pessimistic=" + this.pessimistic + ", realistic=" + this.realistic + ", optimistic=" + this.optimistic
				+ "]";
	}

}
