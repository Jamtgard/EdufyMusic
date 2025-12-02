package com.example.EdufyMusic.models.DTO.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

// ED-114-SJ
@JsonInclude(JsonInclude.Include.NON_NULL) // ED-275-SJ
public class GenreDTO {

    private Long id;
    //ED-266-SJ - changed (title = name) to match MS Genre response.
    private String name;

    public GenreDTO() {}

    public GenreDTO(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    @Override
    public String toString() {
        return "GenreDTO{" +
                "id=" + id +
                ", title='" + name + '\'' +
                '}';
    }
}
