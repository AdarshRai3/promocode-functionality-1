package com.bootcodingTask.promocode_functionality.promocode_functionality.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table (name = "Promocode_Table")
public class Promocodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "promocodeId")
    private Long promocodeId;

    @Column (name ="code", nullable = false)
    private String code;

    @Column (name ="discountPercent" , nullable =false )
    private Integer discountPercent;

    @Column (name = "status" , nullable =false)
    private Boolean isActive;

    @Column (name = "expiryDate", nullable =false)
    private LocalDateTime expiryDate;

    @OneToMany(mappedBy = "promocodes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Courses> coursesList;

}
