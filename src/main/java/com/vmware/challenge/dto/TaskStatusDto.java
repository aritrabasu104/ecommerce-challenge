package com.vmware.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskStatusDto {
	private String result;

	public TaskStatusDto(String result) {
		this.result = result;
	}
	@JsonProperty("result")
	public String getStatus() {
		return result;
	}

	public void setStatus(String result) {
		this.result = result;
	}
	
}
