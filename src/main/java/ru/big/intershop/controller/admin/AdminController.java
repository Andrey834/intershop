package ru.big.intershop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.big.intershop.dto.product.ProductRequest;
import ru.big.intershop.dto.product.ProductShortDto;
import ru.big.intershop.dto.product.ProductUpdate;
import ru.big.intershop.service.ProductSearchService;
import ru.big.intershop.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductSearchService productSearchService;
    private final ProductService productService;
    private static final String REDIRECT_PATH = "redirect:/admin";

    public AdminController(ProductSearchService productSearchService,
                           ProductService productService) {
        this.productSearchService = productSearchService;
        this.productService = productService;
    }

    @GetMapping
    public String adminPanel(Model model) {
        List<ProductShortDto> products = productSearchService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("productRequest", ProductRequest.builder().build());
        return "admin-panel";
    }

    @PostMapping("/product")
    public String addProduct(@ModelAttribute ProductRequest product, Model model) {
        productService.create(product);
        return REDIRECT_PATH;
    }

    @GetMapping("/product/{id}/edit")
    public String viewEditProduct(@PathVariable(name = "id") Long postId, Model model) {
        List<ProductShortDto> products = productSearchService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("productRequest", ProductRequest.builder().build());
        return "admin-panel";
    }

    @PostMapping("/product/{id}/edit")
    public String processEditProduct(@PathVariable(name = "id") Long id,
                                @ModelAttribute ProductRequest product,
                                Model model) {
        productService.update(id, product);
        return REDIRECT_PATH;
    }

    @PostMapping("/product/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return REDIRECT_PATH;
    }
}
