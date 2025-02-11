package br.com.fiap.clipshot.core.infraestructure.queue;

import br.com.fiap.clipshot.core.infraestructure.mail.Mailer;
import br.com.fiap.clipshot.core.infraestructure.utils.JsonUtils;
import br.com.fiap.clipshot.core.video.Video;
import br.com.fiap.clipshot.core.video.VideoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class FailedVideoConsumer implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailedVideoConsumer.class);

    private final VideoRepository videoRepository;
    private final Mailer mailer;

    public FailedVideoConsumer(VideoRepository videoRepository,
                               Mailer mailer) {
        this.videoRepository = videoRepository;
        this.mailer = mailer;
    }

    @Override
    @Transactional
    public void onMessage(Message message) {
        VideoFailedCommand body = JsonUtils.fromJson(message.getBody(), VideoFailedCommand.class);
        LOGGER.info("[VideoConsumer] Consuming message: {}", body);

        Video video = videoRepository.findByIdAndUserId(body.id(), body.userId()).orElseThrow(() -> new RuntimeException("Video not found!"));
        video.markAsError();

        this.mailer.send(video.getUser().getEmail(), "Your video processing has failed", "Your video with name %s and id %s has failed to process, please try to send it again!".formatted(video.getTitle(),body.id()));
    }
}