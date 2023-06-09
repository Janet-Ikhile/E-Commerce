package com.nextgen.product.service;

import com.nextgen.product.dtos.ColorDto;
import com.nextgen.product.dtos.ProductDto;
import com.nextgen.product.dtos.ProductResponse;
import com.nextgen.product.dtos.SizeDto;
import com.nextgen.product.entity.Brand;
import com.nextgen.product.entity.Color;
import com.nextgen.product.entity.Product;
import com.nextgen.product.entity.Size;
import com.nextgen.product.enums.ResponseCodeEnum;
import com.nextgen.product.repository.BrandRepository;
import com.nextgen.product.repository.ColorRepository;
import com.nextgen.product.repository.ProductRepository;
import com.nextgen.product.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final BrandRepository brandRepository;


    public ProductResponse addProduct(ProductDto productDto) {
        Set<Color> colors = getColorEntities(productDto);
        Set<Size> sizes = getSizeEntities(productDto);
        Brand brand = getBrandEntity(productDto);

        Product product = new Product();
        product.setImage(productDto.getImage());
        product.setDescription(productDto.getDescription());
        product.setColors(colors);
        product.setGenderCategory(productDto.getGenderCategory());
        product.setSizes(sizes);
        product.setPrice(productDto.getPrice());
        product.setShoeBrand(brand);
        productRepository.save(product);
        return new ProductResponse(ResponseCodeEnum.SUCCESS.getCode(), ResponseCodeEnum.SUCCESS.getDescription());
    }

    private Brand getBrandEntity(ProductDto productDto) {
        return brandRepository.findByName(productDto.getBrand().getName())
                .orElseGet(() -> {
                    Brand newBrand = new Brand();
                    newBrand.setName(productDto.getBrand().getName());
                    return brandRepository.save(newBrand);
                });
    }

    private Set<Size> getSizeEntities(ProductDto productDto) {
        return productDto.getSizes().stream()
                .map(sizeDto -> {
                    Optional<Size> sizeOptional = sizeRepository.findBySizeName(sizeDto.getSizeName());
                    if (sizeOptional.isPresent()) {
                        return sizeOptional.get();
                    }
                    Size size = new Size();
                    size.setSizeName(sizeDto.getSizeName());
                    sizeRepository.save(size);
                    return size;
                })
                .collect(Collectors.toSet());
    }

    private Set<Color> getColorEntities(ProductDto productDto) {
        return productDto.getColors().stream()
                .map(colorDto -> {
                    Optional<Color> colorOptional = colorRepository.findByColorName(colorDto.getColorName().toUpperCase());
                    if (colorOptional.isPresent()) {
                        return colorOptional.get();
                    }
                    Color color = new Color();
                    color.setColorName(colorDto.getColorName().toUpperCase());
                    colorRepository.save(color);
                    return color;
                })
                .collect(Collectors.toSet());
    }

    public ProductDto getProductRequest(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setImage(product.getImage());
        productDto.setDescription(product.getDescription());
        Set<ColorDto> colorDtos = product.getColors().stream()
                .map(color -> {
                    ColorDto colorDto = new ColorDto();
                    colorDto.setColorName(color.getColorName());
                    return colorDto;
                }).collect(Collectors.toSet());

        Set<SizeDto> sizeDtos = product.getSizes().stream()
                .map(size -> {
                    SizeDto sizeDto = new SizeDto();
                    sizeDto.setSizeName(size.getSizeName());
                    return sizeDto;
                }).collect(Collectors.toSet());

        productDto.setColors(colorDtos);
        productDto.setPrice(product.getPrice());
        productDto.setGenderCategory(product.getGenderCategory());
        productDto.setSizes(sizeDtos);
        productDto.setBrand(product.getShoeBrand());
        productDto.setId(product.getId());
        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : productList) {
            productDtos.add(getProductRequest(product));
        }
        return productDtos;
    }

    public List<ProductDto> getProductsByColor(String colorName) {
        Optional<Color> colorOptional = colorRepository.findByColorName(colorName);
        if (colorOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Color color = colorOptional.get();
        Set<Product> products = color.getProducts();

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(getProductRequest(product));
        }
        return productDtos;
    }
    /*
    * Prices are the same for all colors and sizes for a particular product
All colors are available for all sizes
No sub-brands. Its just brands*/
}
