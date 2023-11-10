package com.EAD.EAD_monolithic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product, Integer>{

        @Query(value = "SELECT * FROM Product WHERE id = ?1", nativeQuery = true)
        Product getProductByProductID(String userID);
}
