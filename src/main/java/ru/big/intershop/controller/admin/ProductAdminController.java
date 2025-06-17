package ru.big.intershop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.product.ProductDto;
import ru.big.intershop.dto.product.ProductRequest;
import ru.big.intershop.enums.ViewName;
import ru.big.intershop.service.ProductService;

@Controller
@RequestMapping("/admin")
public class ProductAdminController {
    private final ProductService productService;
    private static final String ADMIN_REDIRECT_PATH = "/admin";

    public ProductAdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Mono<Rendering> viewAdminPanel() {
        Flux<ProductDto> products = productService.getAll();

        Rendering rendering = Rendering.view(ViewName.ADMIN.getValue())
                .modelAttribute("products", products)
                .modelAttribute("productRequest", ProductRequest.builder().build())
                .build();

        return Mono.just(rendering);
    }

    @PostMapping("/product")
    public Mono<Rendering> addProduct(@ModelAttribute ProductRequest product) {
        Rendering rendering = Rendering
                .redirectTo(ADMIN_REDIRECT_PATH).build();

        return productService.save(product)
                .then(Mono.just(rendering));
    }

    @PostMapping("/product/{id}/edit")
    public Mono<Rendering> processEditProduct(@PathVariable(name = "id") Long id,
                                              @ModelAttribute ProductRequest product) {
        Rendering rendering = Rendering
                .redirectTo(ADMIN_REDIRECT_PATH).build();

        return productService.update(id, product)
                .then(Mono.just(rendering));
    }

    @PostMapping("/product/{id}/delete")
    public  Mono<Rendering> deleteProduct(@PathVariable Long id) {
        Rendering rendering = Rendering
                .redirectTo(ADMIN_REDIRECT_PATH).build();

        return productService.delete(id)
                .then(Mono.just(rendering));
    }
}
