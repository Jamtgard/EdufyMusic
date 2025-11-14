package com.example.EdufyMusic.clients;

import com.example.EdufyMusic.models.DTO.requests.ThumbCreateRecordRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

// ED-235-SJ
@Component
public class ThumbClient {

    @Value("${services.thumb.url}")
    private String thumbServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void createRecordOfSong(Long mediaId, String mediaName){

        String url = UriComponentsBuilder.fromHttpUrl(thumbServiceUrl)
                .path("/api/v1/thumb/media/record")
                .toUriString();

        ThumbCreateRecordRequest body = new ThumbCreateRecordRequest();
        body.setMediaId(mediaId);
        body.setMediaType("SONG");
        body.setMediaName(mediaName);

        restTemplate.postForEntity(url, body, Void.class);
    }
}
