package com.ashu.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User 
{
	@Id
	@SequenceGenerator(name = "seq1",sequenceName = "TaskApp_User_seq",allocationSize = 1,initialValue = 100)
	@GeneratedValue(generator = "seq1",strategy = GenerationType.SEQUENCE)
    private Long id;
	 @NotBlank(message = "Name cannot be empty")
    private String name;
	 @NotBlank(message = "Email is required")
	 @Email(message = "Invalid email format")
    private String email;

	@NotBlank(message = "Role is required")
    private String role;
	private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Task> tasks;
    
    //for handelling team of user only by manager 
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

}
