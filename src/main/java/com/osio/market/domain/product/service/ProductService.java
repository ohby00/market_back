package com.osio.market.domain.product.service;

import com.osio.market.domain.product.dto.ProductAddDTO;
import com.osio.market.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    // 상품 상세 조회
    Optional<Product> getProductById(Long productId);

    // 상품 등록
    Product productAdd(ProductAddDTO productAddDTO);

    // 상품 조회
    List<Product> getAllProducts();
}
