package com.example.EdufyMusic.models.DTO;

// ED-253-SJ
public class UserDTO {

    private Long id;

    public UserDTO() {}
    public UserDTO(Long id) {this.id = id;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                '}';
    }
}
