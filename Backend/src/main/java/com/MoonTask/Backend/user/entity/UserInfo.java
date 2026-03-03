package com.MoonTask.Backend.user.entity;

import com.MoonTask.Backend.task.entity.TaskInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
* This class contains the fields about user related details which are helpful for registration purpose.
 * It contains the fields like username, email, password and has one to one entity mapping with task class.
 * It extends {@link org.springframework.security.core.userdetails.UserDetails} interface which have some
 * unimplemented methods I have implemented them here. This userDetails class will be helpful when implementing
 * spring security.*/
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "UserDetails")
public class UserInfo implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String name;
    // email should be unique, it should not be null, and it should not be updated because it is used as username for login.
    @Column(unique = true, nullable = false, updatable = false)
    private String email;
    // password should not be null, and it should have minimum 8 characters for security purpose.
    @Column(nullable = false)
    @Size(min = 8)
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TaskInfo> tasks;

    /**
     * This method is user full to assign task to user.
     * @param task contains the task information*/
    public void addTask(TaskInfo task){
        this.tasks  = new ArrayList<>();
        this.tasks.add(task);
        task.setUser(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
