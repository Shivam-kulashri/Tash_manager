package com.springboot.TaskManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.TaskManager.exception.ResourceNotFoundException;
import com.springboot.TaskManager.model.Task;
import com.springboot.TaskManager.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public Task insert(Task task) {
		return taskRepository.save(task);
	}

	public List<Task> getAllTask() {
		return taskRepository.findAll();
	}

	public Task validate(int id) throws ResourceNotFoundException {

		Optional<Task> optional = taskRepository.findById(id);

		if (optional.isEmpty())
			throw new ResourceNotFoundException("Invalid ID, doesn't exist");
		return optional.get();
	}

	public void delete(int id) {
		taskRepository.deleteById(id);

	}

}
