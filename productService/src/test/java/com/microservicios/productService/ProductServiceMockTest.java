package com.microservicios.productService;

import com.microservicios.productService.entity.Category;
import com.microservicios.productService.entity.Product;
import com.microservicios.productService.repository.ProductRepository;
import com.microservicios.productService.service.ProductService;
import com.microservicios.productService.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository);
        Product computer = Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer)).thenReturn(computer);
    }

    @Test
    public void whenValidGetProducBytId_thenReturnName(){
        Product productFound = productService.getProduct(1L);
        Assertions.assertThat(productFound.getName()).isEqualTo("computer");
    }

    @Test
    public void whenValidUpdateStock_thenReturnNewStock(){
        Product productNewStock = productService.updateStock(1L, Double.parseDouble("8"));
        Assertions.assertThat(productNewStock.getStock()).isEqualTo(13);
    }
}
