package com.example.EdufyMusic.models.DTO.requests;

import com.example.EdufyMusic.models.enums.MediaType;

import java.util.List;

// ED-235-SJ
// TODO update if necessary due to method not yet created in MS Creator
public class CreatorCreateRecordRequest {

    private Long mediaId;
    private MediaType mediaType;
    private List<Long> creatorIds;

    public CreatorCreateRecordRequest(Long mediaId, MediaType mediaType, List<Long> creatorIds) {
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.creatorIds = creatorIds;
    }

    public Long getMediaId() {return mediaId;}
    public void setMediaId(Long mediaId) {this.mediaId = mediaId;}

    public MediaType getMediaType() {return mediaType;}
    public void setMediaType(MediaType mediaType) {this.mediaType = mediaType;}

    public List<Long> getCreatorIds() {return creatorIds;}
    public void setCreatorIds(List<Long> creatorIds) {this.creatorIds = creatorIds;}
}
