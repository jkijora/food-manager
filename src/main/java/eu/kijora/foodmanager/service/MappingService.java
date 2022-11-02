package eu.kijora.foodmanager.service;

import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.CategoryReadModel;
import eu.kijora.foodmanager.dto.ProductReadModel;
import eu.kijora.foodmanager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MappingService {

    private ProductRepository productRepository;

    public MappingService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductReadModel convertProductIntoDto(Product product){
        return ProductReadModel.builder()
                .productId(product.getId())
                .categories(product.getCategories().stream().map(this::convertCategoryIntoDto).collect(Collectors.toSet()))
                .build();
    }

    public CategoryReadModel convertCategoryIntoDto(Category category){
        return CategoryReadModel.builder()
                .id(category.getId())
                .auxiliaryCategory(category.isAuxiliaryCategory())
                .name(category.getName())
                .build();
    }
}
