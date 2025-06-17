package ru.big.intershop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.product.ProductDto;
import ru.big.intershop.dto.product.ProductRequest;
import ru.big.intershop.mapper.ProductMapper;
import ru.big.intershop.model.Product;
import ru.big.intershop.reposioty.ProductRepository;
import ru.big.intershop.service.ImageService;
import ru.big.intershop.service.ProductService;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    public ProductServiceImpl(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public Mono<Long> save(ProductRequest request) {
        return imageService.save(request.image())
                .flatMap(imageName -> {
                            Product product = ProductMapper.toModel(request);
                            product.setImage(imageName);
                            product.setCreated(LocalDateTime.now());
                            return Mono.just(product);
                        }
                )
                .flatMap(productRepository::save)
                .map(Product::getId);
    }

    @Override
    public Mono<Long> update(Long id, ProductRequest request) {
        Mono<Product> product = getById(id);
        Mono<String> imageName = request.image() != null
                ? imageService.save(request.image())
                : Mono.just("empty");

        return imageName
                .zipWith(product, (fileName, prod) -> {
                    if (!fileName.equals("empty")) {
                        imageService.delete(prod.getImage());
                        prod.setImage(fileName);
                    }
                    prod.setTitle(request.title());
                    prod.setDescription(request.description());
                    prod.setPrice(request.price());

                    return prod;
                })
                .flatMap(productRepository::save)
                .map(Product::getId);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return getById(id).flatMap(productRepository::delete);
    }

    @Override
    public Flux<ProductDto> getAll() {
        return productRepository.findAll()
                .map(ProductMapper::toDto)
                .sort(Comparator.comparing(ProductDto::id));
    }

    private Mono<Product> getById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found with id: " + id)));
    }
}
