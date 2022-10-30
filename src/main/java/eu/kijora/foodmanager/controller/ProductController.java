package eu.kijora.foodmanager.controller;

import eu.kijora.foodmanager.domain.Product;
import eu.kijora.foodmanager.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("products")
@Slf4j
public class ProductController {

    ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
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
