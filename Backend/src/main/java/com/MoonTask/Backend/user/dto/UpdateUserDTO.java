package com.MoonTask.Backend.user.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*Contains the fields, Which fields need to be updated.*/
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UpdateUserDTO {
    private String name;
    @Size(min = 8)
    private String password;
}
