package br.com.fiap.clipshot.core.video.processor;

import java.util.List;

public class VideoProcessorResponse {

    private final List<VideoResponse> videos;

    public VideoProcessorResponse(List<VideoResponse> videos) {
        this.videos = videos;
    }

    public List<VideoResponse> getVideos() {
        return videos;
    }


}
