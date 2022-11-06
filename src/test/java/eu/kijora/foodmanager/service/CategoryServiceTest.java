package eu.kijora.foodmanager.service;

import eu.kijora.foodmanager.controller.CategoryNotFoundException;
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
class CategoryServiceTest {

    @Mock
    ProductRepository prMock;

    @Mock
    CategoryRepository crMock;

    @InjectMocks
    CategoryService categoryService;

    @BeforeAll
    public static void init() {
    }


    @Test
    void findById_WhenNotFound_ThenThrowCategoryNotFound() {
        when(crMock.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(CategoryNotFoundException.class, () -> categoryService.findById(1L));
    }

//    @Test
//    void convertCategoryDtoIntoCategory_WhenEmptyProductIds_ThenDoNotCallProductRepository() {
//        CategoryWriteModel cwm = CategoryWriteModel.builder()
//                .productIds(Set.of())
//                .name("Name of category")
//                .build();
//        Category category = categoryService.convertCategoryDtoIntoCategory(cwm);
//
//        Assertions.assertEquals(0, category.getProducts().size());
//        Assertions.assertEquals("Name of category", category.getName());
//        verify(prMock, never()).findById(anyLong());
//    }
//
//    @Test
//    void convertCategoryDtoIntoCategory_WhenSomeProductIds_ThenCallProductRepository() {
//        when(prMock.findById(1L)).thenReturn(Optional.of(Product.builder().name("product1").build()));
//        CategoryWriteModel cwm = CategoryWriteModel.builder()
//                .productIds(Set.of(1L))
//                .name("Name of category")
//                .build();
//        Category category = categoryService.convertCategoryDtoIntoCategory(cwm);
//
//        Assertions.assertEquals(1, category.getProducts().size());
//        Assertions.assertEquals("Name of category", category.getName());
//        verify(prMock, times(1)).findById(anyLong());
//    }
}