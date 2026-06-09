package com.ashu.repositary;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashu.entity.User;

public interface UserRepositary extends JpaRepository<User, Long> 
{
	 Optional<User> findByEmail(String email);
	 List<User> findByManagerId(Long managerId);
	 //find by name only specific manager users
	 @Query("SELECT u FROM User u WHERE u.manager.id = :managerId AND LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	 List<User> searchByNameAndManager(@Param("name") String name,
	                                  @Param("managerId") Long managerId);
	 
	 Page<User>findByManagerId(Long managerId,Pageable pageable);

}
