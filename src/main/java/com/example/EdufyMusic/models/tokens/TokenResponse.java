package com.example.EdufyMusic.models.tokens;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") long expiresIn) {
}
