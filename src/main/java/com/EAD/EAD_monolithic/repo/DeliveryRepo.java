package com.EAD.EAD_monolithic.repo;

import com.EAD.EAD_monolithic.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepo extends JpaRepository<Delivery, Long> {

}
