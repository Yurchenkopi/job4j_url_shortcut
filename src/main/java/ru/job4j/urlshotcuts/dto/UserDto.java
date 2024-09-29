package ru.job4j.urlshotcuts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private boolean registration;

    private String login;

    private String password;
}
