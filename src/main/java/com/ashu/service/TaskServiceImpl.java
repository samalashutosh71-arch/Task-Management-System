package com.ashu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ashu.customexception.ResourceNotFoundException;
import com.ashu.entity.Task;
import com.ashu.entity.User;
import com.ashu.repositary.TaskRepositary;
import com.ashu.repositary.UserRepositary;

@Service
public class TaskServiceImpl implements TaskService 
{
	@Autowired
	private TaskRepositary taskRepo;
	@Autowired
	private UserRepositary userRepo;

	@Override
	public Task createTask(Task task) {
		Task save=taskRepo.save(task);
		return save;
	}

	@Override
	public List<Task> getAlltask() {
		List<Task>tasks=taskRepo.findAll();
		return tasks;
	}

	@Override
	public Task getTaskById(long id) {
		Task task=taskRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id));
		return task;
	}

	@Override
	public Task updateTask(long id, Task updatedTask) {
		Task task=taskRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id));
		task.setStatus(updatedTask.getStatus());
		task.setDescription(updatedTask.getDescription());
		 task.setDeadline(updatedTask.getDeadline());
		 //if task status update to comp mark that time as completed
		 if("COMPLETED".equalsIgnoreCase(updatedTask.getStatus())) {
			 task.setCompletedAt(LocalDateTime.now());
			 System.out.println("Completed At = " + task.getCompletedAt());
		 }
		 //if task updated to reopen then again it will null
		 if("REOPEN".equalsIgnoreCase(updatedTask.getStatus())) {
			 task.setCompletedAt(null);
		 }
		taskRepo.save(task);
		return task;
	}

	@Override
	@Transactional
	public String deleteTask(long id) {
     Task task=taskRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id));
     
     User user=task.getUser();
   //first remove the task
     if(user!=null) 
     {
    	 user.getTasks().remove(task);
    	 
     }
     taskRepo.delete(task);

		return "task is deleted";
	}
	
	@Override
	public List<Task> findByTaskStatus(String status) {
		String filterStatus=status.trim().toUpperCase();
		List<Task>tasks=taskRepo.findByStatus(filterStatus);
		
		return tasks;
	}
	
	@Override
	public Task assignTask(long taskId, long userId, Task updatedTask) {

	    Task task = taskRepo.findById(taskId)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND, "Task not found"));

	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND, "User not found"));
        //manager of the user
	    User manager = user.getManager();

	    if (manager == null) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST, "User has no manager assigned");
	    }

	    //ensure that the right maneger try to assign task
	    if (task.getUser() != null &&
	        task.getUser().getManager() != null &&
	        !task.getUser().getManager().getId().equals(manager.getId())) {

	        throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN,
	                "You cannot assign task to another manager's user"
	        );
	    }

	    //force to add deadline
	    if (updatedTask.getDeadline() == null) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST, "Deadline is required before assigning task"
	        );
	    }

	    //check if try to put invalid time
	    if (updatedTask.getDeadline().isBefore(java.time.LocalDateTime.now())) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_REQUEST, "Deadline cannot be in the past"
	        );
	    }
        //asssign to user
	    task.setUser(user);

	    // set deadline
	    task.setDeadline(updatedTask.getDeadline());

	    return taskRepo.save(task);
	}
	//get user by id
	@Override
	public List<Task> getTaskByUserId(long userid)
	{
		User user=userRepo.findById(userid).get();
		List<Task>tasksOfUser=user.getTasks();
		
		return tasksOfUser;
	}
	
	//all task pagination
	@Override
	public Page<Task> getAllTaskByPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Task> taskPage = taskRepo.findAll(pageable);
		return taskPage;
	}
	
	

}
