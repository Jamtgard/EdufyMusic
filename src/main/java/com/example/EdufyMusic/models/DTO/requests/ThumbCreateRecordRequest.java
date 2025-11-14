package com.example.EdufyMusic.models.DTO.requests;

// ED-235-SJ
public class ThumbCreateRecordRequest {

    private long mediaId;
    private String mediaType;
    private String mediaName;


    public long getMediaId() {return mediaId;}
    public void setMediaId(long mediaId) {this.mediaId = mediaId;}

    public String getMediaType() {return mediaType;}
    public void setMediaType(String mediaType) {this.mediaType = mediaType;}

    public String getMediaName() {return mediaName;}
    public void setMediaName(String mediaName) {this.mediaName = mediaName;}
}
