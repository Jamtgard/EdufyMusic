package com.example.EdufyMusic.models.DTO.requests;

import com.example.EdufyMusic.models.enums.MediaType;

// ED-235-SJ
public class ThumbCreateRecordRequest {

    private long mediaId;
    private MediaType mediaType;
    private String mediaName;

    public ThumbCreateRecordRequest(Long mediaId, MediaType mediaType, String mediaName)
    {
        this.mediaId = mediaId;
        this.mediaType = mediaType;
        this.mediaName = mediaName;
    }

    public long getMediaId() {return mediaId;}
    public void setMediaId(long mediaId) {this.mediaId = mediaId;}

    public MediaType getMediaType() {return mediaType;}
    public void setMediaType(MediaType mediaType) {this.mediaType = mediaType;}

    public String getMediaName() {return mediaName;}
    public void setMediaName(String mediaName) {this.mediaName = mediaName;}
}
