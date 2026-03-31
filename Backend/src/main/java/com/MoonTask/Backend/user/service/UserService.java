package com.MoonTask.Backend.user.service;

import com.MoonTask.Backend.security.exception.UserException;
import com.MoonTask.Backend.task.entity.TaskInfo;
import com.MoonTask.Backend.task.repository.TaskRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.MoonTask.Backend.user.mapper.MapperDTO;
import com.MoonTask.Backend.security.service.JwtService;
import com.MoonTask.Backend.user.entity.UserInfo;
import com.MoonTask.Backend.user.dto.CreateUserDTO;
import com.MoonTask.Backend.user.dto.UpdateUserDTO;
import com.MoonTask.Backend.user.repository.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Service class responsible for handling core user related business logic.
 * It interacts with the {@link UserRepo} to persist and retries user data.
 * @see UserRepo
 * @see BCryptPasswordEncoder
 * @see MapperDTO */
@Service
public class UserService implements CrudService<CreateUserDTO, UpdateUserDTO> {

    private final UserRepo repo;
    private final BCryptPasswordEncoder encoder;
    private final MapperDTO mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final RedisTemplate<String, Object> redisTemplate;
    private final TaskRepository repository;

    public UserService(UserRepo repo,
                       BCryptPasswordEncoder encoder,
                       MapperDTO mapper,
                       JwtService jwtService,
                       AuthenticationManager authManager,
                       RedisTemplate<String, Object> redisTemplate,
                       TaskRepository repository
    ){
        this.repo = repo;
        this.encoder = encoder;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.redisTemplate = redisTemplate;
        this.repository = repository;
    }

    /**
     * <p>
     *     This method is used to create a new user to the database.
     *     We use {@link BCryptPasswordEncoder} to BCrypt the password before saving it.
     *     Use the {@link MapperDTO} to map the {@link CreateUserDTO} to {@link UserInfo}
     * </p>
     * @param createUser {@link UserInfo} contains the new user details
     * @return {@link String} message if user registers a new account
     * @throws UserException if user already exist with the same email.*/
    @Override
    public String create(CreateUserDTO createUser) {
        Optional<UserInfo> user = repo.findByEmail(createUser.getEmail());
        if(user.isPresent()){
            throw new UserException("An user account present with " + createUser.getEmail() + ". Please use anther one.");
        }
        UserInfo userInfo = mapper.createToUserInfo(createUser);
        userInfo.setPassword(encoder.encode(createUser.getPassword()));
        repo.save(userInfo);
        return "Account created successfully. Welcome, " + userInfo.getName() + "!";
    }

    /**
     * This method is used to update the existing user details like password and name.
     * @param email {@link String} for finding the user details from database.
     * @param userDTO {@link UpdateUserDTO} contains the updated fields.
     * @return {@link String} message if update is successful.
     * @throws UserException if user is not found with the given email.*/
    @Override
    public String update(String email, UpdateUserDTO userDTO) {
        Optional<UserInfo> user = repo.findByEmail(email);
        if (user.isEmpty()){
            throw new UserException("No user account present with " + email + ". Please create an account.");
        }
        if (userDTO.getPassword() != null){
            user.get().setPassword(encoder.encode(userDTO.getPassword()));
        }
        if(userDTO.getName() != null){
            user.get().setName(userDTO.getName());
        }
        repo.save(user.get());
        return "Account updated successfully.";
    }

    /**
     * This method is used to delete the account by taking email.
     * @param email {@link String} used to find the email and delete it.
     * @return {@link String} message if account deleted
     * @throws UserException if user details not found with email.*/
    @Override
    public String delete(String email) {
        Optional<UserInfo> user = repo.findByEmail(email);
        if (user.isEmpty()){
            throw new UserException("No user account is present with " + email + ". Please Login.");
        }
        List<TaskInfo> tasks = repository.getAllTask(email);
        tasks.forEach(e -> redisTemplate.delete("tasks::" + e.getId()));
        repo.delete(user.get());
        redisTemplate.delete("token:" + email);
        Set<String> keys = redisTemplate.keys("tasks::" + email + "*");
        if (keys != null) {
            redisTemplate.delete(keys);
        }
        return "Account deleted successfully.";
    }

    /**
     * This method is used for login purpose
     * <p>
     *     Checks if the user is present with email, password and it is authenticated.
     * </p>
     * @param email {@link String} used to find the account details from database.
     * @param password {@link String} used to login
     * @return {@link String} token which is used to pass through the security.
     * @throws UserException if the user is not authenticated*/
    public String verifyUser(String email, String password){
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if(!auth.isAuthenticated()){
            throw new UserException("Something went wrong. Please check your email and password.");
        }
        String in_memory = (String) redisTemplate.opsForValue().get("token:" + email);
        if (in_memory != null && !jwtService.isExpired(in_memory)) {
            return in_memory;
        }
        String token = jwtService.generateToken(email);
        redisTemplate.opsForValue().set("token:" + email, token, 30, TimeUnit.MINUTES);
        return token;
    }

    public String username(UserDetails user) {
        Optional<UserInfo> userInfo = repo.findByEmail(user.getUsername());
        if(userInfo.isEmpty()) {
            throw new UserException("No username present");
        }
        return userInfo.get().getName();
    }
}
