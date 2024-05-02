package com.klpc.stadspring.domain.orders.repository;

import com.klpc.stadspring.domain.orders.entity.Orders;
import com.klpc.stadspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    public List<Orders> findAllByUser(User user);

    @Query("""
              SELECT COUNT(od) FROM Orders od
              WHERE :productTypeId in (SELECT pt.productType.id FROM od.orderProducts pt)
           """)
    public Optional<Long> findCntOrdersByProductType(Long productTypeId);

}
