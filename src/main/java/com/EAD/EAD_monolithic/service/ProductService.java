package com.EAD.EAD_monolithic.service;

import com.EAD.EAD_monolithic.dto.ProductDTO;
import com.EAD.EAD_monolithic.entity.Product;
import com.EAD.EAD_monolithic.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    public ProductDTO saveProduct(ProductDTO productDTO){
        productRepo.save(modelMapper.map(productDTO, Product.class));
        return productDTO;
    }

    public List<ProductDTO> getAllProducts(){
        List<Product> productList = productRepo.findAll();
        return modelMapper.map(productList, new TypeToken<List<ProductDTO>>(){}.getType());
    }

    public ProductDTO updateProduct(ProductDTO productDTO){
        productRepo.save(modelMapper.map(productDTO, Product.class));
        return productDTO;
    }

    public boolean deleteProduct(ProductDTO productDTO){
        productRepo.delete(modelMapper.map(productDTO, Product.class));
        return true;
    }

    //Retrieve user details when id is given

    public ProductDTO getProductByProductID(int itemId){
        Product product =productRepo.getProductByProductID(itemId);
        return modelMapper.map(product, ProductDTO.class);
    }
}
