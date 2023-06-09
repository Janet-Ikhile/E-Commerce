package com.nextgen.product.controller;

import com.nextgen.product.dtos.ProductDto;
import com.nextgen.product.dtos.ProductResponse;
import com.nextgen.product.entity.Product;
import com.nextgen.product.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("get/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("get/{colorName}")
    public List<ProductDto> getProductsByColor(@PathVariable("colorName") String colorName) {
        return productService.getProductsByColor(colorName);
    }

    @PostMapping("add")
    public ProductResponse addProduct(@Valid @RequestBody ProductDto productDto){
        return productService.addProduct(productDto);

    }
}
