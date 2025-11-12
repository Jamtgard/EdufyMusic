package com.example.EdufyMusic.clients;

import com.example.EdufyMusic.models.DTO.CreatorDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

//ED-275-SJ
@Component
public class CreatorClient {

    @Value("${creator.service.url}")
    private String creatorServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<CreatorDTO> getCreatorsByMedia(String mediaType, Long mediaId){
        String url = UriComponentsBuilder.fromHttpUrl(creatorServiceUrl)
                .path("/creator/creators-mediaid")
                .queryParam("mediaType", mediaType)
                .queryParam("id", mediaId)
                .toUriString();

        try {
            CreatorDTO[] creators = restTemplate.getForObject(url, CreatorDTO[].class);
            return creators != null ? Arrays.asList(creators) : List.of();
        } catch (ResourceAccessException e) {
            return List.of();
        }
    }
}
