package eu.kijora.foodmanager.service;

import eu.kijora.foodmanager.controller.CategoryNotFoundException;
import eu.kijora.foodmanager.controller.ProductNotFoundException;
import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.CategoryReadModel;
import eu.kijora.foodmanager.dto.ProductReadModel;
import eu.kijora.foodmanager.dto.ProductWriteModel;
import eu.kijora.foodmanager.repository.CategoryRepository;
import eu.kijora.foodmanager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ProductService {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Product save(ProductWriteModel pwm) {
        return productRepository.save(convertProductDtoIntoProduct(pwm));
    }

    public Product changeQuantity(Integer quantity, Long productId) {
        Product foundProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(String.format("Id %d not found in database", productId)));
        foundProduct.setQuantity(quantity);
        return productRepository.save(foundProduct);
    }

    public ProductReadModel convertProductIntoDto(Product product) {
        return ProductReadModel.builder()
                .productId(product.getId())
                .categories(product.getCategories().stream().map(this::convertCategoryIntoDto).collect(Collectors.toSet()))
                .name(product.getName())
                .quantity(product.getQuantity())
                .quantityThreshold(product.getQuantityThreshold())
                .closestExpiration(product.getClosestExpiration())
                .comment(product.getComment())
                .build();
    }

    public CategoryReadModel convertCategoryIntoDto(Category category) {
        return CategoryReadModel.builder()
                .id(category.getId())
                .auxiliaryCategory(category.isAuxiliaryCategory())
                .name(category.getName())
                .build();
    }

    public Product convertProductDtoIntoProduct(ProductWriteModel pwm) {
        return Product.builder()
                .categories(pwm.getCategoryIds().stream()
                        .map(c -> categoryRepository.findById(c)
                                .orElseThrow(() -> new CategoryNotFoundException(String.format("Id %d not found in database", c))))
                        .collect(Collectors.toSet()))
                .comment(pwm.getComment())
                .closestExpiration(pwm.getClosestExpiration())
                .name(pwm.getName())
                .quantity(pwm.getQuantity())
                .quantityThreshold(pwm.getQuantityThreshold())
                .build();
    }
}
