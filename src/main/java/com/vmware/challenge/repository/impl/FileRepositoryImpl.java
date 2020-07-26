package com.vmware.challenge.repository.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.vmware.challenge.error.custom.DataReadFailedException;
import com.vmware.challenge.model.NumGenTask;
import com.vmware.challenge.model.NumGenTask.STATUS;
import com.vmware.challenge.repository.FileRepository;

@Component
public class FileRepositoryImpl implements FileRepository {

	@Value("${output.filepath}")
	private String filePath;

	@Override
	public NumGenTask writeFile(NumGenTask task) {

		Path path = Paths.get(filePath, task.getId() + "_output.txt");
		long goal = task.getGoal();
		long step = task.getStep();
		try {
			Path folder = Paths.get(filePath);
			if (!Files.exists(folder))
				Files.createDirectory(Paths.get(filePath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			task.setStatus(STATUS.ERROR);
			return task;
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
		}
		return task;
	}

	@Cacheable("task_output")
	@Override
	public String readFile(UUID taskId) throws DataReadFailedException {
		String result = null;
		Path path = Paths.get(filePath, taskId + "_output.txt");
		try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			result = br.readLine();
		} catch (IOException e) {
			throw new DataReadFailedException(taskId.toString(), ExceptionUtils.getRootCauseMessage(e));
		}
		System.out.println("actual read");
		return result;
	}

}
