package com.EAD.EAD_monolithic.repo;

import com.EAD.EAD_monolithic.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepo extends JpaRepository<Inventory, Integer>{

        @Query(value = "SELECT * FROM inventory WHERE item_id = ?1", nativeQuery = true)
        Inventory getProductByProductID(int item_id);
}
