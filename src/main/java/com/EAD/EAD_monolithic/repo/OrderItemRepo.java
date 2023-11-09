package com.EAD.EAD_monolithic.repo;

import com.EAD.EAD_monolithic.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {
}
