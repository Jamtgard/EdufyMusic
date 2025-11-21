package com.example.EdufyMusic.clients;

import com.example.EdufyMusic.exceptions.BadRequestException;
import com.example.EdufyMusic.exceptions.RestClientException;
import com.example.EdufyMusic.models.DTO.responses.CreatorDTO;
import com.example.EdufyMusic.models.DTO.requests.CreatorCreateRecordRequest;
import com.example.EdufyMusic.models.DTO.responses.MediaIdDTO;
import com.example.EdufyMusic.models.enums.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;


import java.util.Arrays;
import java.util.List;

//ED-275-SJ
@Component
public class CreatorClient {

    private final RestClient restClient;

    public CreatorClient(RestClient.Builder builder, @Value("${creator.service.url}") String creatorServiceUrl) {
        this.restClient = builder
                .baseUrl(creatorServiceUrl)
                .build();
    }

    // ED-235-SJ Reworked to wrap in try{}
    public List<CreatorDTO> getCreatorsByMedia(MediaType mediaType, Long mediaId){

        try{
            ResponseEntity<CreatorDTO[]> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/creator/creators-mediaid") // TODO Dubbelkolla om denna behöver "/creator"
                            .queryParam("mediaType", mediaType)
                            .queryParam("id", mediaId)
                            .build())
                    .retrieve()
                    .toEntity(CreatorDTO[].class);

            CreatorDTO[] creatorDTOs = response.getBody();
            return creatorDTOs != null ? Arrays.asList(creatorDTOs) : List.of();

        } catch (RestClientResponseException e) {
            String errorMessage = e.getResponseBodyAsString();
            throw new BadRequestException("Creator Service Error: " + errorMessage);

        } catch (ResourceAccessException e) {
            throw new RestClientException("Edufy Music", "Edufy Creator");
        }
    }

    // ED-235-SJ
    public boolean createRecordOfSong(Long mediaId, List<Long> creatorIds) {
        try {
            ResponseEntity<Void> response = restClient.post()
                    .uri("/creator/media/record") // TODO Dubbelkolla om denna behöver "/creator"
                    .body(new CreatorCreateRecordRequest(mediaId, MediaType.SONG, creatorIds))
                    .retrieve()
                    .toBodilessEntity();

            return response.getStatusCode().is2xxSuccessful();

        } catch (RestClientResponseException e) {
            String errorMessage = e.getResponseBodyAsString();
            throw new BadRequestException("Creator Service Error: " + errorMessage);

        } catch (ResourceAccessException e) {
            throw new RestClientException("Edufy Music", "Edufy Creator");
        }
    }

    // ED-51-SJ
    public List getMusicByCreator(Long creatorId, MediaType mediaType) {
        try {
            ResponseEntity<List<MediaIdDTO>> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/mediabycreator/{creatorId}/{mediaType}")
                            .build(creatorId, mediaType))
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<List<MediaIdDTO>>() {});

            List<MediaIdDTO> body = response.getBody();
            if (body == null) return List.of();

            return body.stream()
                    .map(MediaIdDTO::getMediaId)
                    .toList();

        } catch (RestClientResponseException e) {
            throw new BadRequestException("Creator Service Error: " + e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            throw new RestClientException("Edufy Music", "Edufy Creator");
        }
    }
}
