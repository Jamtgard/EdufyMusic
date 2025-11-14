package com.example.EdufyMusic.clients;

import com.example.EdufyMusic.models.DTO.CreatorDTO;
import com.example.EdufyMusic.models.DTO.requests.CreatorCreateRecordRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.UriComponentsContributor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

//ED-275-SJ
@Component
public class CreatorClient {

    private final UriComponentsContributor uriComponentsContributor;
    @Value("${creator.service.url}")
    private String creatorServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public CreatorClient(UriComponentsContributor uriComponentsContributor) {
        this.uriComponentsContributor = uriComponentsContributor;
    }

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

    // ED-235-SJ
    //TODO update if necessary due to method not yet created in MS Creator
    public void createRecordOfSong(Long mediaId, List<Long> creatorIds){
        String url = UriComponentsBuilder.fromHttpUrl(creatorServiceUrl)
                .path("/api/v1/creator/media/record")
                .toUriString();

        CreatorCreateRecordRequest body = new CreatorCreateRecordRequest();
        body.setMediaId(mediaId);
        body.setMediaType("SONG");
        body.setCreatorIds(creatorIds);

        restTemplate.postForEntity(url, body, Void.class);
    }
}
