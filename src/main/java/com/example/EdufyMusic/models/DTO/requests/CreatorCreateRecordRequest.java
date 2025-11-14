package com.example.EdufyMusic.models.DTO.requests;

import java.util.List;

// ED-235-SJ
// TODO update if necessary due to method not yet created in MS Creator
public class CreatorCreateRecordRequest {

    private Long mediaId;
    private String mediaType;
    private List<Long> creatorIds;

    public Long getMediaId() {return mediaId;}
    public void setMediaId(Long mediaId) {this.mediaId = mediaId;}

    public String getMediaType() {return mediaType;}
    public void setMediaType(String mediaType) {this.mediaType = mediaType;}

    public List<Long> getCreatorIds() {return creatorIds;}
    public void setCreatorIds(List<Long> creatorIds) {this.creatorIds = creatorIds;}
}
