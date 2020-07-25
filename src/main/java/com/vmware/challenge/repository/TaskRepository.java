package com.vmware.challenge.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vmware.challenge.model.NumGenTask;

@Repository
public interface TaskRepository extends CrudRepository<NumGenTask, UUID> {

}
