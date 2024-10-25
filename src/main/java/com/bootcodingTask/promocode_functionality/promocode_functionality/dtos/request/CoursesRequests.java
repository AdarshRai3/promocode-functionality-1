package com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursesRequests {

   @NotBlank (message = "Course Name is Required")
   private String courseName;

   @NotNull (message = "Price is Required")
   @Positive(message = "Price must be positive")
   private BigDecimal coursePrice;


   private String promocodeId;


}
