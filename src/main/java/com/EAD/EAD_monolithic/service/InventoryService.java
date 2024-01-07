package com.EAD.EAD_monolithic.service;

import com.EAD.EAD_monolithic.dto.InventoryDTO;
import com.EAD.EAD_monolithic.entity.Inventory;
import com.EAD.EAD_monolithic.repo.InventoryRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepo inventoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    public InventoryDTO saveProduct(InventoryDTO inventoryDTO){
        inventoryRepo.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public List<InventoryDTO> getAllProducts(){
        List<Inventory> inventoryList = inventoryRepo.findAll();
        return modelMapper.map(inventoryList, new TypeToken<List<InventoryDTO>>(){}.getType());
    }

    public InventoryDTO updateProduct(InventoryDTO inventoryDTO){
        inventoryRepo.save(modelMapper.map(inventoryDTO, Inventory.class));
        return inventoryDTO;
    }

    public boolean deleteProduct(InventoryDTO inventoryDTO){
        inventoryRepo.delete(modelMapper.map(inventoryDTO, Inventory.class));
        return true;
    }

    //Retrieve user details when id is given

    public InventoryDTO getProductByProductID(int itemId){
        Inventory inventory = inventoryRepo.getProductByProductID(itemId);
        return modelMapper.map(inventory, InventoryDTO.class);
    }
}
