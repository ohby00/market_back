package com.osio.market.domain.product.controller;

import com.osio.market.domain.product.dto.ProductAddDTO;
import com.osio.market.domain.product.dto.ProductDetailListDTO;
import com.osio.market.domain.product.dto.ProductListDTO;
import com.osio.market.domain.product.entity.Product;
import com.osio.market.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
@CrossOrigin()
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 상품 등록
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

    // 상품 조회
    @GetMapping("/list")
    public ResponseEntity<List<ProductListDTO>> productList(){
        List<ProductListDTO> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    // 상품 상세 조회
    @GetMapping("/detail/{productId}")
    public ResponseEntity<ProductDetailListDTO> productDetail(@PathVariable("productId") Long productId) {
            ProductDetailListDTO detail = productService.getProductById(productId);

            return ResponseEntity.ok().body(detail);
    }
}
