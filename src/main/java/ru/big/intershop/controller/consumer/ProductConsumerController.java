package ru.big.intershop.controller.consumer;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.service.ProductSearchService;
import ru.big.intershop.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductConsumerController {
    private final ProductService productService;
    private final ProductSearchService productSearchService;

    public ProductConsumerController(ProductService productService, ProductSearchService productSearchService) {
        this.productService = productService;
        this.productSearchService = productSearchService;
    }

   @GetMapping
    public String products(Model model, HttpServletRequest request) {
        List<ProductShortDto> products = productSearchService.getAll();
        model.addAttribute("products", products);

        return "main";
   }
}
