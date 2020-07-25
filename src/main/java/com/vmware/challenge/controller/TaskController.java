package com.vmware.challenge.controller;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vmware.challenge.dto.TaskDetailsDto;
import com.vmware.challenge.dto.TaskIDDto;
import com.vmware.challenge.dto.TaskStatusDto;
import com.vmware.challenge.error.custom.DataReadFailedException;
import com.vmware.challenge.model.NumGenTask;
import com.vmware.challenge.model.NumGenTask.STATUS;
import com.vmware.challenge.service.TaskService;

@RestController
@Validated
@RequestMapping("/api")
public class TaskController {
	private static final String VALIDATION_NUMLIST = "^get_numlist$";
	
	@Autowired
	private TaskService taskService;

	@Autowired
	private ModelMapper modelMapper;


	@PostMapping("/generate")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<TaskIDDto> createTask(@Valid @RequestBody TaskDetailsDto taskDetail) {
		NumGenTask task = modelMapper.map(taskDetail, NumGenTask.class);
		task = taskService.createTask(task);
		TaskIDDto taskResponseDto = mapTaskToTaskResponse(task);
		return ResponseEntity.status(202).body(taskResponseDto);
	}

	@GetMapping("/tasks/{id}/status")
	public ResponseEntity<TaskStatusDto> checkTaskStatus(@Valid @NotNull @PathVariable(required = true) UUID id) {
		STATUS status = taskService.getStatus(id);
		return ResponseEntity.ok().body(new TaskStatusDto(status.toString()));
	}

	@GetMapping("/tasks/{id}")
	public ResponseEntity<TaskStatusDto> checkOutput(@Valid @NotNull @PathVariable(required = true) UUID id,
			@Valid @Pattern(regexp = VALIDATION_NUMLIST) @RequestParam(required = true) String action) throws DataReadFailedException {
		String data = taskService.readData(id);
		return ResponseEntity.ok().body(new TaskStatusDto(data));
	}

	private TaskIDDto mapTaskToTaskResponse(NumGenTask task) {
		TaskIDDto taskResponseDto = new TaskIDDto();
		taskResponseDto.setTask(task.getId());
		return taskResponseDto;
	}

}
