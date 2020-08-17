package com.tomtom.challenge.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomtom.challenge.dto.CategoryReqDto;
import com.tomtom.challenge.dto.CategoryResDto;
import com.tomtom.challenge.model.Category;
import com.tomtom.challenge.service.AdminService;

@RestController
@Validated
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private ModelMapper modelMapper;

	
	@GetMapping("/admin/category")
	public ResponseEntity<List<CategoryResDto>> getCategories() {
		return ResponseEntity.status(200).body(adminService.getCategories().stream()
				.map(item -> modelMapper.map(item, CategoryResDto.class)).collect(Collectors.toList()));
	}
	
	@PostMapping("/admin/category")
	public ResponseEntity<CategoryResDto> addCategory(@Valid @RequestBody CategoryReqDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		category = adminService.addCategory(category);
		return ResponseEntity.status(201).body( modelMapper.map(category, CategoryResDto.class));
	}

//	@PostMapping("/generate")
//	@ResponseStatus(HttpStatus.CREATED)
//	public ResponseEntity<TaskIDDto> createTask(@Valid @RequestBody TaskDetailsDto taskDetail) {
//		NumGenTask task = modelMapper.map(taskDetail, NumGenTask.class);
//		task = taskService.createTask(task);
//		TaskIDDto taskResponseDto = mapTaskToTaskResponse(task);
//		return ResponseEntity.status(202).body(taskResponseDto);
//	}
//
//	@GetMapping("/tasks/{id}/status")
//	public ResponseEntity<TaskStatusDto> checkTaskStatus(@Valid @NotNull @PathVariable(required = true) UUID id) {
//		STATUS status = taskService.getStatus(id);
//		return ResponseEntity.ok().body(new TaskStatusDto(status.toString()));
//	}
//
//	@GetMapping("/tasks/{id}")
//	public ResponseEntity<TaskStatusDto> checkOutput(@Valid @NotNull @PathVariable(required = true) UUID id,
//			@Valid @Pattern(regexp = VALIDATION_NUMLIST) @RequestParam(required = true) String action) throws DataReadFailedException {
//		String data = taskService.readData(id);
//		return ResponseEntity.ok().body(new TaskStatusDto(data));
//	}
//
//	private TaskIDDto mapTaskToTaskResponse(NumGenTask task) {
//		TaskIDDto taskResponseDto = new TaskIDDto();
//		taskResponseDto.setTask(task.getId());
//		return taskResponseDto;
//	}

}
