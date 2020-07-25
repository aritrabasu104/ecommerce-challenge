package com.vmware.challenge.service;

import java.util.UUID;

import com.vmware.challenge.error.custom.DataReadFailedException;
import com.vmware.challenge.model.NumGenTask;

public interface TaskService {

	public NumGenTask createTask(NumGenTask task);
	public NumGenTask.STATUS getStatus(UUID id);
	public String readData(UUID taskId) throws DataReadFailedException;
}
