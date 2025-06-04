package ru.big.intershop.controller.consumer;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.big.intershop.dto.PageParam;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.enums.Sorting;
import ru.big.intershop.enums.ViewName;
import ru.big.intershop.service.CartService;
import ru.big.intershop.service.ProductSearchService;
import ru.big.intershop.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductConsumerController {
    private final ProductService productService;
    private final ProductSearchService productSearchService;
    private final CartService cartService;

    public ProductConsumerController(ProductService productService,
                                     ProductSearchService productSearchService,
                                     CartService cartService) {
        this.productService = productService;
        this.productSearchService = productSearchService;
        this.cartService = cartService;
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
}
