package com.osio.market.domain.product.service;

import com.osio.market.domain.product.dto.ProductAddDTO;
import com.osio.market.domain.product.dto.ProductDetailListDTO;
import com.osio.market.domain.product.dto.ProductListDTO;
import com.osio.market.domain.product.entity.Product;
import com.osio.market.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /* 상품 조회
       단순히 데이터를 노출시키는 경우 DTO로 빌드
       stream에서 처리한 요소를 리스트 형태로 처리하기 위해 collect를 사용하고,
       findAll을 사용하기 위해 List로 리턴값 지정
    */
    @Override
    public List<ProductListDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductListDTO> list = products.stream().map(
                product -> ProductListDTO.builder()
                        .productId(product.getProductId())
                        .productName(product.getProductName())
                        .productCategory(product.getProductCategory())
                        .productImage(product.getProductImage())
                        .productPrice(product.getProductPrice())
                        .build()
        ).collect(Collectors.toList());

        return list;
    }

    /* 상품 상세 조회
       람다식을 쓰지 않으면 NoSuchElementException 사용 불가 ?
    */
    @Override
    public ProductDetailListDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()
                -> new NoSuchElementException("No item" + productId));
        return ProductDetailListDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productCategory(product.getProductCategory())
                .productImage(product.getProductImage())
                .productPrice(product.getProductPrice())
                .productDetail(product.getProductDetail())
                .productQuantity(product.getProductQuantity())
                .build();
    }

    // 상품 등록
    // 데이터의 수정이 있을 경우 엔티티로 빌드
    @Override
    public Product productAdd(ProductAddDTO productAddDTO) {
        Product product = Product.builder()
                .productName(productAddDTO.getProductName())
                .productCategory(productAddDTO.getProductCategory())
                .productPrice(productAddDTO.getProductPrice())
                .productDetail(productAddDTO.getProductDetail())
                .productImage(productAddDTO.getProductImage())
                .productQuantity(productAddDTO.getProductQuantity())
                .build();
        productRepository.save(product);
        return product;
    }
}