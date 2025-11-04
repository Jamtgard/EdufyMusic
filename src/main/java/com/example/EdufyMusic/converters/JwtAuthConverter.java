package com.example.EdufyMusic.converters;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
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

    @Value("${jwt.auth.converter.resource-id.name}")
    private String resourceIdName;
    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;


    @Override
    public AbstractAuthenticationToken convert(@NotNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream())
                .collect(Collectors.toSet());

        return new JwtAuthenticationToken(jwt, authorities, getPrincipalClaimName(jwt));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {

        Collection<String> resourceRoles;
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;

        if (!jwt.hasClaim("resource_access")) {return Set.of();}
        resourceAccess = jwt.getClaimAsMap("resource_access");

        if (!resourceAccess.containsKey(resourceIdName)) {return Set.of();}
        resource = (Map<String, Object>) resourceAccess.get(resourceIdName);

        if (!resource.containsKey("roles")) {return Set.of();}
        resourceRoles = (Collection<String>) resource.get("roles");

        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect((Collectors.toSet()));
    }

    private String getPrincipalClaimName(Jwt jwt) {

        String claimName = JwtClaimNames.SUB;
        if (principleAttribute != null && !principleAttribute.isEmpty()) {claimName = principleAttribute;}

        return jwt.getClaim(claimName);
    }
}
