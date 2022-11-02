package eu.kijora.foodmanager.service;

import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.CategoryDto;
import eu.kijora.foodmanager.dto.ProductDto;
import eu.kijora.foodmanager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MappingService {

    private ProductRepository productRepository;

    public MappingService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto convertProductIntoDto(Product product){
        return ProductDto.builder()
                .productId(product.getId())
                .categories(product.getCategories().stream().map(this::convertCategoryIntoDto).collect(Collectors.toSet()))
                .build();
    }

    public CategoryDto convertCategoryIntoDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .auxiliaryCategory(category.isAuxiliaryCategory())
                .name(category.getName())
                .build();
    }
}
