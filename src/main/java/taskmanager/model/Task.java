package taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    private String description;

    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be in the future")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Status is required")
    @Column(nullable = false)
    private TaskStatus status;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors, getters, setters, and other methods
    public Task() {
    }

    public Task(String name, String description, LocalDateTime deadline, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }



    @JsonIgnore
    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public Task withId(Long id) {
        this.id = id;
        return this;
    }

    public Task withName(String name) {
        this.name = name;
        return this;
    }

    public Task withDescription(String description) {
        this.description = description;
        return this;
    }

    public Task withDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    public Task withStatus(TaskStatus status) {
        this.status = status;
        return this;
    }

    public Task withUser(User user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Task{id=%d, name='%s', description='%s', deadline=%s, status='%s', user=%s}",
                id, name, description, deadline, status, user);
    }



}