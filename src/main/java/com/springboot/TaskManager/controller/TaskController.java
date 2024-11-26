package com.springboot.TaskManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.TaskManager.dto.ResponseMessageDto;
import com.springboot.TaskManager.exception.ResourceNotFoundException;
import com.springboot.TaskManager.model.Task;
import com.springboot.TaskManager.service.TaskService;

@RestController
public class TaskController {

	@Autowired
	private TaskService taskService;

	@PostMapping("/task/add")
	public Task addTask(@RequestBody Task task) {
		return taskService.insert(task);
	}

	@GetMapping("/task/all")
	public List<Task> getAllTask() {
		List<Task> list = taskService.getAllTask();
		return list;
	}

	@GetMapping("/task/getById/{id}")
	public ResponseEntity<?> getById(@PathVariable int id) throws ResourceNotFoundException {
		Task task = taskService.validate(id);
		return ResponseEntity.ok(task);
	}

	@PostMapping("/task/update/{id}")
	public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Task newTask, ResponseMessageDto dto) {
		try {
			Task oldTask = taskService.validate(id);
			if (newTask.getTitle() != null)
				oldTask.setTitle(newTask.getTitle());
			if (newTask.getDescription() != null)
				oldTask.setDescription(newTask.getDescription());
			if (newTask.getDue_date() != null)
				oldTask.setDue_date(newTask.getDue_date());
			if (newTask.getPriority() != null)
				oldTask.setPriority(newTask.getPriority());
			if (newTask.getStatus() != null)
				oldTask.setStatus(newTask.getStatus());

			// save
			oldTask = taskService.insert(oldTask);
			return ResponseEntity.ok(oldTask);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}

	@DeleteMapping("/task/deletebyid/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id, ResponseMessageDto dto){
		try {
			taskService.validate(id);
			taskService.delete(id);
		}	catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		dto.setMsg("Task deleted");
		return ResponseEntity.ok(dto);
	}


}
