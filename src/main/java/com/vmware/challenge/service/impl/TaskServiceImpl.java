package com.vmware.challenge.service.impl;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmware.challenge.error.custom.DataReadFailedException;
import com.vmware.challenge.model.NumGenTask;
import com.vmware.challenge.model.NumGenTask.STATUS;
import com.vmware.challenge.repository.FileRepository;
import com.vmware.challenge.repository.TaskRepository;
import com.vmware.challenge.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private FileRepository fileRepository;


	@Autowired
	private ExecutorService executorService;

	
	@Override
	public NumGenTask createTask(NumGenTask task) {
		task.setStatus(STATUS.IN_PROGRESS);
		task = taskRepository.save(task);
		scheduleTask(task);
		return task;
	}

	private void scheduleTask(NumGenTask task) {
		executorService.execute(() -> {
			NumGenTask taskReturned = fileRepository.writeFile(task);
			taskRepository.save(taskReturned);
		});

	}

	@Override
	public STATUS getStatus(UUID id) {
		return taskRepository.findById(id).get().getStatus();
	}

	@Override
	public String readData(UUID taskId) throws DataReadFailedException {
		return fileRepository.readFile(taskRepository.findById(taskId).get().getId());
	}

}
