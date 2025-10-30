package com.example.EdufyMusic.models.DTO;

// ED-113-SJ
public class CreatorDTO {

// Attributes ----------------------------------------------------------------------------------------------------------

    private Long id;
    private String title;

// Constructors --------------------------------------------------------------------------------------------------------

    public CreatorDTO() {}

    public CreatorDTO(Long id, String title)
    {
        this.id = id;
        this.title = title;
    }

// Getters & Setters ---------------------------------------------------------------------------------------------------

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

// toString ------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "CreatorDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
