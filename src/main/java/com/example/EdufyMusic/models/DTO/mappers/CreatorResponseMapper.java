package com.example.EdufyMusic.models.DTO.mappers;

import com.example.EdufyMusic.models.DTO.CreatorViewDTO;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplierBuilder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//ED-275-SJ
@Component
public class CreatorResponseMapper {

    public List<CreatorViewDTO> toDtoListWithId(List<CreatorViewDTO> creators) {

        if (creators == null || creators.isEmpty()) {return Collections.emptyList()}
        return creators.stream()
                .map(c -> new CreatorViewDTO(c.getId(), c.getUsername()))
                .collect(Collectors.toList());
    }

    public List<CreatorViewDTO> toDtoListNoId(List<CreatorViewDTO> creators) {
        if (creators == null) return List.of();
        return creators.stream()
                .map(c -> new CreatorViewDTO(c.getUsername()))
                .collect(Collectors.toList());
    }
}
