package com.vmware.challenge.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

@Entity
public class NumGenTask {
	public enum STATUS {
		IN_PROGRESS, SUCCESS, ERROR
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NonNull
	@Min(1)
	private Long goal;

	@NotNull
	@Min(1)
	private Long step;

	private STATUS status;

	public NumGenTask() {

	}
	public NumGenTask(Long goal, Long step) {
		this.goal = goal;
		this.step = step;
	}
	public NumGenTask(Long goal, Long step,UUID id ,STATUS status) {
		this.goal = goal;
		this.step = step;
		this.id = id;
		this.status = status;
	}
	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the goal
	 */
	public Long getGoal() {
		return goal;
	}

	/**
	 * @param goal the goal to set
	 */
	public void setGoal(Long goal) {
		this.goal = goal;
	}

	/**
	 * @return the step
	 */
	public Long getStep() {
		return step;
	}

	/**
	 * @param step the step to set
	 */
	public void setStep(Long step) {
		this.step = step;
	}

	/**
	 * @return the status
	 */
	public STATUS getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(STATUS status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumGenTask other = (NumGenTask) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
