package br.com.fiap.clipshot.core.infra.queue;

import br.com.fiap.clipshot.core.video.Video;
import br.com.fiap.clipshot.core.video.upload.storage.VideoUploadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class VideoQueueProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoQueueProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public VideoQueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(Video video) {
        VideoUploadTask videoUploadTask = new VideoUploadTask(video.getId().toString(), video.getUrl(), video.getUser().getId());
        rabbitTemplate.convertAndSend("clipshot-video-queue", videoUploadTask);
        LOGGER.info("[VideoQueueProducer] Published message with success: id - {}", videoUploadTask.id());
    }
}