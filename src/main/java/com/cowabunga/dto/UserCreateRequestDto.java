package com.cowabunga.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDto {
    @Size(max = 30)
    @NotBlank(message = "name must not be blank")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Name must contain only letters and spaces")
    private String name;
    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "surname must contain only letters and spaces")
    private String surname;
    @NotBlank(message = "password must not be blank")
    @Size(max = 100)
    private String password;
    @Size(max = 30, message = "User can not be without mobile")
    private String mobile;
    @NotBlank(message = "Email address must not be blank")
    @Email(message = "Email address must be valid")
    @Size(min = 10, max = 100, message = "Email address must be minimum 10 and maximum 100 digits long")
    private String email;
}
