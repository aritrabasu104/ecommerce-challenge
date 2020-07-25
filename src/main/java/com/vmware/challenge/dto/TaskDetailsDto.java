package com.vmware.challenge.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TaskDetailsDto {

	@NotEmpty(message = "Please provide a Goal")
	private String goal;

	@NotNull(message = "Please provide a Step")
	private String step;

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

}