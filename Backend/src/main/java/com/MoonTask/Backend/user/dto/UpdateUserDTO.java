package com.MoonTask.Backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Contains the fields, Which fields need to be updated.*/
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateUserDTO {
    private String name;
    private String password;
}
