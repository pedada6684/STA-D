package com.klpc.stadspring.domain.productType.repository;

import com.klpc.stadspring.domain.productType.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<ProductOption, Long> {
    @Query("SELECT o FROM ProductOption o Where o.product.id = :id")
    Optional<List<ProductOption>> getOptionList(@Param("id") Long id);

}
