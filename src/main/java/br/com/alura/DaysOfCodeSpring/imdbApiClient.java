package br.com.alura.DaysOfCodeSpring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class imdbApiClient {

    @Autowired
    public RestTemplate restTemplate;

    public List<ApiController.Filme> getBody(String apiKey) throws JsonProcessingException {
        String url = "https://imdb-api.com/en/API/Top250Movies/";
        ResponseEntity<String> response = restTemplate.getForEntity(url + apiKey, String.class);
        String parsed = response.getBody().substring(9,response.getBody().length() - 19);

        ObjectMapper objectMapper = new ObjectMapper();
        return Arrays.stream(objectMapper.readValue(parsed, ApiController.Filme[].class)).toList();
    }
}
