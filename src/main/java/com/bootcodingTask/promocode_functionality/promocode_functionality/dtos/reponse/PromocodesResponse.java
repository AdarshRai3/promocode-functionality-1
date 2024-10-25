package com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.reponse;

import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.basic.CoursesBasicDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromocodesResponse {
    private Long promocodeId;

    private String code;

    private Integer discountPercent;

    private LocalDateTime expiryDate;

    private Boolean isActive;

    List<CoursesBasicDto> applicableCourses;
}
