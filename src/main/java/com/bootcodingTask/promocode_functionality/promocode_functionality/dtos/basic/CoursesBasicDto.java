package com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.basic;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoursesBasicDto {
    private Long courseId;

    private String courseName;

    private BigDecimal coursePrice;


}
