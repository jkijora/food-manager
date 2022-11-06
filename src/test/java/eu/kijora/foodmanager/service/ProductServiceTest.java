package eu.kijora.foodmanager.service;

import eu.kijora.foodmanager.controller.ProductNotFoundException;
import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.CategoryWriteModel;
import eu.kijora.foodmanager.dto.ProductWriteModel;
import eu.kijora.foodmanager.repository.CategoryRepository;
import eu.kijora.foodmanager.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository prMock;

    @Mock
    CategoryRepository crMock;

    @InjectMocks
    ProductService productService;

    @BeforeAll
    public static void init() {
    }

    @Test
    void changeQuantity_WhenNegativeValue_ThenThrowIAE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.changeQuantity(-10, 1L));
    }

    @Test
    void findById_WhenNotFound_ThenThrowProductNotFound() {
        when(prMock.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ProductNotFoundException.class, () -> productService.findById(1L));
    }


}