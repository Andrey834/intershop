package ru.big.intershop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.big.intershop.dto.product.ProductRequest;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.service.ProductSearchService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
private final ProductSearchService productSearchService;

    public AdminController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping
    public String adminPanel(Model model) {
        List<ProductShortDto> products = productSearchService.getAll();
        model.addAttribute("products", products);
        return "admin-panel";
    }

    @PostMapping("/product")
    public String addProduct(@ModelAttribute ProductRequest product, Model model) {

        return "admin-panel";
    }
}
