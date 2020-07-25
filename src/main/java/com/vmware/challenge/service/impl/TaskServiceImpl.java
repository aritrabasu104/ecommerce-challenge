package com.vmware.challenge.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vmware.challenge.error.custom.DataReadFailedException;
import com.vmware.challenge.model.NumGenTask;
import com.vmware.challenge.model.NumGenTask.STATUS;
import com.vmware.challenge.repository.TaskRepository;
import com.vmware.challenge.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Value("${output.filepath}")
	private String filePath;
	
	@Autowired
	private TaskRepository taskRepository;

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
			Path path = Paths.get(filePath,task.getId()+"_output.txt");
			long goal = task.getGoal();
			long step = task.getStep();
			try {
				Path folder = Paths.get(filePath);
				if(!Files.exists(folder))
					Files.createDirectory(Paths.get(filePath));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				task.setStatus(STATUS.ERROR);
				return;
			}
			try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
				StringBuffer sb = new StringBuffer();
				do {
					sb.append(goal + ",");
					goal = goal - step;
				} while (goal >= step);
				if (goal >= 0) {
					sb.append(goal);
				}
				writer.write(sb.toString());
				task.setStatus(STATUS.SUCCESS);
			} catch (IOException e) {
				task.setStatus(STATUS.ERROR);
			}finally {
				taskRepository.save(task);
			}
		});

	}

	@Override
	public STATUS getStatus(UUID id) {
		return taskRepository.findById(id).get().getStatus();
	}

	@Override
	public String readData(UUID taskId) throws DataReadFailedException {
		String result = null;
		Path path = Paths.get(filePath,taskRepository.findById(taskId).get().getId()+"_output.txt");
		 try (BufferedReader br = Files.newBufferedReader(path,  StandardCharsets.UTF_8)) {
			 result = br.readLine();
		 }catch (IOException e) {
			throw new DataReadFailedException(taskId.toString(), ExceptionUtils.getRootCauseMessage(e));
		}
		return result;
	}

}
