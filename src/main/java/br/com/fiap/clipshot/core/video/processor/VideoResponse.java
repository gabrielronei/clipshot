package br.com.fiap.clipshot.core.video.processor;

public class VideoResponse {
    private final String videoUrl;
    private final String status;

    public VideoResponse(String videoUrl, String status) {
        this.videoUrl = videoUrl;
        this.status = status;
    }

    public String getVideoUrl() {
            return videoUrl;
        }

    public String getStatus() {
            return status;
        }
}