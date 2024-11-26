package com.springboot.TaskManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.TaskManager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

}
