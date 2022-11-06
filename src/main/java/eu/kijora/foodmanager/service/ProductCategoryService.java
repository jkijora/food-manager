package eu.kijora.foodmanager.service;

import eu.kijora.foodmanager.controller.CategoryNotFoundException;
import eu.kijora.foodmanager.controller.ProductNotFoundException;
import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.category.CategoryReadModel;
import eu.kijora.foodmanager.dto.category.ProductReadModelForCategory;
import eu.kijora.foodmanager.dto.product.CategoryReadModelForProduct;
import eu.kijora.foodmanager.dto.category.CategoryWriteModel;
import eu.kijora.foodmanager.dto.product.ProductReadModel;
import eu.kijora.foodmanager.dto.product.ProductWriteModel;
import eu.kijora.foodmanager.repository.CategoryRepository;
import eu.kijora.foodmanager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductCategoryService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // --CATEGORIES--
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

    public CategoryReadModel convertCategoryIntoDto(Category category) {
        return CategoryReadModel.builder()
                .id(category.getId())
                .products(category.getProducts().stream().map(this::convertProductIntoDtoForCategory).collect(Collectors.toSet()))
                .auxiliaryCategory(category.isAuxiliaryCategory())
                .name(category.getName())
                .build();
    }

    public CategoryReadModelForProduct convertCategoryIntoDtoForProduct(Category category) {
        return CategoryReadModelForProduct.builder()
                .id(category.getId())
                .auxiliaryCategory(category.isAuxiliaryCategory())
                .name(category.getName())
                .build();
    }

    // --PRODUCTS--
    public Product convertProductDtoIntoProduct(ProductWriteModel pwm) {
        Set<Category> categories = Set.of();
        if (pwm.getCategoryIds() != null && pwm.getCategoryIds().size() > 0) {
            categories = pwm.getCategoryIds().stream()
                    .map(c -> categoryRepository.findById(c)
                            .orElseThrow(() -> new CategoryNotFoundException(String.format("Id %d not found in database", c))))
                    .collect(Collectors.toSet());
        }
        return Product.builder()
                .categories(categories)
                .comment(pwm.getComment())
                .closestExpiration(pwm.getClosestExpiration())
                .name(pwm.getName())
                .quantity(pwm.getQuantity())
                .quantityThreshold(pwm.getQuantityThreshold())
                .build();
    }

    public ProductReadModel convertProductIntoDto(Product product) {
        return ProductReadModel.builder()
                .productId(product.getId())
                .categories(product.getCategories().stream().map(this::convertCategoryIntoDtoForProduct).collect(Collectors.toSet()))
                .name(product.getName())
                .quantity(product.getQuantity())
                .quantityThreshold(product.getQuantityThreshold())
                .closestExpiration(product.getClosestExpiration())
                .comment(product.getComment())
                .build();
    }

    public ProductReadModelForCategory convertProductIntoDtoForCategory(Product product) {
        return ProductReadModelForCategory.builder()
                .productId(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .quantityThreshold(product.getQuantityThreshold())
                .closestExpiration(product.getClosestExpiration())
                .comment(product.getComment())
                .build();
    }
}
