package com.EAD.EAD_monolithic.controller;

import com.EAD.EAD_monolithic.dto.InventoryDTO;
import com.EAD.EAD_monolithic.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin
public class InventoryControllers {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/getProducts")
    public List<InventoryDTO> getProducts() {
        return inventoryService.getAllProducts();
    }

    @PostMapping("/saveProduct")
    public InventoryDTO saveProduct(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.saveProduct(inventoryDTO);
    }

    @PutMapping("updateProduct")
    public InventoryDTO updateProduct(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.saveProduct(inventoryDTO);
    }

    @DeleteMapping("/deleteProduct")
    public boolean deleteProduct(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.deleteProduct(inventoryDTO);
    }

    @GetMapping("/getSingleProduct/{id}")
    public InventoryDTO getProductByProductId(@PathVariable int id){
        return inventoryService.getProductByProductID(id);
    }
}
