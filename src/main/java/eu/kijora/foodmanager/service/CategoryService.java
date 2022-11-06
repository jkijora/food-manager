package eu.kijora.foodmanager.service;

import eu.kijora.foodmanager.controller.CategoryNotFoundException;
import eu.kijora.foodmanager.controller.ProductNotFoundException;
import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.CategoryReadModel;
import eu.kijora.foodmanager.dto.CategoryWriteModel;
import eu.kijora.foodmanager.repository.CategoryRepository;
import eu.kijora.foodmanager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    ProductService productService;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    public Category save(CategoryWriteModel cwm) {
        return categoryRepository.save(convertCategoryDtoIntoCategory(cwm));
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(String.format("Id %d not found in database", id)));
    }


    public CategoryReadModel convertCategoryIntoDto(Category category) {
        return CategoryReadModel.builder()
                .id(category.getId())
                .auxiliaryCategory(category.isAuxiliaryCategory())
                .name(category.getName())
                .build();
    }

    public Category convertCategoryDtoIntoCategory(CategoryWriteModel cwm) {
        Set<Product> products = Set.of();
        if (cwm.getProductIds() != null && cwm.getProductIds().size() > 0) {
            products = cwm.getProductIds().stream()
                    .map(p -> productRepository.findById(p)
                            .orElseThrow(() -> new ProductNotFoundException(String.format("Id %d not found in database", p))))
                    .collect(Collectors.toSet());
        }
        return Category.builder()
                .auxiliaryCategory(cwm.isAuxiliaryCategory())
                .name(cwm.getName())
                .products(products)
                .build();
    }

}
