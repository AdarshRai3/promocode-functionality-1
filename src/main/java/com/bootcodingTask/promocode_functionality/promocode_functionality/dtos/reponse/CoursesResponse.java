package com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.reponse;

import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.basic.PromocodesBasicDto;
import com.bootcodingTask.promocode_functionality.promocode_functionality.entity.Promocodes;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoursesResponse {

    private Long courseId;


    private String courseName;


    private BigDecimal coursePrice;

    //This wil be calculated by us
    private BigDecimal discountedCoursePrice;


    //This will give us the basic information about our promocode that are applicable
    private PromocodesBasicDto promocodesBasicDto;
}
