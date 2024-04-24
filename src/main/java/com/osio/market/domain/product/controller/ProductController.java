package com.osio.market.domain.product.controller;

import com.osio.market.domain.product.dto.ProductAddDTO;
import com.osio.market.domain.product.entity.Product;
import com.osio.market.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/product")
@CrossOrigin()
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Object> productAdd(
            @RequestBody ProductAddDTO productAddDTO
            /*
                http://localhost:8080/product/add
                {
                "productCategory" : "computer",
                "productDetail" : "hello",
                "productImage" : "image",
                "productName" : "name",
                "productPrice" : 23,
                "productStack" : 1
                }
            */
    ) {
        log.info("productAddDTO: {}", productAddDTO);
        Product product = productService.productAdd(productAddDTO);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> productList(){
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<Object> productDetail(@PathVariable Long productId){
        if (productId == null) {
            return ResponseEntity.badRequest().body("Product ID = null");
        }

        Optional<Product> product = productService.getProductById(productId);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }
}
