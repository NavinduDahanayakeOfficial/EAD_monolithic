package com.EAD.EAD_monolithic.controller;

import com.EAD.EAD_monolithic.dto.ProductDTO;
import com.EAD.EAD_monolithic.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@CrossOrigin
public class ProductControllers {

    @Autowired
    private ProductService productService;

    @GetMapping("/getProducts")
    public List<ProductDTO> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/saveProduct")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @PutMapping("updateProduct")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO){
        return productService.saveProduct(productDTO);
    }

    @DeleteMapping("/deleteProduct")
    public boolean deleteProduct(@RequestBody ProductDTO productDTO){
        return productService.deleteProduct(productDTO);
    }

    @GetMapping("/getSingleProduct/{id}")
    public ProductDTO getProductByProductId(@PathVariable int id){
        return productService.getProductByProductID(id);
    }
}
