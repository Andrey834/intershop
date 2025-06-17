package ru.big.intershop.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.big.intershop.service.ImageService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Path IMAGES_PATH = Paths.get("images");

    @Override
    public Mono<String> save(FilePart file) {
        String fileName = UUID.randomUUID() + ".jpeg";
        Path savedPath = Paths.get(IMAGES_PATH.toUri().getPath(),fileName);
        return file.transferTo(savedPath)
                .thenReturn(fileName);
    }

    @Override
    public void delete(String imageName) {
        Path file = Paths.get(IMAGES_PATH + "/" + imageName);
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not delete image: " + imageName);
        }

    }

    @Override
    public Mono<Resource> get(String imageName) {
        try {
            return Mono.just(new UrlResource(IMAGES_PATH.resolve(imageName).toUri()));
        } catch (MalformedURLException e) {
            return Mono.error(new IllegalArgumentException("Image not found : " + imageName));
        }
    }
}
