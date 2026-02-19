package com.MoonTask.Backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.MoonTask.Backend.user.entity.UserInfo;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserInfo, Integer> {
    //this method will be used to find the user details by email which will be helpful for spring security authentication process.
    Optional<UserInfo> findByEmail(String email);
}
