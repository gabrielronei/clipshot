package br.com.fiap.clipshot.core.video.upload.storage;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("test")
public class FakeStorageGateway implements StorageGateway {
    @Override
    public String upload(MultipartFile file) {
        return file.getOriginalFilename();
    }
}
