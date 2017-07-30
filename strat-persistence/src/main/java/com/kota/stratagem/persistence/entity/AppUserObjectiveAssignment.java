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
import com.kota.stratagem.persistence.query.UserObjectiveAssignmentQuery;

@Entity
@Table(name = "user_objective_assignments")
@NamedQueries(value = { //
		@NamedQuery(name = UserObjectiveAssignmentQuery.REMOVE_BY_ID, query = "DELETE FROM AppUserObjectiveAssignment a WHERE a.id=:" + AssignmentParameter.ID)
		//
})
@SequenceGenerator(name = "userObjectiveAssignmentGenerator", sequenceName = "user_objective_assignments_assignment_id_seq", allocationSize = 1)
public class AppUserObjectiveAssignment extends AbstractUserAssignment implements Serializable {

	private static final long serialVersionUID = -7689042201929946465L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userObjectiveAssignmentGenerator")
	@Column(name = "assignment_id", nullable = false, updatable = false, insertable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, targetEntity = Objective.class)
	@JoinColumn(name = "assignment_objective", referencedColumnName = "objective_id", nullable = false)
	private Objective objective;

	public AppUserObjectiveAssignment(AppUser entrustor, AppUser recipient, Objective objective, Date creationDate) {
		super();
		this.objective = objective;
		this.recipient = recipient;
		this.entrustor = entrustor;
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
		return "UserObjectiveAssignment [id=" + this.id + ", objective=" + this.objective + "]";
	}

}
