package com.rentabook.repository;

import com.rentabook.domain.Order;
import com.rentabook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByUserAndDeleted(User user, boolean deleted);
}
