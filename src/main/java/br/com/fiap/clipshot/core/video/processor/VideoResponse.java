package br.com.fiap.clipshot.core.video.processor;

public class VideoResponse {
    private final String videoUrl;
    private final String status;
    private final String framesUrl;

    public VideoResponse(String videoUrl, String status, String framesUrl) {
        this.videoUrl = videoUrl;
        this.status = status;
        this.framesUrl = framesUrl;
    }

    public String getFramesUrl() {
        return framesUrl;
    }

    public String getVideoUrl() {
            return videoUrl;
        }

    public String getStatus() {
            return status;
        }
}