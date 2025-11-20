package com.example.EdufyMusic.models.DTO.requests;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

// ED-273-SJ
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongsByGenreDTORequest {

    private String genreName;
    private List<Long> mediaIds;

    public SongsByGenreDTORequest() {}
    public SongsByGenreDTORequest(String genreName, List<Long> mediaIds) {
        this.genreName = genreName;
        this.mediaIds = mediaIds;
    }

    public String getGenreName() {return genreName;}
    public void setGenreName(String genreName) {this.genreName = genreName;}

    public List<Long> getMediaIds() {return mediaIds;}
    public void setMediaIds(List<Long> mediaIds) {this.mediaIds = mediaIds;}

    @Override
    public String toString() {
        return "SongsByGenreDTORequest{" +
                "genreName='" + genreName + '\'' +
                ", mediaIds=" + mediaIds +
                '}';
    }
}
