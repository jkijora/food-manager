package eu.kijora.foodmanager.service;

import eu.kijora.foodmanager.controller.ProductNotFoundException;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.ProductWriteModel;
import eu.kijora.foodmanager.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    ProductRepository productRepository;
    ProductCategoryService productCategoryService;

    public ProductService(ProductRepository productRepository, ProductCategoryService productCategoryService) {
        this.productRepository = productRepository;
        this.productCategoryService = productCategoryService;
    }

    public Product save(ProductWriteModel pwm) {
        return productRepository.save(productCategoryService.convertProductDtoIntoProduct(pwm));
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

//    public ProductReadModel convertProductIntoDto(Product product) {
//        return ProductReadModel.builder()
//                .productId(product.getId())
//                .categories(product.getCategories().stream().map(categoryService::convertCategoryIntoDto).collect(Collectors.toSet()))
//                .name(product.getName())
//                .quantity(product.getQuantity())
//                .quantityThreshold(product.getQuantityThreshold())
//                .closestExpiration(product.getClosestExpiration())
//                .comment(product.getComment())
//                .build();
//    }
//
//    public Product convertProductDtoIntoProduct(ProductWriteModel pwm) {
//        Set<Category> categories = Set.of();
//        if (pwm.getCategoryIds() != null && pwm.getCategoryIds().size() > 0) {
//            categories = pwm.getCategoryIds().stream()
//                    .map(c -> categoryRepository.findById(c)
//                            .orElseThrow(() -> new CategoryNotFoundException(String.format("Id %d not found in database", c))))
//                    .collect(Collectors.toSet());
//        }
//        return Product.builder()
//                .categories(categories)
//                .comment(pwm.getComment())
//                .closestExpiration(pwm.getClosestExpiration())
//                .name(pwm.getName())
//                .quantity(pwm.getQuantity())
//                .quantityThreshold(pwm.getQuantityThreshold())
//                .build();
//    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
