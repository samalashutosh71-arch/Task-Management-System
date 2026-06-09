package com.ashu.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

	@Id
	@SequenceGenerator(name = "seq2",sequenceName = "TaskApp_Task_seq",allocationSize = 1,initialValue = 1)
	@GeneratedValue(generator = "seq2",strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;
    private String description;
    private String status; // OPEN, IN_PROGRESS, DONE
    private LocalDateTime deadline;
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //@JsonIgnore
    private User user;
}
