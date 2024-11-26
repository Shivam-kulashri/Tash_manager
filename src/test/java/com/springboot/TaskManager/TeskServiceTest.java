package com.springboot.TaskManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.TaskManager.enums.Priority;
import com.springboot.TaskManager.enums.Status;
import com.springboot.TaskManager.exception.ResourceNotFoundException;
import com.springboot.TaskManager.model.Task;
import com.springboot.TaskManager.repository.TaskRepository;
import com.springboot.TaskManager.service.TaskService;

@SpringBootTest
public class TeskServiceTest {

	@InjectMocks
	private TaskService taskService;

	@Mock
	private TaskRepository taskRepository;

	private Task task;

	@BeforeEach
	public void ititSetup() {
		task = new Task(45, "Add frontend", "Add frontend to recieve the API calls before the deadline",
				LocalDate.parse("2024-12-03"), Priority.High, Status.Pending);
	}

	@Test
	public void postTasktest() {
		when(taskRepository.save(task)).thenReturn(task);

		Task newTask = taskService.insert(task);

		// test & compare
		assertNotNull(newTask);
		verify(taskRepository, times(1)).save(task);
	}

	@Test
	void getByIdTest() {
		when(taskRepository.findById(2)).thenReturn(Optional.empty());

		Task newTask = null;
		try {
			newTask = taskService.validate(2);

		} catch (ResourceNotFoundException e) {
			assertEquals("Invalid ID, doesn't exist".toLowerCase(), e.getMessage().toLowerCase());

		}
		assertNull(newTask);

		verify(taskRepository, times(1)).findById(2);
	}

	@Test
	public void deleteCustomerByIdTestSuccess() {
		// Arrange
		when(taskRepository.findById(1)).thenReturn(Optional.of(task));

		// Act
		taskService.delete(1);

		// Assert
		verify(taskRepository, times(1)).deleteById(1);
	}

	@Test
	public void deleteCustomerByIdTestFailure() {
	    // Arrange
	    when(taskRepository.findById(2)).thenReturn(Optional.empty());

	    // Act
	    taskService.delete(2);

	    // Assert
	    verify(taskRepository, times(1)).deleteById(2);
	}
}
