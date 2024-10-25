package com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.basic;

import lombok.Data;

@Data
public class PromocodesBasicDto {
    private Long promocodeId;

    private String code;

    private Integer discountPercent;
}
