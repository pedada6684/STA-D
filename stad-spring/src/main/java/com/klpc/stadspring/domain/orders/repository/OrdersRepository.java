package com.klpc.stadspring.domain.orders.repository;

import com.klpc.stadspring.domain.orders.entity.Orders;
import com.klpc.stadspring.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    public List<Orders> findAllByUser(User user);

}
