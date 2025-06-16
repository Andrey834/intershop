package ru.big.intershop.service;

import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface ImageService {

    Mono<String> save(FilePart file);

    void delete(String imageName);

    Mono<Resource> get(String imageName);
}
