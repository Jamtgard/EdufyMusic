package com.example.EdufyMusic.models.DTO;

// ED-114-SJ
public class GenreDTO {

// Attributes ----------------------------------------------------------------------------------------------------------

    private Long id;
    //ED-266-SJ - changed (title = name) to match MS Genre response.
    private String name;

// Constructors --------------------------------------------------------------------------------------------------------

    public GenreDTO() {}

    public GenreDTO(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

// Getters & Setters ---------------------------------------------------------------------------------------------------

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

// toString ------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "GenreDTO{" +
                "id=" + id +
                ", title='" + name + '\'' +
                '}';
    }
}
