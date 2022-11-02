package eu.kijora.foodmanager.controller;

import eu.kijora.foodmanager.domain.Category;
import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.dto.ProductDto;
import eu.kijora.foodmanager.repository.CategoryRepository;
import eu.kijora.foodmanager.repository.ProductRepository;
import eu.kijora.foodmanager.service.MappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("products")
@Slf4j
public class ProductController {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    MappingService mappingService;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository, MappingService mappingService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mappingService = mappingService;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto addProduct(@RequestBody Product product) {
        Set<Category> collectedCategories = product.getCategories().stream()
                .map(c -> categoryRepository.findById(c.getId()).orElseThrow(
                        () -> new CategoryNotFoundException(String.format("Id %d not found in database", c.getId()))))
                .collect(Collectors.toSet());
        product.setCategories(collectedCategories);
        return mappingService.convertProductIntoDto(productRepository.save(product));
    }

    @PatchMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> changeQuantity(@RequestBody Integer quantity, @PathVariable Long id) {

        Product foundProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(String.format("Id %d not found in database", id)));
        foundProduct.setQuantity(quantity);
        Product saved = productRepository.save(foundProduct);
        log.debug("Successfully changed the quantity of the product");
        return ResponseEntity.ok().body(saved);
    }


    @ExceptionHandler
    public ResponseEntity<?> handle(ProductNotFoundException productNotFoundException) {
        log.error(productNotFoundException.getMessage());
        return ResponseEntity.notFound().build();
    }
}
