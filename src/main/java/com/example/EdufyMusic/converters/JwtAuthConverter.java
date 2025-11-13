package com.example.EdufyMusic.converters;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ED-165-SJ
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    // ED-265-SJ
    @Value("${music.client.id}")
    private String musicClientId;

    @Override
    public AbstractAuthenticationToken convert(@NotNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                Stream.concat(
                        jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                        extractRoles(jwt).stream()
                ),
                    extractRealmRoles(jwt).stream()
                ).collect(Collectors.toSet()
        );
        return new JwtAuthenticationToken(jwt, authorities);
    }

    // ED-80-SJ - Wanted to rework auth-flow, renamed for clarity.
    private Collection<? extends GrantedAuthority> extractRoles(Jwt jwt) {

        Collection<String> resourceRoles;
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;

        if (!jwt.hasClaim("resource_access")) {return Set.of();}
        resourceAccess = jwt.getClaimAsMap("resource_access");

        if (!resourceAccess.containsKey(musicClientId)) {return Set.of();}
        resource = (Map<String, Object>) resourceAccess.get(musicClientId);

        if (!resource.containsKey("roles")) {return Set.of();}
        resourceRoles = (Collection<String>) resource.get("roles");

        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect((Collectors.toSet()));
    }

    // ED-80-SJ - Wanted to rework auth-flow, implemented new selection for better management of extracting realm roles.
    private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt){

        Map<String, Object> realmAccess;
        Collection<String> realmRoles;

        if(!jwt.hasClaim("realm_access")) {return Set.of();}
        realmAccess = jwt.getClaimAsMap("realm_access");

        if(!realmAccess.containsKey("roles")) {return Set.of();}

        realmRoles = (Collection<String>) realmAccess.get("roles");

        return realmRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toSet());
    }
}
