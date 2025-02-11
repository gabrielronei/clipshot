package br.com.fiap.clipshot.core.video.upload;

import br.com.fiap.clipshot.core.infraestructure.queue.VideoQueueProducer;
import br.com.fiap.clipshot.core.video.Video;
import br.com.fiap.clipshot.core.video.VideoRepository;
import br.com.fiap.clipshot.core.video.upload.storage.StorageGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoUploadService.class);

    private final StorageGateway storageGateway;
    private final VideoRepository videoRepository;
    private final VideoQueueProducer videoQueueProducer;

    public VideoUploadService(StorageGateway storageGateway,
                              VideoRepository videoRepository,
                              VideoQueueProducer videoQueueProducer) {
        this.storageGateway = storageGateway;
        this.videoRepository = videoRepository;
        this.videoQueueProducer = videoQueueProducer;
    }

    @Async
    public void upload(List<Video> videos) {
        LOGGER.info("[S3StorageGateway] Uploading video(s) to S3: {}", videos.size());

        List<Video> updatedVideos = videos.stream().filter(video -> video.getFile().isPresent())
                .map(video -> {
                    String uploadUrl = storageGateway.upload(video.getFile().get());
                    return video.update(uploadUrl);
                }).toList();

        List<Video> updated = videoRepository.saveAll(updatedVideos);

        updated.forEach(videoQueueProducer::publish);
    }
}
