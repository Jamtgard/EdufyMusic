package com.example.EdufyMusic.clients;

import com.example.EdufyMusic.exceptions.BadRequestException;
import com.example.EdufyMusic.exceptions.RestClientException;
import com.example.EdufyMusic.models.DTO.requests.ThumbCreateRecordRequest;
import com.example.EdufyMusic.models.enums.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;


// ED-235-SJ
@Component
public class ThumbClient {

    private final RestClient restClient;

    public ThumbClient(RestClient.Builder builder, @Value("${services.thumb.url}") String thumbServiceUrl) {
        this.restClient = builder
                .baseUrl(thumbServiceUrl)
                .build();
    }

    public boolean createRecordOfSong(Long mediaId, String mediaName) {
        try {
            ResponseEntity<Void> response = restClient.post()
                    .uri("/api/v1/thumb/media/record")
                    .body(new ThumbCreateRecordRequest(mediaId,MediaType.SONG, mediaName))
                    .retrieve()
                    .toBodilessEntity();

            return response.getStatusCode().is2xxSuccessful();

        } catch (RestClientResponseException e) {
            String errorMessage = e.getResponseBodyAsString();
            throw new BadRequestException("Thumb Service Error:" + errorMessage);
        } catch (ResourceAccessException e) {
            throw new RestClientException("Edufy Music", "Edufy Thumb");
        }
    }
}
