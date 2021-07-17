package com.devadath.factaday.fact;

/*
 * @author Devadath Prabhu
 * @created Jul 17, 2021
 */

import com.devadath.factaday.config.RestTemplateConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestTemplateConfig.class})
public class FactFetcherTest {

    @Spy
    @InjectMocks
    private FactFetcher factFetcher;

    @Mock
    RestTemplate restTemplate;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    ResponseEntity responseEntity;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFactOfTheDay_positive() throws Exception {
        LocalDate date = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        StringBuilder factPrefix = new StringBuilder();
        factPrefix.append(date.getMonth()).append(" ").append(date.getDayOfMonth()).append(" :\n");
        String responseBody = "{\n" +
                " \"text\": \"the Fourth Crusade captures Constantinople by assault\",\n" +
                " \"year\": 1203,\n" +
                " \"number\": 189,\n" +
                " \"found\": true,\n" +
                " \"type\": \"date\"\n" +
                "}";
        LinkedHashMap<String, Object> factObj = new LinkedHashMap<>();
        factObj.put("text", "the Fourth Crusade captures Constantinople by assault");
        factObj.put("year", 1203);

        doReturn(responseEntity).when(restTemplate).exchange(anyString(), any(), any(), any(Class.class));
        doReturn(HttpStatus.OK).when(responseEntity).getStatusCode();
        doReturn(responseBody).when(responseEntity).getBody();
        doReturn(factObj).when(objectMapper).readValue(responseBody, LinkedHashMap.class);

        String fact = factFetcher.getFactOfTheDay();

        assertTrue(fact.contains((String)factObj.get("text")));
        assertTrue(fact.contains(String.valueOf(factObj.get("year"))));
    }

    @Test
    public void testGetFactOfTheDay_negative() throws Exception {

        doReturn(responseEntity).when(restTemplate).exchange(anyString(), any(), any(), any(Class.class));
        doReturn(HttpStatus.BAD_REQUEST).when(responseEntity).getStatusCode();

        String fact = factFetcher.getFactOfTheDay();

        assertTrue(StringUtils.isBlank(fact));

    }

}
