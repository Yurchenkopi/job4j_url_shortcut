package ru.job4j.urlshotcuts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.urlshotcuts.UrlShortcutApplication;
import ru.job4j.urlshotcuts.dto.UrlDto;
import ru.job4j.urlshotcuts.model.Url;
import ru.job4j.urlshotcuts.service.UrlService;

import java.util.*;

import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = UrlShortcutApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService simpleUrlService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void whenConvertCodeThenGetUrl() throws Exception {
        String expectedCode = "AbCdEfGh";
        Map<String, String> expectedResponse = Map.of("code", expectedCode);
        String testUrl = "https://abc.com/123";
        Url url = new Url();
        url.setUrl(testUrl);

        when(simpleUrlService.convert(any(Url.class))).thenReturn(Optional.of(expectedResponse));

        this.mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(url)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(expectedCode));
    }

    @Test
    public void whenConvertCodeThenBadRequest() throws Exception {
        Url url = new Url();

        when(simpleUrlService.convert(any(Url.class))).thenReturn(Optional.empty());

        this.mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(url)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGetUrlByCodeThenFoundUrl() throws Exception {
        String testCode = "AbCdEfGh";
        String url = "https://abc.com/123";
        int total = 1;
        Url expectedUrl = new Url();
        expectedUrl.setUrl(url);
        expectedUrl.setTotal(total);

        when(simpleUrlService.incrementTotalAndGet(testCode)).thenReturn(Optional.of(expectedUrl));

        this.mockMvc.perform(get("/redirect/{code}", testCode))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", expectedUrl.getUrl()));
    }

    @Test
    public void whenGetUrlByInvalidCodeThenNotFound() throws Exception {
        String testCode = "AbCdEfGh";

        when(simpleUrlService.incrementTotalAndGet(anyString())).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/redirect/{code}", testCode))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenRequestStatThenResponseWithUrlStat() throws Exception {
        var urlDto1 = new UrlDto("ya.ru/1.html", 2);
        var urlDto2 = new UrlDto("ya.ru/2.html", 4);
        var urlDto3 = new UrlDto("ya.ru/3.html", 7);
        var expectedRsl = List.of(urlDto1, urlDto2, urlDto3);

        when(simpleUrlService.findAllByUser()).thenReturn(expectedRsl);

        this.mockMvc.perform(get("/statistic"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedRsl)));
    }

    @Test
    public void whenRequestStatThenNotFound() throws Exception {
        List<UrlDto> expectedRsl = Collections.emptyList();

        when(simpleUrlService.findAllByUser()).thenReturn(expectedRsl);

        this.mockMvc.perform(get("/statistic"))
                .andExpect(status().isNotFound());
    }

}