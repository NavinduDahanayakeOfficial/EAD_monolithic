package com.EAD.EAD_monolithic.repo;

import com.EAD.EAD_monolithic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product, Integer>{

        @Query(value = "SELECT * FROM Product WHERE item_id = ?1", nativeQuery = true)
        Product getProductByProductID(int item_id);
}
