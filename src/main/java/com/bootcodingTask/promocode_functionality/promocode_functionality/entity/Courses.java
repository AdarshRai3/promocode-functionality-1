package com.bootcodingTask.promocode_functionality.promocode_functionality.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table (name = "Courses_Table")
public class Courses {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name ="courseId")
    private Long courseId;

    @Column(name ="courseName",  nullable = false )
    private String courseName;

    @Column (name ="coursePrice" ,nullable = false )
    private BigDecimal coursePrice;

    @ManyToOne
    @JoinColumn (name = "promocodeId")
    private Promocodes promocodes;

}
