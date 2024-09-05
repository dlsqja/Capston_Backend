package com.HELPT.Backend.domain.product;

import com.HELPT.Backend.domain.equipment.Equipment;
import com.HELPT.Backend.domain.equipment.dto.EquipmentDto;
import com.HELPT.Backend.domain.product.dto.ProductRequest;
import com.HELPT.Backend.domain.product.dto.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    public ProductRequest setupProductRequest(){
        return ProductRequest.builder().price(30000).months(1)
                .build();
    }
    public Product setupProduct(){
        return Product.builder()
                .productId(1L)
                .gymId(1L)
                .months(1)
                .price(30000)
                .build();
    }

    public List<Product> setupProductList(){
        List<Product> products = new ArrayList<>();
        Product product1 = Product.builder()
                .productId(1L)
                .gymId(1L)
                .months(1)
                .price(30000)
                .build();
        Product product2 = Product.builder()
                .productId(2L)
                .gymId(2L)
                .months(3)
                .price(90000)
                .build();

        products.add(product1);
        products.add(product2);

        return products;
    }

    @Test
    @DisplayName("[Product] 헬스장 상품 등록 테스트")
    void saveProduct(){
        // given
        ProductRequest productRequest = setupProductRequest();
        Product product = setupProduct();
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        ProductResponse productResponse = productService.addProduct(1L,productRequest);

        // then
        verify(productRepository).save(any(Product.class));
        assertEquals(productResponse.getProductId(),product.getProductId());
    }

    @Test
    @DisplayName("[Product] 헬스장 상품 목록 조회 테스트")
    void findProduct(){
        // given
        Product product = setupProduct();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        // when
        ProductResponse productResponse = productService.findProduct(1L);

        // then
        verify(productRepository).findById(any(Long.class));
        assertEquals(productResponse.getProductId(),product.getProductId());
    }

    @Test
    @DisplayName("[Product] 상품 수정 테스트")
    void a(){
        // given
        Product product = setupProduct();
        ProductRequest productRequest = setupProductRequest();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        // when
        ProductResponse productResponse = productService.modifyProduct(1L,1L,productRequest);

        // then
        verify(productRepository).findById(any(Long.class));
        assertEquals(productResponse.getProductId(),product.getProductId());
    }

    @Test
    @DisplayName("[Product] 상품 삭제 테스트")
    void b(){
        // given
        Product product = setupProduct();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // when
        productService.deleteProduct(product.getProductId());

        // then
        verify(productRepository).deleteById(any(Long.class));
    }




}
