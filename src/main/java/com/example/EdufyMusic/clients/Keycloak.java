package com.example.EdufyMusic.clients;

import com.example.EdufyMusic.models.tokens.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

// ED-309-SJ
@Component
public class Keycloak {

    private final RestClient restClient;
    private final String keycloakUrl;
    private final String clientSecret;
    private final String clientId;

    public Keycloak(RestClient.Builder restClientBuilder,
                        @Value("${keycloak.url}") String keycloakUrl,
                        @Value("${music.client.id}") String clientId,
                        @Value("${keycloak.client-secret}") String clientSecret) {
        this.restClient = restClientBuilder.build();
        this.keycloakUrl = keycloakUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getAccessToken() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);

        TokenResponse tokenResponse = restClient
                .post()
                .uri(keycloakUrl + "/realms/edufy_realm/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(TokenResponse.class);

        assert tokenResponse != null;
        return tokenResponse.accessToken();
    }
}
