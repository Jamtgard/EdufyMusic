package com.example.EdufyMusic.clients;

import com.example.EdufyMusic.exceptions.BadRequestException;
import com.example.EdufyMusic.exceptions.RestClientException;
import com.example.EdufyMusic.models.DTO.UserDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientResponseException;

// ED-253-SJ
@Component
public class UserClient {

    private final RestClient restClient;
    private final Keycloak keycloak;

    public UserClient(RestClient.Builder builder, @Value("${user.service.url}") String userServiceUrl,
                      Keycloak keycloak) {
        this.restClient = builder
                .baseUrl(userServiceUrl)
                .build();
        this.keycloak = keycloak;
    }

    // ED-253-SJ
    public UserDTO getUserById(Long id) {

        try {
            ResponseEntity<UserDTO> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user-id/{id}/clientcall")
                            .build(id))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloak.getAccessToken())
                    .retrieve()
                    .toEntity(UserDTO.class);

            return response.getBody();

        } catch (RestClientResponseException e) {
            String errorMessage = e.getResponseBodyAsString();
            throw new BadRequestException("User Service Error: " + errorMessage);

        } catch (ResourceAccessException e) {
            throw new RestClientException("Edufy Music", "Edufy User");
        }
    }

    // ED-253-SJ
    public UserDTO getUserBySUB(String sub) {

        try {
            ResponseEntity<UserDTO> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/user-sub/{sub}/clientcall")
                            .build(sub))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + keycloak.getAccessToken())
                    .retrieve()
                    .toEntity(UserDTO.class);

            return response.getBody();

        } catch (RestClientResponseException e) {
            String errorMessage = e.getResponseBodyAsString();
            throw new BadRequestException("User Service Error: " + errorMessage);

        } catch (ResourceAccessException e) {
            throw new RestClientException("Edufy Music", "Edufy User");
        }
    }
}
