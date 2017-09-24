package com.kota.stratagem.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.kota.stratagem.persistence.parameter.AssignmentParameter;
import com.kota.stratagem.persistence.query.TeamObjectiveAssignmentQuery;

@Entity
@Table(name = "team_objective_assignments")
@NamedQueries(value = { //
		@NamedQuery(name = TeamObjectiveAssignmentQuery.GET_BY_ID, query = "SELECT a FROM TeamObjectiveAssignment a WHERE a.id=:" + AssignmentParameter.ID),
		@NamedQuery(name = TeamObjectiveAssignmentQuery.REMOVE_BY_ID, query = "DELETE FROM TeamObjectiveAssignment a WHERE a.id=:" + AssignmentParameter.ID)
		//
})
@SequenceGenerator(name = "teamObjectiveAssignmentGenerator", sequenceName = "team_objective_assignments_assignment_id_seq", allocationSize = 1)
public class TeamObjectiveAssignment extends AbstractTeamAssignment implements Serializable {

	private static final long serialVersionUID = -3593130192401269344L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamObjectiveAssignmentGenerator")
	@Column(name = "assignment_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = Objective.class)
	@JoinColumn(name = "assignment_objective", referencedColumnName = "objective_id", nullable = false)
	private Objective objective;

	public TeamObjectiveAssignment() {
		super();
		this.creationDate = new Date();
	}

	public TeamObjectiveAssignment(Objective objective, Date creationDate) {
		super();
		this.objective = objective;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Objective getObjective() {
		return this.objective;
	}

	public void setObjective(Objective objective) {
		this.objective = objective;
	}

	@Override
	public String toString() {
		return "TeamObjectiveAssignment [id=" + this.id + ", objective=" + this.objective + "]";
	}

}
