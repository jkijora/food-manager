package eu.kijora.foodmanager.controller;

import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.ProductReadModel;
import eu.kijora.foodmanager.repository.CategoryRepository;
import eu.kijora.foodmanager.repository.ProductRepository;
import eu.kijora.foodmanager.service.MappingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductController.class)
public class ProductControllerIntegrationTest {

    @MockBean //adds the mock to the Spring application context
    ProductRepository productRepository;
    @MockBean
    CategoryRepository categoryRepository;
    @SpyBean //Adds spy to Spring app context
    MappingService mappingService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllProducts() throws Exception {
        List<Product> products = List.of(new Product(2, "ketchup"), new Product(5, "flour"));
        when(productRepository.findAll()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                        .andDo(print())
                                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name").value("ketchup"))
                .andExpect(jsonPath("$[1].name").value("flour"));

    }
}