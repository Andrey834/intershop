package ru.big.intershop.controller.consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.big.intershop.dto.PageParam;
import ru.big.intershop.dto.cart.CartShort;
import ru.big.intershop.dto.cart.ItemCartShort;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.enums.ViewName;
import ru.big.intershop.service.CartService;
import ru.big.intershop.service.ProductSearchService;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductSearchService productSearchService;
    private final CartService cartService;

    public ProductController(ProductSearchService productSearchService, CartService cartService) {
        this.productSearchService = productSearchService;
        this.cartService = cartService;
    }

    @GetMapping
    public Mono<Rendering> viewProducts(@ModelAttribute PageParam pageParam) {
        Flux<ProductShortDto> products = productSearchService.setMaxPage(pageParam)
                .flatMapMany(productSearchService::getAll);

        Mono<CartShort> cart = cartService.getCartShort();

        Rendering rendering = Rendering.view(ViewName.PRODUCTS.getValue())
                .modelAttribute("products", products)
                .modelAttribute("pageParam", pageParam)
                .modelAttribute("cart", cart)
                .build();

        return Mono.just(rendering);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<Rendering> viewProduct(@PathVariable Long id) {
        Mono<ProductShortDto> product = productSearchService.getById(id);
        Mono<ItemCartShort> itemCart = cartService.getItemCart(id);

        Rendering rendering = Rendering.view(ViewName.PRODUCT.getValue())
                .modelAttribute("product", product)
                .modelAttribute("itemCart", itemCart)
                .build();
        ResponseEntity.status(302).build();
        return Mono.just(rendering);
    }
}
