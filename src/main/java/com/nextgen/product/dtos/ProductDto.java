package com.nextgen.product.dtos;

import com.nextgen.product.entity.Brand;
import com.nextgen.product.enums.GenderCategory;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductDto {
    private Long id;
    private String image;
    private String description;
    //@NotNull(message = "Size is required")
    private Set<SizeDto> sizes;
    //@NotNull(message = "Colour is required")
    private Set<ColorDto> colors;
    private Brand brand;
    // @NotBlank(message = "ShoeType should either be MALE or FEMALE")
    private GenderCategory genderCategory;
    private BigDecimal price;
}
