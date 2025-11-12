package com.example.EdufyMusic.models.DTO;

//ED-275-SJ - Created class to be able to hide id if user has role User. Admins will see id.
public class CreatorViewDTO {

    private Long id;
    private String username;

    public CreatorViewDTO() {}
    public CreatorViewDTO(String username) {this.username = username;}
    public CreatorViewDTO(Long id, String username)
    {
        this.id = id;
        this.username = username;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override
    public String toString() {
        return "CreatorViewDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }

}
