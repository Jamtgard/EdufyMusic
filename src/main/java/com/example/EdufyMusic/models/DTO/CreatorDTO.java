package com.example.EdufyMusic.models.DTO;

// ED-113-SJ
public class CreatorDTO {

// Attributes ----------------------------------------------------------------------------------------------------------

    private Long id;
    // ED-275-SJ changed attribute name to match response from MS Creator.
    private String username;

// Constructors --------------------------------------------------------------------------------------------------------

    public CreatorDTO() {}

    public CreatorDTO(Long id, String username)
    {
        this.id = id;
        this.username = username;
    }

// Getters & Setters ---------------------------------------------------------------------------------------------------

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

// toString ------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "CreatorDTO{" +
                "id=" + id +
                ", title='" + username + '\'' +
                '}';
    }
}
