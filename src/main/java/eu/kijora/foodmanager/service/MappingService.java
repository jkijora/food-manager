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


    public MappingService() {
    }

    public ProductReadModel convertProductIntoDto(Product product){
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

    public CategoryReadModel convertCategoryIntoDto(Category category){
        return CategoryReadModel.builder()
                .id(category.getId())
                .auxiliaryCategory(category.isAuxiliaryCategory())
                .name(category.getName())
                .build();
    }
}
