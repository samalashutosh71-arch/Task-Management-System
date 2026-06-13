package com.ashu.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.entity.Task;

public interface TaskRepositary extends JpaRepository<Task, Long> 
{
	public List<Task>findByStatus(String status);

	
	/*@Modifying
	@Query("UPDATE Task t SET t.status='COMPLETED' WHERE t.id=:id")
	int updateTaskStatus(Long id);
	@Modifying
	@Query("DELETE FROM Task t WHERE t.id=:id")
	int deleteTask(Long id);*/

}
