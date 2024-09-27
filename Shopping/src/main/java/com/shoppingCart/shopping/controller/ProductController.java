package com.shoppingCart.shopping.controller;

import com.shoppingCart.shopping.model.Product;
import com.shoppingCart.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id)
    {
        Product product=productService.getProductById(id);
        if(product!=null)
        {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int productId){
        Product product=productService.getProductById(productId);
        if(product!=null)
        {
            return  new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile)
    {
        Product saveProduct=null;
        try {
            saveProduct =productService.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>(saveProduct,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile)
    {
        if(productService.getProductById(product.getId())!=null) {
            Product saveProduct=null;
            try {
                saveProduct =productService.addOrUpdateProduct(product,imageFile);
                return new ResponseEntity<>("Updated",HttpStatus.CREATED);
            } catch (IOException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {
        Product product=productService.getProductById(id);
        if(product!=null)
        {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword)
    {
        List<Product> products=productService.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
