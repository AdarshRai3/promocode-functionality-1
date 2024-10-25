package com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromocodesRequests {
     @NotBlank(message= "Valid Promocode is  required")
     private String code;

     @NotNull (message = "Discount Percent is required ")
     @Min(0) @Max(100)
     private Integer discountPercent;

     @NotNull (message ="Active Status is required")
     private Boolean isActive;

     @NotNull(message = "Expiry Date is required")
     @Future (message ="Expiry date must be of future")
     private LocalDateTime expiryDate;

     @NotEmpty(message ="CourseList is required")
     private List<Long>courseId;
}
