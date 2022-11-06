package eu.kijora.foodmanager.service;

import eu.kijora.foodmanager.controller.ProductNotFoundException;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.product.ProductWriteModel;
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

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
