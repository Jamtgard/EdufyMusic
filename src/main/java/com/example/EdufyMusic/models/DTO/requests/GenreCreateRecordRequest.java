package com.example.EdufyMusic.models.DTO.requests;

import com.example.EdufyMusic.models.enums.MediaType;

import java.util.List;

// ED-235-SJ
public class GenreCreateRecordRequest {

    private Long mediaId;
    private MediaType mediaType;
    private List<Long> genreIds;

    public GenreCreateRecordRequest(Long mediaId, MediaType mediaType, List<Long> genreIds) {
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.genreIds = genreIds;
    }

    public Long getMediaId() {return mediaId;}
    public void setMediaId(Long mediaId) {this.mediaId = mediaId;}

    public MediaType getMediaType() {return mediaType;}
    public void setMediaType(MediaType mediaType) {this.mediaType = mediaType;}

    public List<Long> getGenreIds() {return genreIds;}
    public void setGenreIds(List<Long> genreIds) {this.genreIds = genreIds;}
}
