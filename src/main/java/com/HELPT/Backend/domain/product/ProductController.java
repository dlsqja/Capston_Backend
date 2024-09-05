package com.HELPT.Backend.domain.product;

import com.HELPT.Backend.domain.product.dto.ProductRequest;
import com.HELPT.Backend.domain.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{gymId}")
    public ResponseEntity<List<ProductResponse>> productList(@PathVariable("gymId") Long gymId) {

        return ResponseEntity.ok(productService.findAllProducts(gymId));
    }

    @PostMapping("/{gymId}")
    public ResponseEntity<ProductResponse> addProduct(@PathVariable("gymId") Long gymId,
                                              @RequestBody ProductRequest productRequest) {

        return ResponseEntity.ok(productService.addProduct(gymId,productRequest));
    }

    @PutMapping("/{gymId}/{productId}")
    public ResponseEntity<ProductResponse> modifyProduct(@PathVariable("gymId") Long gymId,
                                                         @PathVariable("productId") Long productId,
                                                      @RequestBody ProductRequest productRequest) {

        return ResponseEntity.ok(productService.modifyProduct(gymId,productId,productRequest));
    }

    @DeleteMapping("/{gymId}/{productId}")
    public ResponseEntity<Boolean> removeProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        
        return ResponseEntity.ok(true);
    }
}
