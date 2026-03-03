package com.MoonTask.Backend.user.mapper;

import com.MoonTask.Backend.user.dto.CreateUserDTO;
import com.MoonTask.Backend.user.entity.UserInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MapperDTOTest {


    MapperDTO dto = new MapperDTO();

    @Test
    void testCreateToUserInto(){
        CreateUserDTO userDTO = CreateUserDTO.builder()
                .name("praveen")
                .email("p@gmail.com")
                .password("00000000").build();
        UserInfo user = dto.createToUserInfo(userDTO);
        assertNotNull(user);
        assertEquals("praveen", user.getName());
    }
}
