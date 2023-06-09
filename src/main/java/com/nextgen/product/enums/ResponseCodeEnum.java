package com.nextgen.product.enums;

public enum ResponseCodeEnum {
    SUCCESS(0, "Success"),
    ERROR(-1, "Unable to process your request."),
    ;

    private int code;
    private String description;

    ResponseCodeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
