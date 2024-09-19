package ru.job4j.urlshotcuts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "Site must be not empty")
    @Pattern(regexp = "^[^.]+\\.([a-zA-Z]{1,4})$",
            message = "Entered link is not url.")
    private String site;

    private String login;

    private String password;
}
