package com.example.EdufyMusic.models.DTO;

// ED-253-SJ
public class PlayedSongDTO {

    private String url;

    public PlayedSongDTO() {}
    public PlayedSongDTO(String url) {this.url = url;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    @Override
    public String toString() {
        return "PlayedSongDTO{" +
                "url='" + url + '\'' +
                '}';
    }
}
