package ru.big.intershop.service.impl;

import org.springframework.stereotype.Service;
import ru.big.intershop.dto.product.ProductDto;
import ru.big.intershop.dto.product.ProductRequest;
import ru.big.intershop.dto.product.ProductUpdate;
import ru.big.intershop.mapper.ProductMapper;
import ru.big.intershop.model.Product;
import ru.big.intershop.repository.ProductRepository;
import ru.big.intershop.service.ImageService;
import ru.big.intershop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;

    public ProductServiceImpl(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    @Override
    public ProductDto create(ProductRequest productRequest) {
        String imageName = imageService.save(productRequest.image());
        Product product = ProductMapper.toProduct(productRequest);
        product.setImage(imageName);
        Product newProduct = productRepository.save(product);

        return ProductMapper.toDto(newProduct);
    }

    @Override
    public void update(Long id, ProductRequest productRequest) {
        Product product = getProduct(id);

        if (!productRequest.image().isEmpty()) {
            imageService.delete(product.getImage());
            String imageName = imageService.save(productRequest.image());
            product.setImage(imageName);
        }

        product.setTitle(productRequest.title());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());

        productRepository.save(product);
    }

    @Override
    public ProductDto get(Long id) {
        return ProductMapper.toDto(getProduct(id));
    }

    @Override
    public void delete(Long id) {
        Product product = getProduct(id);
        imageService.delete(product.getImage());
        productRepository.delete(product);
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found - " + id));
    }
}
