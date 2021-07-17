package com.devadath.factaday.fact;

/*
 * @author Devadath Prabhu
 * @created Jul 17, 2021
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Set;

@Component
public class FactFetcher {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final String uriTemplate = "https://numbersapi.p.rapidapi.com/${month}/${day}/date?json=true&fragment=true";

    public String getFactOfTheDay() throws JsonProcessingException {

        HttpEntity headers = getHeaders();

        LocalDate date = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        String uri = uriTemplate.replace("${month}", String.valueOf(date.getMonthValue()))
                .replace("${day}", String.valueOf(date.getDayOfMonth()));

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, headers, String.class);

        StringBuilder factOfTheDay = new StringBuilder();

        if (response.getStatusCode() == HttpStatus.OK) {
            LinkedHashMap factObj = objectMapper.readValue(response.getBody(), LinkedHashMap.class);
            factOfTheDay.append("Today's fact (").append(date.getMonth()).append(" ").append(date.getDayOfMonth()).append(") :\n");
            factOfTheDay.append("On this day, in ").append(factObj.get("year")).append(", ").append(factObj.get("text"));
        }

        return factOfTheDay.toString();
    }

    private HttpEntity getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", "fc7711e53amsha34e4572c2d1e16p1e33fajsn24b1c9a44664");
        headers.set("x-rapidapi-host", "numbersapi.p.rapidapi.com");

        HttpEntity request = new HttpEntity(headers);
        return request;
    }
}
