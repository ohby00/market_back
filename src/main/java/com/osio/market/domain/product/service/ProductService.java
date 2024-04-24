package com.osio.market.domain.product.service;

import com.osio.market.domain.product.dto.ProductAddDTO;
import com.osio.market.domain.product.dto.ProductDetailListDTO;
import com.osio.market.domain.product.dto.ProductListDTO;
import com.osio.market.domain.product.entity.Product;

import java.util.List;

public interface ProductService {
    // 상품 상세 조회
    ProductDetailListDTO getProductById(Long productId);

    // 상품 등록
    Product productAdd(ProductAddDTO productAddDTO);

    // 상품 조회
    List<ProductListDTO> getAllProducts();
}
