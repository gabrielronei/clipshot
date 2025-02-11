package br.com.fiap.clipshot.core.video.processor;

import java.util.List;

public class VideoProcessorResponse {

    private final List<VideoResponse> videoResponse;

    public VideoProcessorResponse(List<VideoResponse> videoResponse) {
        this.videoResponse = videoResponse;
    }

    public List<VideoResponse> getVideoResponse() {
        return videoResponse;
    }


}
