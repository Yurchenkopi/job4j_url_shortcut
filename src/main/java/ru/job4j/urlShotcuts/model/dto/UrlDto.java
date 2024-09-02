package ru.job4j.urlShotcuts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlDto {
    private String url;

    private Integer total;
}
