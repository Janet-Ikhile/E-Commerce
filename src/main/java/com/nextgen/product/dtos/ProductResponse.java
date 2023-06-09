package com.nextgen.product.dtos;

import lombok.Data;

@Data
public class ProductResponse {
    private int code;
    private String message;

    public ProductResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
