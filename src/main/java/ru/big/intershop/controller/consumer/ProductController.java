package ru.big.intershop.controller.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.big.intershop.dto.PageParam;
import ru.big.intershop.dto.product.ProductDto;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.enums.ViewName;
import ru.big.intershop.service.CartService;
import ru.big.intershop.service.ProductSearchService;
import ru.big.intershop.service.ProductService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductSearchService productSearchService;
    private final CartService cartService;
    private final ProductService productService;

    public ProductController(ProductSearchService productSearchService,
                             CartService cartService, ProductService productService) {
        this.productSearchService = productSearchService;
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String viewProducts(@ModelAttribute PageParam pageParam, Model model) {
        int maxPages = productSearchService.countPages(pageParam);
        pageParam.setMaxPages(maxPages);

        List<ProductShortDto> products = productSearchService.getAll(pageParam);
        Map<Long, Integer> cart = cartService.getAll();

        model.addAttribute("products", products);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("cart", cart);
        return ViewName.PRODUCTS.getValue();
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.get(id);
        model.addAttribute("product", productDto);
        model.addAttribute("itemCart", cartService.get(id));
        return ViewName.PRODUCT.getValue();
    }

}
