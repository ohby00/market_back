package com.osio.market.domain.product.service;

import com.osio.market.domain.product.dto.ProductAddDTO;
import com.osio.market.domain.product.entity.Product;
import com.osio.market.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product productAdd(ProductAddDTO productAddDTO) {
        Product product = Product.builder()
                .productName(productAddDTO.getProductName())
                .productCategory(productAddDTO.getProductCategory())
                .productPrice(productAddDTO.getProductPrice())
                .productDetail(productAddDTO.getProductDetail())
                .productImage(productAddDTO.getProductImage())
                .productStack(productAddDTO.getProductStack())
                .build();
        productRepository.save(product);
        return product;
    }

}