package ru.job4j.urlshotcuts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlDto {
    private String url;

    private Integer total;
}
