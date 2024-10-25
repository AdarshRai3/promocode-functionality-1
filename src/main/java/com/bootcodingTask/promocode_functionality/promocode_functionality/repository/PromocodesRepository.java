package com.bootcodingTask.promocode_functionality.promocode_functionality.repository;

import com.bootcodingTask.promocode_functionality.promocode_functionality.entity.Promocodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PromocodesRepository extends JpaRepository<Promocodes,Long> {

    Optional<Promocodes> findByCode(String code);
    List<Promocodes> findByIsActiveTrue();
    List<Promocodes> findByExpiryDateAfter(LocalDateTime date);

}
