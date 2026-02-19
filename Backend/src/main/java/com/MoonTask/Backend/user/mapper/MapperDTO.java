package com.MoonTask.Backend.user.mapper;

import org.springframework.stereotype.Component;
import com.MoonTask.Backend.user.entity.UserInfo;
import com.MoonTask.Backend.user.dto.CreateUserDTO;

/**
 * A mapper class used to convert user entered data to entity*/
@Component
public class MapperDTO {

    /**
     * converts the used entered data to entity
     * @param userDTO {@link CreateUserDTO} contains the details, these are the details user entered when creating user account.
     * @return {@link UserInfo} an entity class.*/
    public UserInfo createToUserInfo(CreateUserDTO userDTO){
        return UserInfo.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }
}
