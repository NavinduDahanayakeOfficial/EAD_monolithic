package com.EAD.EAD_monolithic.repo;

import com.EAD.EAD_monolithic.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {
}
