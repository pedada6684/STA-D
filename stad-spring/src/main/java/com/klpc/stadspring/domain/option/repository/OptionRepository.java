package com.klpc.stadspring.domain.option.repository;

import com.klpc.stadspring.domain.option.entity.ProductOption;
import com.klpc.stadspring.domain.product.controller.response.GetProductInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface OptionRepository extends JpaRepository<ProductOption, Long> {
    @Query("SELECT o FROM ProductOption o Where o.product.id = :id")
    Optional<List<ProductOption>> getOptionList(@Param("id") Long id);

}
