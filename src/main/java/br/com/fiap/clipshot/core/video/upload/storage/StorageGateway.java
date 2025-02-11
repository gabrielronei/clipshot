package br.com.fiap.clipshot.core.video.upload.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageGateway {

    String upload(MultipartFile file);
}
