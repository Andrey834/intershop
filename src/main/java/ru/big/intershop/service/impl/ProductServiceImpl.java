package ru.big.intershop.service.impl;

import org.springframework.stereotype.Service;
import ru.big.intershop.dto.product.ProductDto;
import ru.big.intershop.dto.product.ProductRequest;
import ru.big.intershop.dto.product.ProductUpdate;
import ru.big.intershop.mapper.ProductMapper;
import ru.big.intershop.model.Product;
import ru.big.intershop.repository.ProductRepository;
import ru.big.intershop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto create(ProductRequest productRequest) {
        Product product = productRepository.save(ProductMapper.toProduct(productRequest));
        return ProductMapper.toDto(product);
    }

    @Override
    public void update(Long id, ProductRequest productRequest) {
        Product product = getProduct(id);

        product.setTitle(productRequest.title());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
    }

    @Override
    public ProductDto get(Long id) {
        return ProductMapper.toDto(getProduct(id));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found - " + id));
    }
}
