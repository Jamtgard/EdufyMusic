package com.example.EdufyMusic.models.DTO.responses;

// ED-51-SJ
public class MediaIdDTO {

    private Long mediaId;

    public MediaIdDTO() {}

    public MediaIdDTO(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Long getMediaId() { return mediaId; }
    public void setMediaId(Long mediaId) { this.mediaId = mediaId; }
}
