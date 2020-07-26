package com.vmware.challenge.repository;

import java.util.UUID;

import com.vmware.challenge.error.custom.DataReadFailedException;
import com.vmware.challenge.model.NumGenTask;

public interface FileRepository {
	public NumGenTask writeFile(NumGenTask task);
	public String readFile(UUID taskId) throws DataReadFailedException;
	
}
