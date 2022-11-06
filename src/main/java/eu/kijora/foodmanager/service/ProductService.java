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

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    CategoryRepository categoryRepository;
    ProductRepository productRepository;
    CategoryService categoryService;

    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Product save(ProductWriteModel pwm) {
        return productRepository.save(convertProductDtoIntoProduct(pwm));
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(String.format("Id %d not found in database", id)));
    }

    public Product changeQuantity(Integer quantity, Long productId) {
        if (quantity >= 0) {
            Product foundProduct = this.findById(productId);
            foundProduct.setQuantity(quantity);
            return productRepository.save(foundProduct);
        } else {
            throw new IllegalArgumentException("Not allowed to set negative quantity");
        }
    }

    public ProductReadModel convertProductIntoDto(Product product) {
        return ProductReadModel.builder()
                .productId(product.getId())
                .categories(product.getCategories().stream().map(categoryService::convertCategoryIntoDto).collect(Collectors.toSet()))
                .name(product.getName())
                .quantity(product.getQuantity())
                .quantityThreshold(product.getQuantityThreshold())
                .closestExpiration(product.getClosestExpiration())
                .comment(product.getComment())
                .build();
    }

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
}
