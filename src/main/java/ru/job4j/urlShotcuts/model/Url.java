package ru.job4j.urlShotcuts.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "urls")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NotBlank(message = "URL must be not empty")
    @Pattern(regexp = "^[^.]+\\.([a-zA-Z]{1,4})/(.*)$",
            message = "Entered link is not url.")
    private String url;

    private String code;

    private int total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
