package com.example.EdufyMusic.models.DTO.requests;

import java.util.List;

// ED-235-SJ
public class GenreCreateRecordRequest {

    private Long mediaId;
    private String mediaType;
    private List<Long> genreIds;

    public Long getMediaId() {return mediaId;}
    public void setMediaId(Long mediaId) {this.mediaId = mediaId;}

    public String getMediaType() {return mediaType;}
    public void setMediaType(String mediaType) {this.mediaType = mediaType;}

    public List<Long> getGenreIds() {return genreIds;}
    public void setGenreIds(List<Long> genreIds) {this.genreIds = genreIds;}
}
